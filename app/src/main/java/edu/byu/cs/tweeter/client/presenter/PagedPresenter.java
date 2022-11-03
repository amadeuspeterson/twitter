package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IObjectObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IPageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter {
    protected static final int PAGE_SIZE = 10;
    protected User targetUser = Cache.getInstance().getCurrUser();
    protected AuthToken authToken = Cache.getInstance().getCurrUserAuthToken();
    protected T lastItem;
    protected  boolean hasMorePages = true;
    protected boolean isLoading = false;
    protected boolean isGettingUser;
    protected PagedView<T> view;
    private UserService userService;

    public PagedPresenter(PagedView<T> view) {
        super(view);
        this.view = view;
        userService = new UserService();
    }

    public interface PagedView<T> extends View {
        void setLoading(boolean isLoading);
        void addItems(List<T> items);
        void navigateToUser(User user);
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMorePages() {
        this.isLoading = true;
        view.setLoading(true);
        getItems(authToken, targetUser, PAGE_SIZE, lastItem);
    }

    public void getUser(String alias) {
        view.displayInfoMessage("Getting user's profile...");
        userService.getUser(alias, authToken, new GetUserObserver());
    }

    public abstract void getItems(AuthToken authToken, User targetUser, int pageSizes, T lastItem);

    protected class PagedObserver implements IPageObserver<T> {

        @Override
        public void handleSuccess(List<T> objects, boolean hasMorePages) {
            lastItem = (objects.size() > 0) ? objects.get(objects.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.setLoading(false);
            view.addItems(objects);
            isLoading = false;
        }

        @Override
        public void handleFailure(String message) {
            view.setLoading(false);
            view.displayErrorMessage(message);
            isLoading = false;
        }

        @Override
        public void handleException(Exception exception) {
            view.setLoading(false);
            view.displayErrorMessage("Failed to get paged items because of exception: " + exception.getMessage());
            isLoading = false;
        }
    }

    private class GetUserObserver implements IObjectObserver<User> {
        @Override
        public void handleSuccess(User user) {
            view.navigateToUser(user);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayInfoMessage("Failed to get user because of an exception: " + exception.getMessage());
        }
    }
}
