package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.LoggerDAOController;
import org.egreen.opensms.server.dao.IndividualDAOController;
import org.egreen.opensms.server.entity.Logger;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class LoggerDAOService {

    @Autowired
    private LoggerDAOController centerDAOController;

    @Autowired
    private IndividualDAOController individualDAOController;

    private List<Logger> all;
    private String id;


    /**
     * Center SignUp
     *
     * @param center
     * @return
     */
    public String save(Logger center) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
       // center.setCenterid(newid);


        String s = centerDAOController.create(center);
        return s;
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


    public List<Logger> getAll() {
        return centerDAOController.getAll();
    }

    public List<Logger> getAllCenterByPagination(String quary, Integer limit, Integer offset) {
        return centerDAOController.getAllCenterersByPagination(quary, limit, offset);
    }

    public String update(Logger Center) {
        return centerDAOController.update(Center);
    }

    public Integer removeCenterById(String centerid) {
        return centerDAOController.removeCenterById(centerid);
    }

    public String getId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(2);
        return newid;
    }

    public List<Logger> getAllCentersByBranchId(String branchid) {

        return centerDAOController.getAllCentersByBranchId(branchid);
    }

    public String getNextId(String branchID) {
//        Branch branch = individualDAOController.read(branchID);
        String centerCode = null;
        long centerCount = centerDAOController.getCenterCountByBranch(branchID);
        centerCount++;
        Individual branch = individualDAOController.read(branchID);
        if (branch != null) {
            String city = branch.getName().substring(0, 3);
            centerCode = city + "/" + centerCount;
        }
        return centerCode;
    }

    public Logger getCenterById(String centerid) {
        return centerDAOController.read(centerid);
    }


    public List<Logger> getAllByPagination(Integer limit, Integer offset) {
        return centerDAOController.getAll(offset, limit, "name");
    }

    public boolean checkIfExist(String centerName) {
        return centerDAOController.checkIfExist(centerName);
    }
}
