package at.qe.skeleton.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class TimeFlipConf implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facetId;
    private int facetPoint;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    private int time;

    public TimeFlipConf() { }

    public Integer getFacetId() { return facetId; }

    public Integer getFacetPoint() {
        return facetPoint;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setFacetId(Integer facetId) { this.facetId = facetId; }

    public void setFacetPoint(Integer facetPoint) {
        this.facetPoint = facetPoint;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Integer getTime() { return time; }

    public void setTime(Integer time) { this.time = time; }

}