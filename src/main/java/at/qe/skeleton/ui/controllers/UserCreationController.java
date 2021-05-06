package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import at.qe.skeleton.ui.controllers.demo.UserStatusController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;

/**
 * Controller for the user view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserCreationController extends Controller implements Serializable {

    @Autowired
    private UserService userService;

    /**
     * Attribute to cache the currently displayed user
     */
    private User user;

    @PostConstruct
    public void init() {
        doCreateNewUser();
    }

    @Autowired
    UserStatusController userStatusController;

    public void doCreateNewUser() {
        user = new User();
        user.setEnabled(true);
    }

    /**
     * Sets the currently displayed user and reloads it form db. This user is
     * targeted by any further calls of
     * {@link #doReloadUser()}, {@link #doSaveUser()} and
     *
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
        doReloadUser();
    }

    public void createUser() {
        user = new User();
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
     * Action to save the currently displayed user.
     */
    public void doSaveUser() {
        try {
            if(user.getRoles().isEmpty())
                user.setRoles(Collections.singleton(UserRole.PLAYER));
            if(!userService.isUsernameAlreadyTaken(user)) {
                user = userService.saveUser(user);
                displayInfo("User created", "");
                userStatusController.addUserStatus(user);
            }
            else
                displayInfo("User not created, username already exists", "");
        } catch (IllegalArgumentException e) {
            displayError("Error", e.getMessage());
        }
    }

    public List<UserRole> getListRoles(){
        List<UserRole> list = new ArrayList<>();
        list.add(UserRole.ADMIN);
        list.add(UserRole.GAME_MANAGER);
        list.add(UserRole.PLAYER);
        return list;
    }
}
