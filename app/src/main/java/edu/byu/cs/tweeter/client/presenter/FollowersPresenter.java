package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends FollowPresenter {

    public FollowersPresenter(FollowView view) {
        super(view);
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSizes, User lastItem) {
        followService.getFollowers(authToken, targetUser, PAGE_SIZE, lastItem, new GetUsersObserver());
    }
}