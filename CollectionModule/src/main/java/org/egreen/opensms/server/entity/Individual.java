package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "individual")
@JsonIgnoreProperties
public class Individual implements EntityInterface <String> {

    private String individualId;
    private String name;
    private String contactNum;
    private String location;
    private String nic;
    private String center;
    private BigDecimal commision;
    private BigDecimal deductionInv;
    private BigDecimal lessComissionSingle;
    private BigDecimal rent;
    private boolean isSalaryPay;
    private BigDecimal pcChargers;
    private BigDecimal fixedSalary;
    private BigDecimal notCommisionPersentage;
    private Integer type;


    @Id
    @Column(name = "individualId")
    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public BigDecimal getDeductionInv() {
        return deductionInv;
    }

    public void setDeductionInv(BigDecimal deductionInv) {
        this.deductionInv = deductionInv;
    }

    public BigDecimal getLessComissionSingle() {
        return lessComissionSingle;
    }

    public void setLessComissionSingle(BigDecimal lessComissionSingle) {
        this.lessComissionSingle = lessComissionSingle;
    }

    public BigDecimal getPcChargers() {
        return pcChargers;
    }

    public void setPcChargers(BigDecimal pcChargers) {
        this.pcChargers = pcChargers;
    }

    public boolean isSalaryPay() {
        return isSalaryPay;
    }

    public void setSalaryPay(boolean salaryPay) {
        isSalaryPay = salaryPay;
    }

    public BigDecimal getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(BigDecimal fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public BigDecimal getNotCommisionPersentage() {
        return notCommisionPersentage;
    }

    public void setNotCommisionPersentage(BigDecimal notCommisionPersentage) {
        this.notCommisionPersentage = notCommisionPersentage;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    @Transient
    public String getId() {
        return getIndividualId();
    }

    @Override
    public String toString() {
        return "Individual{" +
                "individualId='" + individualId + '\'' +
                ", name='" + name + '\'' +
                ", contactNum='" + contactNum + '\'' +
                ", location='" + location + '\'' +
                ", nic='" + nic + '\'' +
                ", center='" + center + '\'' +
                ", commision=" + commision +
                ", lessComissionSingle=" + lessComissionSingle +
                ", rent=" + rent +
                ", isSalaryPay=" + isSalaryPay +
                ", pcChargers=" + pcChargers +
                ", fixedSalary=" + fixedSalary +
                ", notCommisionPersentage=" + notCommisionPersentage +
                ", type=" + type +
                '}';
    }
}
