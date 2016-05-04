package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "loanrequest")
@JsonIgnoreProperties
public class LoanRequest implements EntityInterface <String> {


    private String      loanRequestId;
    private String      centerid;
    private String      individualId;
    private BigDecimal  amount;
    private String      user;
    private Timestamp   requestDate;
    private boolean     status;
    private String      sTime;



    @Id
    @Column(name = "loanRequestId")
    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public String getCenterid() {
        return centerid;
    }

    public void setCenterid(String centerid) {
        this.centerid = centerid;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
    
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
        return getLoanRequestId();
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "loanRequestId='" + loanRequestId + '\'' +
                ", centerid='" + centerid + '\'' +
                ", individualId='" + individualId + '\'' +
                ", amount=" + amount +
                ", user='" + user + '\'' +
                ", requestDate=" + requestDate +
                ", status=" + status +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
