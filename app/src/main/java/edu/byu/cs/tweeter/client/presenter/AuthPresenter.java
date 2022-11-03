package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.IAuthObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthPresenter extends Presenter{
    protected final AuthView view;

    public interface AuthView extends View {
        void navigateToUser(User user);
    }

    public AuthPresenter(AuthView view) {
        super(view);
        if (view == null) {
            throw new NullPointerException();
        }
        this.view = view;
    }

    protected class AuthObserver implements IAuthObserver {

        @Override
        public void handleSuccess(User user, AuthToken authToken) {
            view.displayInfoMessage("Hello " + user.getFirstName());
            view.clearErrorMessage();
            view.navigateToUser(user);
        }

        @Override
        public void handleFailure(String message) {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

        @Override
        public void handleException(Exception exception) {
            view.clearInfoMessage();
            view.displayErrorMessage("Failed to authenticate because of exception: " + exception.getMessage());
        }
    }
}
