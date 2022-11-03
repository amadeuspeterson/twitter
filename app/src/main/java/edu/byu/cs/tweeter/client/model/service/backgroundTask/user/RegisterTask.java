package edu.byu.cs.tweeter.client.model.service.backgroundTask.user;

import android.os.Handler;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {

    private final String firstName;

    private final String lastName;

    private final String image;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    private Pair<User, AuthToken> register() {
        User registeredUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(registeredUser, authToken);
    }

    @Override
    protected void runTask() throws IOException {
        Pair<User, AuthToken> registerResult = register();

        User registeredUser = registerResult.getFirst();
        AuthToken authToken = registerResult.getSecond();

        sendSuccessMessage(registeredUser, authToken);
    }
}
