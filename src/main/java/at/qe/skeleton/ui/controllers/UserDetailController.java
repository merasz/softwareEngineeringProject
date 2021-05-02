package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the user view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserDetailController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    /**
     * Attribute to cache the currently displayed user
     */
    private User user;

    /**
     * Sets the currently displayed user and reloads it form db. This user is
     * targeted by any further calls of
     * {@link #doReloadUser()}, {@link #doSaveUser()} and
     * {@link #doDeleteUser()}.
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        doReloadUser();
    }

    /**
     * Returns the currently displayed user.
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadUser() {
        user = userService.loadUser(user.getUsername());
    }

    /**
     * Action to delete the currently displayed user.
     */
    public void doDeleteUser() {
        try {
            this.userService.deleteUser(user);
            user = null;
            displayInfo("User deleted", "Account successfully deleted.");
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        } catch (Exception e) {
            displayError("Error", "Account could not be deleted.");
        }
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveUser() {
        try {
            if(user.getRoles().size() > 0) {
                user = userService.saveUser(user);
                displayInfo("User updated!","");
            }
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

//    /**
//     * Action to change a user's password.
//     */
//    public void doUpdatePassword() {
//        try {
//            user = userService.updatePassword(user, password, confirmPass);
//            displayInfo("Password changed" ,"Password successfully changed.");
//        } catch (IllegalArgumentException e){
//            displayError("Password cannot be empty", "Please enter any characters for your password.");
//        }
//    }

//    /**
//     * Action to change a user's roles.
//     */
//    public void doUpdateRoles() {
//        try {
//            user = userService.updateRoles(user, roles);
//            displayInfo("User rolle changed" ,"Roll successfully changed.");
//        } catch (IllegalArgumentException e){
//            displayError("Password cannot be empty", "Please enter any characters for your password.");
//        }
//    }

    public List<UserRole> getListRoles(){
        List<UserRole> list = new ArrayList<>();
        list.add(UserRole.ADMIN);
        list.add(UserRole.GAME_MANAGER);
        list.add(UserRole.PLAYER);
        return list;
    }


}
