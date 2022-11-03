package edu.byu.cs.tweeter.client.presenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IBooleanObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IEmptyObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IIntegerObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter {
    private final MainView view;

    private UserService userService;
    private FeedService feedService;

    public interface MainView extends View{
        void navigateToLogin();
        void setFollowButton();
        void setFollowingButton();
        void setFollowingCountText(int count);
        void setFollowerCountText(int count);
        void enableFollowButton(boolean enable);
    }

    public MainPresenter(MainView view) {
        super(view);
        if (view == null) {
            throw new NullPointerException();
        }
        this.view = view;
    }

    protected FeedService getFeedService() {
        if (feedService == null) {
            feedService = new FeedService();
        }
        return feedService;
    }

    protected UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void logout() {
        view.displayInfoMessage("Logging out...");
        getUserService().logout(Cache.getInstance().getCurrUserAuthToken(), new LogoutObserver());
    }

    public void handleFollowBtnClick(String buttonText, User selectedUser) {
        view.enableFollowButton(false);
        if (buttonText.equals("Following")) {
            unfollow(selectedUser);
        } else {
            follow(selectedUser);
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public void updateSelectedUserFollowingAndFollowers(User selectedUser) {
        new FollowService().getFollowerCount(selectedUser, new GetFollowerCountObserver());
        new FollowService().getFollowingCount(selectedUser, new GetFollowingCountObserver());
    }

    public void isFollower(User selectedUser) {
        new FollowService().isFollower(selectedUser, new IsFollowerObserver());
    }

    public class LogoutObserver implements IEmptyObserver {
        @Override
        public void handleSuccess() {
            view.clearErrorMessage();
            view.displayInfoMessage("Successfully logged out!");
            view.navigateToLogin();
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to logout because of an exception: " + exception.getMessage());
        }
    }

    private class IsFollowerObserver implements IBooleanObserver {
        @Override
        public void handleSuccess(boolean value) {
            if (value) {
                view.setFollowButton();
            } else {
                view.setFollowingButton();
            }
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to determine following relationship because of exception: " + exception.getMessage());
        }
    }

    public void follow(User selectedUser) {
        view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
        new FollowService().follow(selectedUser, new FollowObserver());
        updateSelectedUserFollowingAndFollowers(selectedUser);
    }

    private class FollowObserver implements IEmptyObserver {
        @Override
        public void handleSuccess() {
            view.clearInfoMessage();
            view.setFollowingButton();
        }

        @Override
        public void handleFailure(String message) {
            view.clearErrorMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearErrorMessage();
            view.displayErrorMessage("Failed to unfollow because of an exception: " + exception.getMessage());
        }
    }

    public void unfollow(User selectedUser) {
        view.displayInfoMessage("Removing " + selectedUser.getName() + "...");
        new FollowService().unfollow(selectedUser, new UnfollowObserver());
        updateSelectedUserFollowingAndFollowers(selectedUser);
    }

    private class UnfollowObserver implements IEmptyObserver {
        @Override
        public void handleSuccess() {
            view.clearInfoMessage();
            view.setFollowButton();
        }

        @Override
        public void handleFailure(String message) {
            view.clearErrorMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearErrorMessage();
            view.displayErrorMessage("Failed to unfollow because of an exception: " + exception.getMessage());
        }
    }

    public void postStatus(String post) {
        view.displayInfoMessage("Posting Status...");
        try {
            AuthToken authToken = Cache.getInstance().getCurrUserAuthToken();
            Status newStatus = new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
            getFeedService().postStatus(authToken, newStatus, new PostStatusObserver());
        } catch (Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to post status because of exception: " + exception.getMessage());
        }
    }

    public class PostStatusObserver implements IEmptyObserver {
        @Override
        public void handleSuccess() {
            view.clearInfoMessage();
            view.clearErrorMessage();
            view.displayInfoMessage("Successfully posted status.");
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayInfoMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to post status because of exception: " + exception.getMessage());
        }
    }

    public void getFollowingCount(User selectedUser) {
        new FollowService().getFollowingCount(selectedUser, new GetFollowingCountObserver());
    }

    private class GetFollowingCountObserver implements IIntegerObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowingCountText(count);
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to get following count because of an exception: " + exception.getMessage());
        }
    }

    public void getFollowerCount(User selectedUser) {
        new FollowService().getFollowerCount(selectedUser, new GetFollowerCountObserver());
    }

    private class GetFollowerCountObserver implements IIntegerObserver {
        @Override
        public void handleSuccess(int count) {
            view.setFollowerCountText(count);
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to get followers count because of an exception: " + exception.getMessage());
        }
    }
}