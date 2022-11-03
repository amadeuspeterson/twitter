package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * The presenter for the "following" functionality of the application.
 */
public class FollowingPresenter extends FollowPresenter{

    public FollowingPresenter(FollowView view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSizes, User lastItem) {
        followService.getFollowees(authToken, targetUser, PAGE_SIZE, lastItem, new GetUsersObserver());
    }
}
