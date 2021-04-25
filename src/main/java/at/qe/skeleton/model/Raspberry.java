package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Raspberry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int raspberryId;

    private String hostname;

    //TODO: review this, many raspberries for one user?
    @ManyToOne
    private User user;

    private boolean inUse;
    private String ipAddress;
    private String apiKey;

    @OneToOne
    private TimeFlip timeFlip;

    public Raspberry() {
    }

    public Raspberry(int raspberryId, String hostname, User user, boolean inUse, String ipAddress, TimeFlip timeFlip) {
        this.raspberryId = raspberryId;
        this.hostname = hostname;
        this.user = user;
        this.inUse = inUse;
        this.ipAddress = ipAddress;
        this.timeFlip = timeFlip;
    }

    public int getRaspberryId() {
        return raspberryId;
    }

    public void setRaspberryId(int raspberryId) {
        this.raspberryId = raspberryId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getApiKey() { return apiKey; }

    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public Raspberry(int raspberryId, String hostname, User user, boolean inUse, String ipAddress) {
        this.raspberryId = raspberryId;
        this.hostname = hostname;
        this.user = user;
        this.inUse = inUse;
        this.ipAddress = ipAddress;
    }

    public TimeFlip getTimeFlip() {
        return timeFlip;
    }

    public void setTimeFlip(TimeFlip timeFlip) {
        this.timeFlip = timeFlip;
    }
}
