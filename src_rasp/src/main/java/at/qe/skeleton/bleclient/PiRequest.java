package at.qe.skeleton.bleclient;

public class PiRequest {

    private long id;
    private String ipAddress;
    private int facetId;
    private int battery;
    private int numElements;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String macAddress) {
        this.ipAddress = macAddress;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getNumElements() {
        return numElements;
    }

    public void setNumElements(int numElements) {
        this.numElements = numElements;
    }
}
