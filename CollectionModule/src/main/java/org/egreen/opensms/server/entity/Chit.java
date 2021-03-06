package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "chit")
@JsonIgnoreProperties
public class Chit implements EntityInterface <String> {

    private String chitId;
    private String centerid;
    private String individualId;
    private String envelopeId;
    private Timestamp datetime;
    private String number;
    private BigDecimal invesment;
    private BigDecimal amount;
    private String remark;
    private Boolean status;


    @Id
    @Column(name = "chitId")
    public String getChitId() {
        return chitId;
    }

    public void setChitId(String chitId) {
        this.chitId = chitId;
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

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getInvesment() {
        return invesment;
    }

    public void setInvesment(BigDecimal invesment) {
        this.invesment = invesment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    @Override
    @Transient
    public String getId() {
        return getChitId();
    }


    @Override
    public String toString() {
        return "Chit{" +
                "chitId='" + chitId + '\'' +
                ", centerid='" + centerid + '\'' +
                ", individualId='" + individualId + '\'' +
                ", envelopeId='" + envelopeId + '\'' +
                ", datetime=" + datetime +
                ", number='" + number + '\'' +
                ", invesment=" + invesment +
                ", amount=" + amount +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
