package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "envelop")
@JsonIgnoreProperties
public class Envelope implements EntityInterface <String> {

    private String     envelopId;
    private String     name;
    private Timestamp  date;
    private String     chitNumbers;
    private BigDecimal invesment;
    private BigDecimal cash;
    private BigDecimal notCommision;
    private BigDecimal pcChargers;
    private String     center;
    private String     individualId;


    @Id
    @Column(name = "envelopId")
    public String getEnvelopId() {
        return envelopId;
    }

    public void setEnvelopId(String envelopId) {
        this.envelopId = envelopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getChitNumbers() {
        return chitNumbers;
    }

    public void setChitNumbers(String chitNumbers) {
        this.chitNumbers = chitNumbers;
    }

    public BigDecimal getInvesment() {
        return invesment;
    }

    public void setInvesment(BigDecimal invesment) {
        this.invesment = invesment;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getNotCommision() {
        return notCommision;
    }

    public void setNotCommision(BigDecimal notCommision) {
        this.notCommision = notCommision;
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

    public BigDecimal getPcChargers() {
        return pcChargers;
    }

    public void setPcChargers(BigDecimal pcChargers) {
        this.pcChargers = pcChargers;
    }

    @Override
    @Transient
    public String getId() {
        return getEnvelopId();
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "envelopId='" + envelopId + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", chitNumbers='" + chitNumbers + '\'' +
                ", invesment=" + invesment +
                ", cash=" + cash +
                ", notCommision=" + notCommision +
                ", pcChargers=" + pcChargers +
                ", center='" + center + '\'' +
                ", individualId='" + individualId + '\'' +
                '}';
    }
}
