package edu.byu.cs.tweeter.client.model.service.backgroundTask.user;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
    }

    protected Pair<User, AuthToken> login() {
        User loggedInUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(loggedInUser, authToken);
    }

    @Override
    protected void runTask() throws IOException {
        Pair<User, AuthToken> loginResult = login();

        User user = loginResult.getFirst();
        AuthToken authToken = loginResult.getSecond();

        sendSuccessMessage(user, authToken);
    }
}
