package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.AuthHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.EmptyHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.handler.ObjectHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IAuthObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IEmptyObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IObjectObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.user.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.user.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.user.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.user.LogoutTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {
    public void login(String username, String password, IAuthObserver observer) {
        LoginTask loginTask = new LoginTask(username, password, new AuthHandler(observer));
        BackgroundTaskUtils.runTask(loginTask);
    }


    public void register(String firstName, String lastName, String username, String password, String imageToUpload, IAuthObserver observer) {
        RegisterTask registerTask = new RegisterTask(firstName, lastName, username, password, imageToUpload, new AuthHandler(observer));
        BackgroundTaskUtils.runTask(registerTask);
    }

    public void getUser(String username, AuthToken currUserAuthToken, IObjectObserver<User> observer) {
        GetUserTask getUserTask = new GetUserTask(currUserAuthToken,
                username, new ObjectHandler<User>(observer));
        BackgroundTaskUtils.runTask(getUserTask);
    }

    public void logout(AuthToken authToken, IEmptyObserver observer) {
        LogoutTask logoutTask = new LogoutTask(authToken, new EmptyHandler(observer));
        BackgroundTaskUtils.runTask(logoutTask);
    }
}