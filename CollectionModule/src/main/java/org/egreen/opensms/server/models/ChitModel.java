package org.egreen.opensms.server.models;

import java.sql.Timestamp;

/**
 * Created by ruwan on 3/1/16.
 */
public class ChitModel {

    private String individualId;
    private Timestamp datetime;
    private Integer type;
    private Integer limit;
    private Integer offset;



    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "ChitModel{" +
                "individualId='" + individualId + '\'' +
                ", datetime=" + datetime +
                ", type=" + type +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
