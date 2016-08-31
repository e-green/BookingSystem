package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ReportLogDAOController;
import org.egreen.opensms.server.entity.ReportLog;

import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ruwan on 2/11/16.
 */
@Service
public class ReportLogDAOService {

    @Autowired
    private ReportLogDAOController accountDAOController;

    /**
     * save ReportLog
     *
     * @param account
     * @return
     */
    public String save(ReportLog account) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        account.setRplogid(newid);
        account.setDatetime(new Timestamp(new Date().getTime()));
        return accountDAOController.create(account);
    }

    /**
     * update ReportLog
     *
     * @param account
     * @return
     */
    public String update(ReportLog account) {
        return accountDAOController.update(account);
    }

    /**
     * getAll accounts
     *
     * @return
     */
    public List<ReportLog> getAll() {
        return accountDAOController.getAll();
    }



    public ReportLog getById(String id){return  accountDAOController.read(id);}


    /**
     * Get Random String
     *
     * @param len
     * @return
     * @author Pramoda Nadeeshan Fernando
     * @version 1.0
     * @since 2015-02-12 4.26PM
     */
    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /**
     * get acocunt by center or individual id
     *
     *
     * @param centerId
     * @param sTime
     * @return
     */
    public ReportLog getReportLogByCenterAndStime(String centerId, String sTime){
        return accountDAOController.getReportLogByCenterAndStime(centerId,sTime);
    }




}
