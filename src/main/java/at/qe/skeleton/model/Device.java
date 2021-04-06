package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deviceId;
    private String deviceIdIp;

    @OneToMany(mappedBy = "device")
    private List<User> users;

    public Device() {
    }

    public Device(int deviceId, String deviceIdIp) {
        this.deviceId = deviceId;
        this.deviceIdIp = deviceIdIp;
    }

    @OneToMany(mappedBy = "device")
    private List<User> userDevice;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceIdIp() {
        return deviceIdIp;
    }

    public void setDeviceIdIp(String deviceIdIp) {
        this.deviceIdIp = deviceIdIp;
    }
}
