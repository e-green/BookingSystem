package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ChitDAOController;
import org.egreen.opensms.server.entity.Chit;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Pramoda Fernando on 1/14/2015.
 */
@Service
public class ChitDAOService {

    @Autowired
    private ChitDAOController chitDAOController;
    
    private List<Chit> all;
    private String id;


    /**
     *
     * chit SignUp
     *
     * @param chit
     * @return
     */
    public String save(Chit chit) {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
       chit.setChitId(newid);
        chit.setDatetime(new Timestamp(new Date().getTime()));

        String s = chitDAOController.create(chit);
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


    public List<Chit> getAll() {
        return chitDAOController.getAll();
    }

    public List<Chit> getAllBranchersByPagination(Integer limit, Integer offset) {
        return chitDAOController.getAllBranchersByPagination(limit, offset);
    }

    public String update(Chit chit) {
        return chitDAOController.update(chit);
    }

    public Integer removeChitById(String chitId) {
        return chitDAOController.removeChitById(chitId) ;
    }

    public String getId() {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    public Chit getBranchById(String chitid) {

        return chitDAOController.read(chitid);
    }

    public String getNextID() {
        return chitDAOController.getNextId();
    }

    public List<Chit> getAllChitById(Integer limit, Integer offset, String id,Integer type,Date date) {

        return chitDAOController.getAllChitById(limit, offset,id,type,date);
    }

    public Long getAllCount(String id) {

        return chitDAOController.getAllChitCount(id);
    }

    public List<Chit> getAllChitsByEnvelopeId(String envelopeId) {

        return chitDAOController.getAllChitsByEnvelopeId(envelopeId);
    }

    public Long getAllChitByIdCount(String id, Integer type, Date date1) {
        return chitDAOController.getAllChitByIdCount(id,type,date1);
    }
}
