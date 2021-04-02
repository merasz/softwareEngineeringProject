package at.qe.skeleton.services;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;
import at.qe.skeleton.repositories.UserRepository;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public User saveUser(User user) {
        if (user.isNew()) {
            user.setCreateDate(new Date());
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(User user) throws IllegalArgumentException {
        // Last Admin can't be deleted
        if (user.getRoles().contains(UserRole.ADMIN) && userRepository.findByRole(UserRole.ADMIN).size() < 2) {
            throw new IllegalArgumentException("Only existing admin cannot be deleted.");
        }

        //TODO: delete scores
        this.scoreRepository.findAllByUser(user).stream().forEach(score -> this.scoreRepository.delete(score));

        userRepository.delete(user);
    }

    public User saveNewUser(String username, String password, String firstName, String lastName, String email, Set<UserRole> roles) throws IllegalArgumentException, NullPointerException {
        User user = createNewUser(username, password, firstName, lastName, email, roles);
        return saveUser(user);
    }

    public User saveEditedUser(User user) throws IllegalArgumentException {
        validateInput(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRoles());
        return saveUser(user);
    }

    private User createNewUser(String username, String password, String firstName, String lastName, String email, Set<UserRole> roles) throws IllegalArgumentException, NullPointerException {
        validateInput(username, password, firstName, lastName, roles);
        validateUsername(username);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEnabled(true);
        user.setRoles(roles);

        return user;
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public User updatePassword(User user, String password) throws IllegalArgumentException {
        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        user.setPassword(password.trim());
        return saveUser(user);
    }

    private void validateInput(String username, String password, String firstName, String lastName, Set<UserRole> roles) throws IllegalArgumentException, NullPointerException {
        if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || roles.isEmpty()) {
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

}
