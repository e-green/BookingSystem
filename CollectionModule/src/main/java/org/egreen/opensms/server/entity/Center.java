package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

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
    private Boolean hasCommision;


    @Id
    @Column(name = "centerid")
    public String getCenterid() {
        return centerid;
    }

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

    public Boolean getHasCommision() {
        return hasCommision;
    }

    public void setHasCommision(Boolean hasCommision) {
        this.hasCommision = hasCommision;
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
                ", hasCommision=" + hasCommision +
                '}';
    }


}
