package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class UserCreateController extends UserController implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    /**
     * Action to create new user.
     */
    public void doSaveNewUser() {
        Set<UserRole> newRoles = new HashSet<>();
        newRoles.add(UserRole.EMPLOYEE);

        try {
            User user = getUserService().saveNewUser(this.username, this.password, this.firstName, this.lastName, this.email, newRoles);
            displayInfo("Player created", "You have been successfully registered. You can log in now.");
        } catch (IllegalArgumentException e) {
            displayError("Fehler", e.getMessage());
        }
    }
}
