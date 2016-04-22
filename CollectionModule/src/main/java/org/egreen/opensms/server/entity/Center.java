package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "center")
@JsonIgnoreProperties
public class Center implements EntityInterface <String> {

    private String centerid;
    private String name;
    private String location;
    private String description;
    private Integer orderBy;
    private BigDecimal commision;
    private BigDecimal lessComissionSingle;
    private BigDecimal pcChargers;
    private BigDecimal notCommisionPersentage;
    private Integer type;


    @Id
    @Column(name = "centerid")
    public String getCenterid() { return centerid; }

    public void setCenterid(String centerid) {
        this.centerid = centerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
    }

    public BigDecimal getLessComissionSingle() {
        return lessComissionSingle;
    }

    public void setLessComissionSingle(BigDecimal lessComissionSingle) {
        this.lessComissionSingle = lessComissionSingle;
    }

    public BigDecimal getNotCommisionPersentage() {
        return notCommisionPersentage;
    }

    public void setNotCommisionPersentage(BigDecimal notCommisionPersentage) {
        this.notCommisionPersentage = notCommisionPersentage;
    }

    public BigDecimal getPcChargers() {
        return pcChargers;
    }

    public void setPcChargers(BigDecimal pcChargers) {
        this.pcChargers = pcChargers;
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
        return getCenterid();
    }

    @Override
    public String toString() {
        return "Center{" +
                "centerid='" + centerid + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", orderBy=" + orderBy +
                ", commision=" + commision +
                ", lessComissionSingle=" + lessComissionSingle +
                ", pcChargers=" + pcChargers +
                ", notCommisionPersentage=" + notCommisionPersentage +
                ", type=" + type +
                '}';
    }
}
