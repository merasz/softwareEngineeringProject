package at.qe.skeleton.repositories;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.UserRole;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing {@link User} entities.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
public interface UserRepository extends AbstractRepository<User, String> {

    /**
     * Returns the first user with the given name.
     *
     * @param username
     * @return user
     */
    User findFirstByUsername(String username);

    /**
     * Returns a list of users with the given role
     * ADMIN
     * MANAGER
     * USER
     * @param role
     * @return list of users
     */
    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") UserRole role);

    /**
     * Returns a list of all admins.
     *
     * @return list of users
     */
    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.ADMIN")
    List<User> findAllAdmins();

    /**
     * Returns a list of all managers.
     *
     * @return list of users
     */
    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.GAME_MANAGER")
    List<User> findAllManagers();

    /**
     * Return all users with its given role Player.
     *
     * @return list of users
     */
    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.PLAYER")
    List<User> findAllPlayers();

    /**
     * Returns a list of all Users in a team.
     * @param team
     * @return
     */
    List<User> findAllPlayersByTeam(Team team);

    /**
     * Find all Users who have added this specified raspberry to their profile,
     * its used to see who is available for this game.
     *
     * @param raspberry
     * @return List of users
     */
    List<User> findAllByRaspberry(Raspberry raspberry);
}
