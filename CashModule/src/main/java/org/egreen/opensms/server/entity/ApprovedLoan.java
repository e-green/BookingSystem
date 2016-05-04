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
    private BigDecimal deductionPayment;
    private Timestamp duedate;
    private BigDecimal dueamount;
    private String sTime;


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

    public BigDecimal getDeductionPayment() {
        return deductionPayment;
    }

    public void setDeductionPayment(BigDecimal deductionPayment) {
        this.deductionPayment = deductionPayment;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
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
                ", deductionPayment=" + deductionPayment +
                ", duedate=" + duedate +
                ", dueamount=" + dueamount +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
