package edu.byu.cs.tweeter.client.model.service.backgroundTask.follow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticatedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Background task that determines if one user is following another.
 */
public class IsFollowerTask extends AuthenticatedTask {

    public static final String IS_FOLLOWER_KEY = "is-follower";

    private final User follower;

    private final User followee;

    private boolean isFollower;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
    }

    private void sendSuccessMessage(boolean isFollower) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

    @Override
    protected void runTask() {
        isFollower = new Random().nextInt() > 0;
        sendSuccessMessage(isFollower);
    }
}
