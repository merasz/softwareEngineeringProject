package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.controllers.demo.UserStatusController;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
    private UserListController userListController;

    @Autowired
    UserStatusController userStatusController;

    @Autowired
    private UserService userService;

    /**
     * Attribute to cache the currently displayed user
     */
    private User newUser;
    private User selectedUser;

    private String password;
    private String confirmPassword;

    @PostConstruct
    public void init() {
        doCreateNewUser();
    }

    public void doCreateNewUser() {
        newUser = new User();
    }

    /**
     * Sets the currently displayed user and reloads it form db. This user is
     * targeted by any further calls of
     * {@link #doReloadUser()}, {@link #doSaveNewUser()} and
     * {@link #doDeleteUser()}.
     *
     * @param selectedUser
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        doReloadUser();
    }

    /**
     * Returns the currently displayed user.
     *
     * @return User
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Action to force a reload of the currently displayed user.
     */
    public void doReloadUser() {
        selectedUser = userService.loadUser(selectedUser.getUsername());
    }

    /**
     * Action to delete the currently displayed user.
     */
    public void doDeleteUser() {
        try {
            this.userService.deleteUser(selectedUser);
            selectedUser = null;
            displayInfo("User deleted", "Account successfully deleted.");
            userListController.loadUsers();
        } catch (IllegalArgumentException e){
            displayError("User not deleted", e.getMessage());
        }
    }

    /**
     * Action to save the currently displayed user.
     */
    public void doSaveNewUser() {
        try {
            saveNewUser();
            displayInfo("User created", "");
            userStatusController.addUserStatus(selectedUser);
            doCreateNewUser();
            userListController.loadUsers();
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage(), e.getCause().getMessage());
        }
    }

    private void saveNewUser() throws IllegalArgumentException {
        selectedUser = newUser;
        if (!(password == null) && !password.isEmpty()) {
            updatePassword();
        }
        if(!userService.isUsernameAlreadyTaken(selectedUser)) {
            selectedUser = userService.saveUser(selectedUser);
        } else {
            throw new IllegalArgumentException("User not created", new Throwable("Username already exists."));
        }
    }

    /**
     * Sign up as a new user from login-page
     */
    public void signUp() {
        newUser.setRoles(new HashSet<>());
        try {
            saveNewUser();
            displayInfo("Account created", "You can log in now.");
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage(), e.getCause().getMessage());
        }
    }

    /**
     * Edit the currently displayed user.
     */
    public void doUpdateUser() {
        try {
            if (!password.isEmpty()) {
                updatePassword();
            }
            saveUser();
            PrimeFaces.current().executeScript("PF('userEditDialog').hide()");
            displayInfo("User edited", "");
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage(), e.getCause().getMessage());
        }
    }

    /**
     * Edit the own users password in the profile.
     */
    public void updatePasswordDialog() {
        try {
            updatePassword();
            saveUser();
            PrimeFaces.current().executeScript("PF('changePasswordDialog').hide()");
            displayInfo("Password changed", "");
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage(), e.getCause().getMessage());
        }
    }

    private void updatePassword() throws IllegalArgumentException {
        if (password.length() > 3) {
            if (password.equals(confirmPassword)) {
                selectedUser.setPassword(password);
            } else {
                throw new IllegalArgumentException("Password not changed", new Throwable("Confirmation password does not match."));
            }
        } else {
            throw new IllegalArgumentException("Password invalid", new Throwable("Your password must be at least 4 characters long."));
        }
    }

    /**
     * simply save a user and update UserListController.
     */
    public void saveUser() {
        selectedUser = userService.saveUser(selectedUser);
        userListController.loadUsers();
    }

    /**
     * Get User Roles for datatable filter.
     */
    public List<UserRole> getListRoles(){
        return Arrays.asList(UserRole.values());
    }

    //region getter & setter
    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    //endregion
}
