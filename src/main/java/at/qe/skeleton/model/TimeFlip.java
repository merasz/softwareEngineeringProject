package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class TimeFlip implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeFlipId;

    private String timeFlipName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSynchronization;
    private int batteryPercentage;
    private int calibrationNumber;

    @OneToOne
    private Raspberry raspberry;

    @ManyToOne
    private TimeFlipSetup timeFlipSetup;

    private boolean inUse;

    //TODO: multiple status instances per timeflip?
    @ElementCollection(targetClass = TimeFlipSetupStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Timeflip_TimeflipSetupStatuts")
    @Enumerated(EnumType.STRING)
    private Set<TimeFlipSetupStatus> timeFlipSetupStatuses;

    @OneToMany(mappedBy = "timeFlip")
    private List<Task> tasks;

    public TimeFlip() {
    }

    public TimeFlip(String timeFlipName, Date lastSynchronization, int batteryPercentage,
                    int calibrationNumber, Raspberry raspberry, TimeFlipSetup timeFlipSetup, boolean inUse,
                    Set<TimeFlipSetupStatus> timeFlipSetupStatuses, List<Task> tasks) {
        this.timeFlipName = timeFlipName;
        this.lastSynchronization = lastSynchronization;
        this.batteryPercentage = batteryPercentage;
        this.calibrationNumber = calibrationNumber;
        this.raspberry = raspberry;
        this.inUse = inUse;
        this.timeFlipSetup = timeFlipSetup;
        this.timeFlipSetupStatuses = timeFlipSetupStatuses;
        this.tasks = tasks;
    }

    public int getTimeFlipId() {
        return timeFlipId;
    }

    public void setTimeFlipId(int timeFlipId) {
        this.timeFlipId = timeFlipId;
    }

    public String getTimeFlipName() {
        return timeFlipName;
    }

    public void setTimeFlipName(String timeFlipName) {
        this.timeFlipName = timeFlipName;
    }

    public Date getLastSynchronization() {
        return lastSynchronization;
    }

    public void setLastSynchronization(Date lastSynchronization) {
        this.lastSynchronization = lastSynchronization;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public int getCalibrationNumber() {
        return calibrationNumber;
    }

    public void setCalibrationNumber(int calibrationNumber) {
        this.calibrationNumber = calibrationNumber;
    }

    public Raspberry getRaspberry() {
        return raspberry;
    }

    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public TimeFlipSetup getTimeFlipSetup() {
        return timeFlipSetup;
    }

    public void setTimeFlipSetup(TimeFlipSetup timeFlipSetup) {
        this.timeFlipSetup = timeFlipSetup;
    }

    public Set<TimeFlipSetupStatus> getTimeFlipSetupStatuses() {
        return timeFlipSetupStatuses;
    }

    public void setTimeFlipSetupStatuses(Set<TimeFlipSetupStatus> timeFlipSetupStatuses) {
        this.timeFlipSetupStatuses = timeFlipSetupStatuses;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
