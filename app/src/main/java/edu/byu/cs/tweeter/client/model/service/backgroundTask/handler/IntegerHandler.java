package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.CountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IIntegerObserver;

public class IntegerHandler extends BackgroundTaskHandler<IIntegerObserver> {
    public IntegerHandler(IIntegerObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(IIntegerObserver observer, Bundle data) {
        int count = data.getInt(CountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }
}
