package at.qe.skeleton.api.model;

import java.util.Objects;

public class PiRequest {

    private long id;
    private String ipAddress;
    private int facetId;
    private String val;

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

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiRequest piRequest = (PiRequest) o;
        return id == piRequest.id &&
                facetId == piRequest.facetId &&
                ipAddress.equals(piRequest.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ipAddress, facetId);
    }
}
