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

    User findFirstByUsername(String username);

    List<User> findByUsernameContaining(String username);

    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.ADMIN")
    List<User> findAllAdmins();

    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.GAME_MANAGER")
    List<User> findAllManagers();

    @Query("SELECT u FROM User u join u.roles r WHERE r = at.qe.skeleton.model.UserRole.PLAYER")
    List<User> findAllPlayers();

    List<User> findAllPlayersByTeam(Team team);

    List<User> findAllByRaspberry(Raspberry raspberry);
}
