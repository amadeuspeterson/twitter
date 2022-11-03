package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.follow.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IBooleanObserver;

public class BooleanHandler extends BackgroundTaskHandler<IBooleanObserver> {

    public BooleanHandler(IBooleanObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(IBooleanObserver observer, Bundle data) {
        boolean isFollower = data.getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        observer.handleSuccess(isFollower);
    }
}
