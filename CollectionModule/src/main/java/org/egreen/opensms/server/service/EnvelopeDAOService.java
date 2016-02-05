package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.entity.Envelope;
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
    
    private List<Envelope> all;
    private String id;


    /**
     *
     * individual SignUp
     *
     * @param individual
     * @return
     */
    public String save(Envelope individual) {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
//        if (individual.getName()!=null) {
//            individual.setBranchid(individual.getName());
//        }

        individual.setEnvelopId(newid);

        String s = envelopeDAOController.create(individual);
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


}
