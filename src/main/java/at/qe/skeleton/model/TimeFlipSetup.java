package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TimeFlipSetup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeFlipSetupId;
    private int setupTime;
    private int facetId;

    @OneToMany(mappedBy = "timeFlipSetup")
    private List<TimeFlip> timeFlipList;

    public TimeFlipSetup() {
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

    public TimeFlipSetup(int timeFlipSetupId, int setupTime, int facetId) {
        this.timeFlipSetupId = timeFlipSetupId;
        this.setupTime = setupTime;
        this.facetId = facetId;
    }
}
