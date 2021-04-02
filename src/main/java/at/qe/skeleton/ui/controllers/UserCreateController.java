package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

@Component
@Scope("view")
public class UserCreateController extends UserController implements Serializable {
    private User user = getUser();
    private final UserService userService = getUserService();
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String confirmPass;
    private UserRole[] roles;
    //private Set<UserRole> roles;

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveUser() {
        try {
            user = userService.createUser(user, username, password, firstName, lastName, email);
            displayInfo("Player created", "You have been successfully registered. You can log in now.");
        } catch (IllegalArgumentException e) {
            displayError("Fehler", e.getMessage());
        }
    }

    /**
     * Action to change a user's password.
     */
    public void doUpdatePassword() {
        try {
            user = userService.updatePassword(user, password, confirmPass);
            displayInfo("Password changed" ,"Password successfully changed.");
        } catch (IllegalArgumentException e){
            displayError("Password cannot be empty", "Please enter any characters for your password.");
        }
    }

    /**
     * Action to change a user's roles.
     */
    public void doUpdateRoles() {
        try {
            user = userService.updateRoles(user, roles);
            displayInfo("Password changed" ,"Password successfully changed.");
        } catch (IllegalArgumentException e){
            displayError("Password cannot be empty", "Please enter any characters for your password.");
        }
    }
}
