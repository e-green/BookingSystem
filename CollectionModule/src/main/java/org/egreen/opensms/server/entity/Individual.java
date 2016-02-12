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
    private BigDecimal pcChargers;

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

    private BigDecimal notCommision;
    public BigDecimal getCommision() {
        return commision;
    }

    public void setCommision(BigDecimal commision) {
        this.commision = commision;
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
                ", pcChargers=" + pcChargers +
                ", notCommision=" + notCommision +
                '}';
    }

}
