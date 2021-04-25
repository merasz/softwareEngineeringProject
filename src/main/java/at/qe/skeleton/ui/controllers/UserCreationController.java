package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void doCreateNewUser() {
        user = new User();
        user.setEnabled(true);
    }

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
     * Action to save the currently displayed user.
     */
    public void doSaveUser() {
        try {
            Set<UserRole> val = new HashSet<>();
            val.add(UserRole.PLAYER);
            user.setRoles(val);
            user = userService.saveUser(user);
            displayInfo("Player created", "You have been successfully registered. You can log in now.");
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
