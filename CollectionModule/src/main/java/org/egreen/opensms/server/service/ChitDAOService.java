package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ChitDAOController;
import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.ChitCountListModel;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Autowired
    private EnvelopeDAOController envelopeDAOController;


    /**
     * chit SignUp
     *
     * @param chit
     * @return
     */
    public String save(Chit chit) {
        String newid = idCreation();
        chit.setChitId(newid);
        Envelope envelope = envelopeDAOController.read(chit.getEnvelopeId());
        String s = null;

        if(envelope.getFinished()!= null && envelope.getFinished() == false){
            chit.setFinish(false);
            chitDAOController.create(chit);
            s= "1";
        }
        if(envelope.getFinished()!= null && envelope.getFinished() == true){
            s="0";
        }

        return s;
    }

    /**
     * updating chit
     *
     * @param chit
     * @return
     */
    public String update(Chit chit) {
        Envelope envelope = envelopeDAOController.read(chit.getEnvelopeId());
        String s=null;
        if(envelope.getFinished()!= null && envelope.getFinished() == false){
            chitDAOController.update(chit);
            s= "1";
        }
        if(envelope.getFinished()!= null && envelope.getFinished() == true){
            s="0";
        }
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

    private String idCreation(){
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    /**
     * get all chits
     *
     * @return
     */
    public List<Chit> getAll() {
        return chitDAOController.getAll();
    }

    public List<Chit> getAllBranchersByPagination(Integer limit, Integer offset) {
        return chitDAOController.getAllBranchersByPagination(limit, offset);
    }

    /**
     * remove chit by chitId
     *
     * @param chitId
     * @return
     */
    public Integer removeChitById(String chitId) {
        return chitDAOController.removeChitById(chitId);
    }

    /**
     * Get Chit by chitId
     *
     * @param chitid
     * @return
     */
    public Chit getChitById(String chitid) {

        return chitDAOController.read(chitid);
    }

    /**
     * get All Chits By memberId(individualId or centerId) & string Time (sTime) with limits
     *
     * @param limit
     * @param offset
     * @param id
     * @param type
     * @param date
     * @return
     */
    public List<Chit> getAllChitById(Integer limit, Integer offset, String id, Integer type, String date) {

        return chitDAOController.getAllChitById(limit, offset, id, type, date);
    }

    /**
     * get all chits by cennterId
     *
     * @param id
     * @return
     */
    public Long getAllCount(String id) {

        return chitDAOController.getAllChitCount(id);
    }

    /**
     * get all chits by envelopeId
     *
     * @param envelopeId
     * @return
     */
    public List<Chit> getAllChitsByEnvelopeId(String envelopeId) {

        return chitDAOController.getAllChitsByEnvelopeId(envelopeId);
    }

    /**
     * get All Chits By Id, type & string date(sTime)
     *
     * @param id
     * @param type
     * @param date1
     * @return
     */
    public Long getAllChitByIdCount(String id, Integer type, String date1) {
        return chitDAOController.getAllChitByIdCount(id, type, date1);
    }

    /**
     * get All Chiths By string Date(sTime) and IndividualId
     *
     * @param formatedDate
     * @param individualId
     * @return
     */
    public List<Chit> getAllChithsByFormattedDateNIndividualId(String formatedDate, String individualId) {
        return chitDAOController.getAllChithsByFormattedDateNIndividualId(formatedDate, individualId);
    }

    /**
     * get Chit CountList Of Individual For Spesific Date Range
     *
     * @param individualId
     * @param sTime
     * @param endSTime
     * @return
     */
    public List<ChitCountListModel> getChitCountListOfIndividualForSpesificDateRange(String individualId,String sTime, String endSTime) {
        return chitDAOController.getChitCountListOfIndividualForSpesificDateRange(individualId,sTime,endSTime);
    }
}
