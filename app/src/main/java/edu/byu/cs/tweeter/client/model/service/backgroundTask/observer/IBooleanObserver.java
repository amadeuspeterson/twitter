package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface IBooleanObserver extends IServiceObserver {
    void handleSuccess(boolean value);
}
