package edu.byu.cs.tweeter.client.model.service.backgroundTask.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticatedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask {

    public static final String USER_KEY = "user";

    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private final String alias;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.alias = alias;
    }

    private void sendSuccessMessage(User user) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putSerializable(USER_KEY, user);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

    @Override
    protected void runTask() {
        User user = getUser();

        sendSuccessMessage(user);
    }


    private User getUser() {
        return getFakeData().findUserByAlias(alias);
    }
}
