package at.qe.skeleton.api.model;

import java.util.Objects;

public class PiRequest {

    private long id;
    private String macAddress;
    private int facetId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getFacetId() {
        return facetId;
    }

    public void setFacetId(int facetId) {
        this.facetId = facetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiRequest piRequest = (PiRequest) o;
        return id == piRequest.id &&
                facetId == piRequest.facetId &&
                macAddress.equals(piRequest.macAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, macAddress, facetId);
    }
}
