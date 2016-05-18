package org.egreen.opensms.server.models;

/**
 * Created by ruwan on 5/18/16.
 */
public class ChitCountListModel {
    private int chitCount;

    public int getChitCount() {
        return chitCount;
    }

    public void setChitCount(int chitCount) {
        this.chitCount = chitCount;
    }

    @Override
    public String toString() {
        return "ChitCountListModel{" +
                "chitCount=" + chitCount +
                '}';
    }
}
