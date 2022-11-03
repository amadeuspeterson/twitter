package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class RegisterPresenter extends AuthPresenter {
    public RegisterPresenter(AuthView view) {
        super(view);
    }

    public void initiateRegister(String firstName, String lastName, String username, String password, String image){
        String message = validateRegister(firstName, lastName, username, password, image);
        if(message == null) {
            view.clearErrorMessage();
            view.displayInfoMessage("Registering...");
            new UserService().register(firstName, lastName, username, password, image, new AuthObserver());
        } else {
            view.clearInfoMessage();
            view.displayErrorMessage(message);
        }

    }

    public String validateRegister(String firstName, String lastName, String username, String password,
                                   String imageToUpload) {
            if (firstName.length() == 0) {
                return "First Name cannot be empty.";
            }
            if (lastName.length() == 0) {
                return "Last Name cannot be empty.";
            }
            if (username.length() == 0) {
                return "Alias cannot be empty.";
            }
            if (username.charAt(0) != '@') {
                return "Alias must begin with @.";
            }
            if (username.length() < 2) {
                return "Alias must contain 1 or more characters after the @.";
            }
            if (password.length() == 0) {
                return "Password cannot be empty.";
            }

            if (imageToUpload == null) {
                return "Profile image must be uploaded.";
            }
            return null;
    }
}
