package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deviceId;

    private String deviceIdIp;

    @OneToMany(mappedBy = "device")
    private List<User> userDevice;

    public Device() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public List<User> getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(List<User> userDevice) {
        this.userDevice = userDevice;
    }

    public Device(int deviceId, String deviceIdIp, List<User> userDevice) {
        this.deviceId = deviceId;
        this.deviceIdIp = deviceIdIp;
        this.userDevice = userDevice;
    }
}
