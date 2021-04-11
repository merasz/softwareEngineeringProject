package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private int facetId;
    private int durationInMinutes;
    private int pointsForTask;

    @ManyToOne
    private TimeFlip timeFlip;

    @ElementCollection(targetClass = RequestType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Task_RequestType")
    @Enumerated(EnumType.STRING)
    private Set<RequestType> requestTypes;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getPointsForTask() {
        return pointsForTask;
    }

    public void setPointsForTask(int pointsForTask) {
        this.pointsForTask = pointsForTask;
    }

    public Task() {
    }

    public Task(int taskId, int facetId, int durationInMinutes, int pointsForTask) {
        this.taskId = taskId;
        this.facetId = facetId;
        this.durationInMinutes = durationInMinutes;
        this.pointsForTask = pointsForTask;
    }
}
