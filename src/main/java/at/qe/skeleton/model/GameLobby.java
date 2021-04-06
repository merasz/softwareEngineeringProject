package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class GameLobby implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameLobbyId;

    private int userDeviceId;
    //private List<Integer> lastPlayedGameStats;
    @OneToMany(mappedBy = "gameLobby")
    private List<User> activeUsers;
    //private List<Integer> activeVirtualRooms;

    @OneToOne
    private User user;

    public int getGameLobbyId() {
        return gameLobbyId;
    }

    public void setGameLobbyId(int gameLobbyId) {
        this.gameLobbyId = gameLobbyId;
    }

    public int getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(int userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<User> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameLobby(int gameLobbyId, int userDeviceId, List<Integer> lastPlayedGameStats, List<User> activeUsers, List<Integer> activeVirtualRooms, User user) {
        this.gameLobbyId = gameLobbyId;
        this.userDeviceId = userDeviceId;
        this.activeUsers = activeUsers;
        this.user = user;
    }

    public GameLobby() {
    }
}
