package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IPageObserver;

public class PageHandler<T> extends BackgroundTaskHandler<IPageObserver<T>> {
    public PageHandler(IPageObserver<T> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(IPageObserver<T> observer, Bundle data) {
        List<T> items = (List<T>) data.getSerializable(PagedTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(PagedTask.MORE_PAGES_KEY);
        if(items != null){
            observer.handleSuccess(items, hasMorePages);
        }
    }
}
