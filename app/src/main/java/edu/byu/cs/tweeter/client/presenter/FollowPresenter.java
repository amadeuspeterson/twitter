package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IPageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class FollowPresenter extends PagedPresenter<User>{
    protected final FollowService followService;
    protected FollowView view;

//    @Override
//    public void getItems(AuthToken authToken, User targetUser, int pageSizes, User lastItem) {
//        followService.getFollowees(authToken, targetUser, PAGE_SIZE, lastItem, new GetUsersObserver());
//    }

    public interface FollowView extends PagedView<User>{}

    public FollowPresenter(FollowView view) {
        super(view);
        this.view = view;
        this.followService = new FollowService();
    }

    protected class GetUsersObserver implements IPageObserver<User> {

        @Override
        public void handleSuccess(List<User> objects, boolean hasMorePages) {
            lastItem = (objects.size() > 0) ? objects.get(objects.size() - 1) : null;
            setHasMorePages(hasMorePages);
            view.setLoading(false);
            view.addItems(objects);
            isLoading = false;
        }

        @Override
        public void handleFailure(String message) {
            view.setLoading(false);
            view.displayErrorMessage("Failed to get users: " + message);
            isLoading = false;
        }

        @Override
        public void handleException(Exception exception) {
            view.setLoading(false);
            view.displayErrorMessage("Failed to get users because of exception: " + exception.getMessage());
            isLoading = false;
        }
    }
}
