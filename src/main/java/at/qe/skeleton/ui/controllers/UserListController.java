package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.services.UserService;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

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

    private Collection<User> filteredList;
    private String option;

    @PostConstruct
    public void init() {
        setOption("showAll");
        setFilteredList(getUsers());
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

    public Collection<User> getFilteredList() {
        if(getOption().equals("showAll")) {
            return filteredList;
        } else if(getOption().equals("admin")) {
            return filteredList.stream().filter(u -> u.getRoles().contains(UserRole.ADMIN)).collect(Collectors.toList());
        } else if(getOption().equals("manager")) {
            return filteredList.stream().filter(u -> u.getRoles().contains(UserRole.GAME_MANAGER)).collect(Collectors.toList());
        } else if (getOption().equals("player")) {
            return filteredList.stream().filter(u -> u.getRoles().contains(UserRole.PLAYER)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public String getOption() { return option; }

    public void setOption(String option) {
        this.option = option;
    }

    public void setFilteredList(Collection<User> filteredList) {
        this.filteredList = filteredList;
    }

}
