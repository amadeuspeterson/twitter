package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.feed.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.feed.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.feed.GetStoryTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.EmptyHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PageHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IEmptyObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IPageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService {
    public void postStatus(AuthToken authToken, Status status, IEmptyObserver observer){
        PostStatusTask statusTask = new PostStatusTask(authToken, status, new EmptyHandler(observer));
        BackgroundTaskUtils.runTask(statusTask);
    }

    public void getFeed(AuthToken authToken, User user, int PAGE_SIZE, Status lastStatus, IPageObserver<Status> observer) {
        GetFeedTask getFeedTask = new GetFeedTask(authToken,
                user, PAGE_SIZE, lastStatus, new PageHandler<Status>(observer));
        BackgroundTaskUtils.runTask(getFeedTask);
    }

    public void getStory(User user, int PAGE_SIZE, Status lastStatus, IPageObserver<Status> observer){
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, PAGE_SIZE, lastStatus, new PageHandler<Status>(observer));
        BackgroundTaskUtils.runTask(getStoryTask);
    }

}
