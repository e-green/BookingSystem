package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by ruwan on 5/20/16.
 */
@Entity
@Table(name = "centersummerycheck")
@JsonIgnoreProperties
public class CenterSummeryCheck implements EntityInterface<String>{

    private String centersummerycheck;
    private String centerId;
    private Boolean summaryFinish;
    private String sDate;

    @Id
    @Column(name = "centersummerycheck")
    public String getCentersummerycheck() { return centersummerycheck; }

    public void setCentersummerycheck(String centersummerycheck) { this.centersummerycheck = centersummerycheck; }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public Boolean getSummaryFinish() {
        return summaryFinish;
    }

    public void setSummaryFinish(Boolean summaryFinish) {
        this.summaryFinish = summaryFinish;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    @Override
    @Transient
    public String getId() {
        return getCenterId();
    }

    @Override
    public String toString() {
        return "CenterSummeryCheck{" +
                "centerId='" + centerId + '\'' +
                ", summaryFinish=" + summaryFinish +
                ", sDate='" + sDate + '\'' +
                '}';
    }
}
