package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

public interface IIntegerObserver extends IServiceObserver {
    void handleSuccess(int count);
}
