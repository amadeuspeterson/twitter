package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IObjectObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.user.GetUserTask;

public class ObjectHandler<T> extends BackgroundTaskHandler<IObjectObserver<T>> {
    public ObjectHandler(IObjectObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(IObjectObserver observer, Bundle data) {
        T item = (T) data.getSerializable(GetUserTask.USER_KEY);
        observer.handleSuccess(item);
    }
}
