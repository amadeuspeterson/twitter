package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.BooleanHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.EmptyHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.IntegerHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.PageHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IBooleanObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IEmptyObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IIntegerObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IPageObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public void getFollowees(AuthToken authToken, User targetUser, int limit, User lastFollowee, IPageObserver<User> observer) {
        GetFollowingTask followingTask = new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new PageHandler<User>(observer));
        BackgroundTaskUtils.runTask(followingTask);
    }

    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, IPageObserver<User> observer) {
        GetFollowersTask followersTask = new GetFollowersTask(authToken, targetUser, limit, lastFollower, new PageHandler<User>(observer));
        BackgroundTaskUtils.runTask(followersTask);
    }

    public void isFollower(User selectedUser, IBooleanObserver observer) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new BooleanHandler(observer));
        BackgroundTaskUtils.runTask(isFollowerTask);
    }

    public void follow(User selectedUser, IEmptyObserver observer) {
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new EmptyHandler(observer));
        BackgroundTaskUtils.runTask(followTask);

    }

    public void unfollow(User selectedUser, IEmptyObserver observer) {
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new EmptyHandler(observer));
        BackgroundTaskUtils.runTask(unfollowTask);
    }

    public void getFollowingCount(User selectedUser, IIntegerObserver observer){
        CountTask followingCountTask = new CountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new IntegerHandler(observer));
        BackgroundTaskUtils.runTask(followingCountTask);
    }

    public void getFollowerCount(User selectedUser, IIntegerObserver observer){
        CountTask followersCountTask = new CountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new IntegerHandler(observer));
        BackgroundTaskUtils.runTask(followersCountTask);
    }
}
