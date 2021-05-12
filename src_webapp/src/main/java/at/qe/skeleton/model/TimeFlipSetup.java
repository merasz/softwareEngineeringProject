package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TimeFlipSetup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeFlipSetupId;

    private int setupTime;
    private int facetId;

    @OneToMany(mappedBy = "timeFlipSetup")
    private List<TimeFlip> timeFlipList;

    public TimeFlipSetup() {
    }

    public TimeFlipSetup(int setupTime, int facetId, List<TimeFlip> timeFlipList) {
        this.setupTime = setupTime;
        this.facetId = facetId;
        this.timeFlipList = timeFlipList;
    }

    public int getTimeFlipSetupId() {
        return timeFlipSetupId;
    }

    public void setTimeFlipSetupId(int timeFlipSetupId) {
        this.timeFlipSetupId = timeFlipSetupId;
    }

    public int getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(int setupTime) {
        this.setupTime = setupTime;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<TimeFlip> getTimeFlipList() {
        return timeFlipList;
    }

    public void setTimeFlipList(List<TimeFlip> timeFlipList) {
        this.timeFlipList = timeFlipList;
    }
}
