package org.egreen.opensms.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by ruwan on 2/11/16.
 */
@Entity
@Table(name = "reportlog")
@JsonIgnoreProperties
public class ReportLog implements EntityInterface <String>{

    private String rplogid;
    private Timestamp datetime;
    private String centerid;
    private String stime;
    private boolean print;


    @Id
    @Column(name = "rplogid")
    public String getRplogid() {
        return rplogid;
    }

    public void setRplogid(String rplogid) {
        this.rplogid = rplogid;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getCenterid() {
        return centerid;
    }

    public void setCenterid(String centerid) {
        this.centerid = centerid;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }


    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    @Override
    @Transient
    public String getId() {
        return getRplogid();
    }

    @Override
    public String toString() {
        return "ReportLog{" +
                "rplogid='" + rplogid + '\'' +
                ", datetime=" + datetime +
                ", centerid='" + centerid + '\'' +
                ", stime='" + stime + '\'' +
                ", print=" + print +
                '}';
    }
}
