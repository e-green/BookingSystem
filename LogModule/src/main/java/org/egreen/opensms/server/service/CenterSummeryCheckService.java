package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.CenterSummeryCheckDAOController;
import org.egreen.opensms.server.entity.CenterSummeryCheck;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

/**
 * Created by ruwan on 5/20/16.
 */
@Service
public class CenterSummeryCheckService {

    @Autowired
    private CenterSummeryCheckDAOController centerSummeryCheckDAOController;

    public String save(CenterSummeryCheck centerSummeryCheck) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        centerSummeryCheck.setCentersummerycheck(newid);
        return centerSummeryCheckDAOController.create(centerSummeryCheck);
    }

    public boolean getCenterSummeryCheck(String centerId, String sTime) {
        return centerSummeryCheckDAOController.getCenterSummeryCheck(centerId,sTime);
    }

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
}
