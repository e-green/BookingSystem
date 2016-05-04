package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "envelop")
@JsonIgnoreProperties
public class Envelope implements EntityInterface<String> {

    private String envelopId;
    private String name;
    private Timestamp date;
    private String chitNumbers;
    private BigDecimal invesment;
    private BigDecimal cash;
    private BigDecimal notCommision;
    private BigDecimal expences;
    private BigDecimal salary;
    private Boolean isLessCommisionSingle;
    private BigInteger chitCount;
    private BigInteger commision;
    private String center;
    private String individualId;
    private Boolean isLoanDeduct;
    private Boolean rentDeduct;
    private Boolean finished;
    private String sTime;




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

    public Boolean getLessCommisionSingle() {
        return isLessCommisionSingle;
    }

    public void setLessCommisionSingle(Boolean lessCommisionSingle) {
        isLessCommisionSingle = lessCommisionSingle;
    }

    public BigDecimal getNotCommision() {
        return notCommision;
    }

    public void setNotCommision(BigDecimal notCommision) {
        this.notCommision = notCommision;
    }

    public BigDecimal getExpences() {
        return expences;
    }

    public void setExpences(BigDecimal expences) {
        this.expences = expences;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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

    public BigInteger getChitCount() {
        return chitCount;
    }

    public void setChitCount(BigInteger chitCount) {
        this.chitCount = chitCount;
    }

    public BigInteger getCommision() {
        return commision;
    }

    public void setCommision(BigInteger commision) {
        this.commision = commision;
    }

    public Boolean getLoanDeduct() {
        return isLoanDeduct;
    }

    public void setLoanDeduct(Boolean loanDeduct) {
        isLoanDeduct = loanDeduct;
    }

    public Boolean getRentDeduct() {
        return rentDeduct;
    }

    public void setRentDeduct(Boolean rentDeduct) {
        this.rentDeduct = rentDeduct;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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
                ", expences=" + expences +
                ", salary=" + salary +
                ", isLessCommisionSingle=" + isLessCommisionSingle +
                ", chitCount=" + chitCount +
                ", commision=" + commision +
                ", center='" + center + '\'' +
                ", individualId='" + individualId + '\'' +
                ", isLoanDeduct=" + isLoanDeduct +
                ", rentDeduct=" + rentDeduct +
                ", finished=" + finished +
                ", sTime='" + sTime + '\'' +
                '}';
    }
}
