package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "branchpermission")
@JsonIgnoreProperties
public class BranchPermission implements EntityInterface <String> {

    private String branchpermissionid;
    private String adminId;
    private String branchId;
    private Timestamp datetime;


    @Id
    @Column(name = "branchpermissionid")
    public String getBranchpermissionid() {
        return branchpermissionid;
    }

    public void setBranchpermissionid(String branchpermissionid) {
        this.branchpermissionid = branchpermissionid;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    @Transient
    public String getId() {
        return getBranchpermissionid();
    }


    @Override
    public String toString() {
        return "BranchPermission{" +
                "branchpermissionid='" + branchpermissionid + '\'' +
                ", adminId='" + adminId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
