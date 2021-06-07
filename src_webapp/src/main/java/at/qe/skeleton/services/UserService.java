package at.qe.skeleton.services;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.UserRepository;

import java.util.*;
import at.qe.skeleton.ui.controllers.demo.ChatManagerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Service for accessing and manipulating user data.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("application")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChatManagerController chatManagerController;

    /**
     * Returns a collection of all users.
     *
     * @return
     */
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a single user identified by its username.
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('GAME_MANAGER')")
    public User loadUser(String username) {
        return userRepository.findFirstByUsername(username);
    }

    /**
     * Saves the user. This method will also set {@link User#createDate} for new
     * entities or {@link User#updateDate} for updated entities. The user
     * requesting this operation will also be stored as {@link User#createDate}
     * or {@link User#updateUser} respectively.
     *
     * @param user the user to save
     * @return the updated user
     */
    //@PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public User saveUser(User user) {
        if(user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(UserRole.PLAYER));
        }

        // get creator user ('signupBot' when created via signup)
        User authenticator = getAuthenticatedUser();
        if (authenticator == null) {
            authenticator = userRepository.findFirstByUsername("signupBot");
        }

        if (user.isNew()) {
            user.setCreateDate(new Date());
            user.setEnabled(true);
            user.setCreateUser(authenticator);
        } else {
            user.setUpdateDate(new Date());
            user.setUpdateUser(authenticator);
        }
        return userRepository.save(user);
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    //@PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public void deleteUser(User user) throws IllegalArgumentException {
        if (chatManagerController.getPossibleRecipients().contains(user)) {
            throw new IllegalArgumentException("User is currently logged in and therefore can't be deleted.");
        }

        if (user.isEnabled()) {
            throw new IllegalArgumentException("User must be disabled before deleting.");
        }

        // Last Admin must not be deleted
        if (user.getRoles().contains(UserRole.ADMIN) && userRepository.findByRole(UserRole.ADMIN).size() < 2) {
            throw new IllegalArgumentException("Only existing admin cannot be deleted.");
        }

        this.scoreRepository.findAllByUser(user).forEach(score -> this.scoreRepository.delete(score));

        for (Team t : user.getTeam()) {
            List<User> players = t.getTeamPlayers();
            players.remove(user);
            t.setTeamPlayers(players);
            teamRepository.save(t);
        }
        userRepository.delete(user);
    }

    //region getter & setter
    public Collection<User> getAllAdmins() {
        return userRepository.findAllAdmins();
    }

    public Collection<User> getAllManagers() {
        return userRepository.findAllManagers();
    }

    public Collection<User> getAllPlayers() {
        return userRepository.findAllPlayers();
    }

    private User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findFirstByUsername(auth.getName());
    }

    public List<User> getUserByTeam(Team team) {
        return userRepository.findAllPlayersByTeam(team);
    }

    public boolean isUsernameAlreadyTaken(User user) {
        return userRepository.findAll().contains(user);
    }

    public List<User> getUserByRaspberry(Raspberry raspberry) {
        return userRepository.findAllByRaspberryAndRaspberryNotNull(raspberry);
    }
    //endregion
}
