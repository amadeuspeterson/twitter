package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IEmptyObserver;

public class EmptyHandler extends BackgroundTaskHandler<IEmptyObserver> {
    public EmptyHandler(IEmptyObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(IEmptyObserver observer, Bundle data) {
        observer.handleSuccess();
    }
}
