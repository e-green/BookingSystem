package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "logger")
@JsonIgnoreProperties
public class Logger implements EntityInterface <String> {

    private String logid;
    private String refId;
    private Timestamp tableDate;
    private String individualId;
    private String reason;
    private BigDecimal amount;
    private Boolean inoutStatus;
    private Timestamp date;
    private Boolean isPay;


    @Id
    @Column(name = "logid")
    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public Timestamp getTableDate() {
        return tableDate;
    }

    public void setTableDate(Timestamp tableDate) {
        this.tableDate = tableDate;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getInoutStatus() {
        return inoutStatus;
    }

    public void setInoutStatus(Boolean inoutStatus) {
        this.inoutStatus = inoutStatus;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getIsPay() {
        return isPay;
    }

    public void setIsPay(Boolean isPay) {
        this.isPay = isPay;
    }





    @Override
    @Transient
    public String getId() {
        return getLogid();
    }

    @Override
    public String toString() {
        return "Center{" +
                "logid='" + logid + '\'' +
                ", refId='" + refId + '\'' +
                ", tableDate=" + tableDate +
                ", individualId='" + individualId + '\'' +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                ", inoutStatus=" + inoutStatus +
                ", date=" + date +
                ", isPay=" + isPay +
                '}';
    }
}
