package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class GameLobby implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameLobbyId;

    private int userDeviceId;

    @ElementCollection
    private List<Integer> lastPlayedGameStats;

    @OneToMany(mappedBy = "gameLobby")
    private List<User> activeUsers;

    @ElementCollection
    private List<Integer> activeVirtualRooms;

    @OneToOne
    private User user;

    public GameLobby() {
    }

    public GameLobby(int userDeviceId, List<Integer> lastPlayedGameStats,
                     List<User> activeUsers, List<Integer> activeVirtualRooms, User user) {
        this.userDeviceId = userDeviceId;
        this.activeUsers = activeUsers;
        this.user = user;
        this.lastPlayedGameStats = lastPlayedGameStats;
        this.activeVirtualRooms = activeVirtualRooms;
    }

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

    public List<Integer> getLastPlayedGameStats() {
        return lastPlayedGameStats;
    }

    public void setLastPlayedGameStats(List<Integer> lastPlayedGameStats) {
        this.lastPlayedGameStats = lastPlayedGameStats;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Integer> getActiveVirtualRooms() {
        return activeVirtualRooms;
    }

    public void setActiveVirtualRooms(List<Integer> activeVirtualRooms) {
        this.activeVirtualRooms = activeVirtualRooms;
    }
}
