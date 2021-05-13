package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.services.UserService;
import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Controller for the user list view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private UserService userService;

    private String option;

    @PostConstruct
    public void init() {
        setOption("all");
    }
    /**
     * Returns a list of all users.
     *
     * @return
     */
    public Collection<User> getUsers() {
        if(getOption().equals("all")) {
            return userService.getAllUsers();
        } else if(getOption().equals("admin")) {
            return userService.getAllAdmins();
        } else if(getOption().equals("manager")) {
            return userService.getAllManagers();
        } else if (getOption().equals("player")) {
            return userService.getAllPlayers();
        } else {
            return null;
        }
    }

    public Collection<User> getAllPlayers(){
        return userService.getAllPlayers();
    }

    public String getOption() { return option; }

    public void setOption(String option) {
        this.option = option;
    }

}
