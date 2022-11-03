package edu.byu.cs.tweeter.client.model.service.backgroundTask.observer;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public interface IAuthObserver extends IServiceObserver {
    void handleSuccess(User user, AuthToken authToken);
}
