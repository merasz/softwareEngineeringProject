package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class VirtualRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int virtualRoomId;

    private int totalScore;
    private int nrRound;
    private int raspberryId;

    @OneToMany(mappedBy = "virtualRoom")
    private List<Team> playerList;

    //private List<Integer> deviceTeamIdList;

    //@OneToOne
    //private GameConfig gameconfig;

    //@OneToMany(mappedBy = "score")
    //private List<Score> score;

    public VirtualRoom() {
    }

    public VirtualRoom(int virtualRoom, int totalScore, int nrRound, int raspberryId, List<Team> playerList) {
        this.virtualRoomId = virtualRoomId;
        this.totalScore = totalScore;
        this.nrRound = nrRound;
        this.raspberryId = raspberryId;
        this.playerList = playerList;

    }

    public int getVirtualRoomId() {
        return virtualRoomId;
    }

    public void setVirtualRoomId(int virtualRoomId) {
        this.virtualRoomId = virtualRoomId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNrRound() {
        return nrRound;
    }

    public void setNrRound(int nrRound) {
        this.nrRound = nrRound;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public List<Team> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Team> playerList) {
        this.playerList = playerList;
    }

}
