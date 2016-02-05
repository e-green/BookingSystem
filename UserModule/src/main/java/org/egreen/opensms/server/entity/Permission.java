package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by Pramoda Fernando on 3/18/2015.
 */
@Entity
@Table(name = "permission")
@JsonIgnoreProperties
public class Permission implements EntityInterface <String> {

    private String permissionid;
    private String adminId;
    private String permission;


    @Id
    @Column(name = "permissionid")
    public String getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(String permissionid) {
        this.permissionid = permissionid;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


    @Override
    @Transient
    public String getId() {
        return getPermissionid();
    }

    @Override
    public String toString() {
        return "Permission{" +
                "permissionid='" + permissionid + '\'' +
                ", adminId='" + adminId + '\'' +
                ", permission=" + permission +
                '}';
    }
}
