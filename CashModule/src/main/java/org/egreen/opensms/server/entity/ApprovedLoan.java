package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "approvedloan")
@JsonIgnoreProperties
public class ApprovedLoan implements EntityInterface<String> {


    private String approvedloanId;
    private String center;
    private String individualId;
    private Timestamp datetime;
    private BigDecimal amount;
    private String duration;
    private Timestamp duedate;
    private BigDecimal dueamount;


    @Id
    @Column(name = "approvedloanId")
    public String getApprovedloanId() {
        return approvedloanId;
    }

    public void setApprovedloanId(String approvedloanId) {
        this.approvedloanId = approvedloanId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Timestamp getDuedate() {
        return duedate;
    }

    public void setDuedate(Timestamp duedate) {
        this.duedate = duedate;
    }

    public BigDecimal getDueamount() {
        return dueamount;
    }

    public void setDueamount(BigDecimal dueamount) {
        this.dueamount = dueamount;
    }


    @Override
    @Transient
    public String getId() {
        return getApprovedloanId();
    }


    @Override
    public String toString() {
        return "ApprovedLoan{" +
                "approvedloanId='" + approvedloanId + '\'' +
                ", center='" + center + '\'' +
                ", individualId='" + individualId + '\'' +
                ", datetime=" + datetime +
                ", amount=" + amount +
                ", duration='" + duration + '\'' +
                ", duedate=" + duedate +
                ", dueamount=" + dueamount +
                '}';
    }
}
