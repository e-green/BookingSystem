package org.egreen.opensms.server.models;

import java.math.BigDecimal;

/**
 * Created by ruwan on 2/15/16.
 */
public class AllDetailModel {

    private int usersCount;
    private int dueLoanCount;
    private int centerCount;
    private int individualCount;


    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getDueLoanCount() {
        return dueLoanCount;
    }

    public void setDueLoanCount(int dueLoanCount) {
        this.dueLoanCount = dueLoanCount;
    }

    public int getCenterCount() {
        return centerCount;
    }

    public void setCenterCount(int centerCount) {
        this.centerCount = centerCount;
    }

    public int getIndividualCount() {
        return individualCount;
    }

    public void setIndividualCount(int individualCount) {
        this.individualCount = individualCount;
    }
}
