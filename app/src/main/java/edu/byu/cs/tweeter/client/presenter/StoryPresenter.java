package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends FeedPresenter {

    public StoryPresenter(FeedView view) {
        super(view);
    }

    @Override
    public void getItems(AuthToken authToken, User targetUser, int pageSizes, Status lastItem) {
        feedService.getStory(targetUser, PAGE_SIZE, lastItem, new PagedObserver());
    }
}
