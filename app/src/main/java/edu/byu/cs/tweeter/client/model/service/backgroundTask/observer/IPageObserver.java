package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

import java.util.List;

public interface IPageObserver<T> extends IServiceObserver {
    void handleSuccess(List<T> objects, boolean hasMorePages);
}
