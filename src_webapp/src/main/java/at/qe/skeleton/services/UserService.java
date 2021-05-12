package at.qe.skeleton.services;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

import org.primefaces.shaded.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Returns a collection of all users.
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Loads a single user identified by its username.
     *
     * @param username the username to search for
     * @return the user with the given username
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
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
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public User saveUser(User user) {
        if (user.isNew()) {
            user.setCreateDate(new Date());
            user.setEnabled(true);
            user.setCreateUser(getAuthenticatedUser());
        } else {
            user.setUpdateDate(new Date());
            user.setUpdateUser(getAuthenticatedUser());
        }
        return userRepository.save(user);
    }

    /**
     * Deletes the user.
     *
     * @param user the user to delete
     */
    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public void deleteUser(User user) throws IllegalArgumentException {
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

    /*
    public User createUser(User user, String username, String password) throws IllegalArgumentException, NullPointerException {
        validateInput(username, password);
        validateUsername(username);

        Set<UserRole> roles = user.getRoles();
        if (user.isNew()) {
            user = new User();
        }
        //TODO: check
        if (roles.isEmpty()) {
            roles = new HashSet<>(Collections.singletonList(UserRole.PLAYER));
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setRoles(roles);

        return saveUser(user);
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #user.username")
    public User updatePassword(User user, String password, String confirm) throws IllegalArgumentException {
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        } else if (!password.equals(confirm)) {
            throw new IllegalArgumentException("Confirmed password differs.");
        }

        user.setPassword(password.trim());
        return saveUser(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateRoles(User user, UserRole[] roles) {
        Set<UserRole> ur = new HashSet<>(Arrays.asList(roles));
        user.setRoles(ur);

        return userRepository.save(user);
    }
    */

    public Collection<User> getAllAdmins() {
        return userRepository.findAllAdmins();
    }

    public Collection<User> getAllManagers() {
        return userRepository.findAllManagers();
    }

    public Collection<User> getAllPlayers() {
        return userRepository.findAllPlayers();
    }


    private void validateInput(String username, String password) throws IllegalArgumentException, NullPointerException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("All fields need to be filled.");
        }
    }

    private void validateUsername(String username) throws IllegalArgumentException {
        User user = userRepository.findFirstByUsername(username);
        if (user != null) {
            throw new IllegalArgumentException("A user with this username does already exist.");
        }
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
        return userRepository.findAllByRaspberry(raspberry);
    }
}