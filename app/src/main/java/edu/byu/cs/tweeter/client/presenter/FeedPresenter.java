package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class FeedPresenter extends PagedPresenter<Status> {
    protected FeedView view;
    protected final FeedService feedService;

    public interface FeedView extends PagedView<Status> {
        void navigateToUrl(String url);
    }

    public FeedPresenter(FeedView view) {
        super(view);
        this.view = view;
        feedService = new FeedService();
    }

    public void handleClickMention(String clickable) {
        if (clickable.contains("http")) {
            view.navigateToUrl(clickable);
        } else {
            getUser(clickable);
        }
    }
}
