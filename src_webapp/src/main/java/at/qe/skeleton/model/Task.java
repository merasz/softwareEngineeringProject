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

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    public Task() {
    }

    public Task(int facetId, int durationInMinutes, int pointsForTask, TimeFlip timeFlip, RequestType requestType) {
        this.facetId = facetId;
        this.durationInMinutes = durationInMinutes;
        this.pointsForTask = pointsForTask;
        this.timeFlip = timeFlip;
        this.requestType = requestType;
    }

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

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public TimeFlip getTimeFlip() {
        return timeFlip;
    }

    public void setTimeFlip(TimeFlip timeFlip) {
        this.timeFlip = timeFlip;
    }
}
