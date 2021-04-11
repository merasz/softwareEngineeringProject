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
    private Date lastSynchronization;
    private int batteryPercentage;
    private int calibrationNumber;

    @OneToOne
    private Raspberry raspberry;

    @ManyToOne
    private TimeFlipSetup timeFlipSetup;

    private boolean inUse;

    @ElementCollection(targetClass = TimeFlipSetupStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Timeflip_TimeflipSetupStatuts")
    @Enumerated(EnumType.STRING)
    private Set<TimeFlipSetupStatus> timeFlipSetupStatuses;

    @OneToMany(mappedBy = "timeFlip")
    private List<Task> tasks;

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

    public TimeFlip(int timeFlipId, String timeFlipName, Date lastSynchronization, int batteryPercentage, int calibrationNumber, Raspberry raspberry, boolean inUse) {
        this.timeFlipId = timeFlipId;
        this.timeFlipName = timeFlipName;
        this.lastSynchronization = lastSynchronization;
        this.batteryPercentage = batteryPercentage;
        this.calibrationNumber = calibrationNumber;
        this.raspberry = raspberry;
        this.inUse = inUse;
    }

    public TimeFlip() {
    }
}
