package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.CenterDAOController;
import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.dao.IndividualDAOController;
import org.egreen.opensms.server.entity.Center;
import org.egreen.opensms.server.entity.Envelope;
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
public class EnvelopeDAOService {

    @Autowired
    private EnvelopeDAOController envelopeDAOController;

    @Autowired
    private CenterDAOController centerDAOController;

    @Autowired
    private IndividualDAOController individualDAOController;
    
    private List<Envelope> all;
    private String id;


    /**
     *
     * envelope save
     *
     * @param envelope
     * @return
     */
    public String save(Envelope envelope) {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        envelope.setEnvelopId(newid);

        String s = envelopeDAOController.create(envelope);
        if(s!=null){
//            Logger logger=new Logger();
//            logger.setAmount(envelope.getInvesment());
//            String centerId = envelope.getCenter();
//            String individualId = envelope.getIndividualId();
//            Center center;
//            Individual individual;
//            if(null!= centerId){
//                center = centerDAOController.read(centerId);
//                logger.setCenterAccountNo(center.getAccountNo());
//            }
//            if(null != individualId){
//                individual = individualDAOController.read(individualId);
//                logger.setIndividualAccountNo(individual.getAccountNo());
//            }
//            ///////////////////////////////////////////
//            loggerDAOController.create(logger);
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


    public List<Envelope> getAll() {
        return envelopeDAOController.getAll();
    }

    public List<Envelope> getAllBranchersByPagination(Integer limit, Integer offset) {
        return envelopeDAOController.getAllBranchersByPagination(limit, offset);
    }

    public String update(Envelope individual) {
        return envelopeDAOController.update(individual);
    }

    public Integer removeBranchById(String individualId) {
        return envelopeDAOController.removeBranchById(individualId) ;
    }

    public String getId() {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    public Envelope getBranchById(String individualid) {

        return envelopeDAOController.read(individualid);
    }

    public String getNextID() {
        return envelopeDAOController.getNextId();
    }


    public List<Envelope> getAllByPagination(Integer limit, Integer offset) {
        return envelopeDAOController.getAll(offset,limit,"name");
    }

    public boolean checkIfExist(String centerName) {
        return envelopeDAOController.checkIfExist(centerName);
    }

    public List<Envelope> getEnvelopesByCenterId(String centerId, Integer limit, Integer offset, Date date) {

        return envelopeDAOController.getEnvelopesByCenterId(centerId,limit,offset,date);
    }

    public List<Envelope> getEnvelopesByCenterId(String centerId) {

        return envelopeDAOController.getAllByPropertyByStringValue(centerId,"center");
    }

    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, Date date) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDate(individualId,limit,offset,date);
    }

    public Envelope getEnvelopesByIndividualIdByDateNCenterId(String individualId, String formatedDate, String centerId) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDateNCenterId(individualId,formatedDate,centerId);
    }

    public List<Envelope> getEnvelopeByCenterIdDate(String center, Integer limit, Integer offset, String formatedDate) {
        return envelopeDAOController.getEnvelopeByCenterIdDate(center,limit,offset,formatedDate);
    }

    public boolean getEnvelopeByCenterIdNIndividualIdDate(String center, String individualId, String formatedDate) {
        return envelopeDAOController.getEnvelopeByCenterIdNIndividualIdDate(center,individualId,formatedDate);
    }
}
