package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.IndividualDAOController;


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
public class IndividualDAOService {

    @Autowired
    private IndividualDAOController individualDAOController;
    
    private List<Individual> all;
    private String id;


    /**
     *
     * individual SignUp
     *
     * @param individual
     * @return
     */
    public String save(Individual individual) {
//        String id = new Date().getTime()+"";
//        Hashids hashids = new Hashids(id);
//        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
//        String newid = hexaid + "" + randomString(10);
//        if (individual.getName()!=null) {
//            individual.setBranchid(individual.getName());
//        }

     //  individual.setIndividualId(newid);

        String s = individualDAOController.create(individual);
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


    public List<Individual> getAll() {
        return individualDAOController.getAll();
    }

    public List<Individual> getAllBranchersByPagination(Integer limit, Integer offset) {
        return individualDAOController.getAllBranchersByPagination(limit, offset);
    }

    public String update(Individual individual) {
        return individualDAOController.update(individual);
    }

    public Integer removeBranchById(String individualId) {
        return individualDAOController.removeBranchById(individualId) ;
    }

    public String getId() {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    public Individual getBranchById(String individualid) {

        return individualDAOController.read(individualid);
    }

    public String getNextID() {
        return individualDAOController.getNextId();
    }


    public List<Individual> getAllByPagination(Integer limit, Integer offset) {
        return individualDAOController.getAll(offset,limit,"name");
    }

    public boolean checkIfExist(String centerName) {
        return individualDAOController.checkIfExist(centerName);
    }

    public List<Individual> getIndividualsByCenterId(String centerId) {

        return individualDAOController.getAllByPropertyByStringValue(centerId,"center");
    }

    public Individual getIndividualsByCenterIdAndIndividualId(String centerId, String individualId) {
        return individualDAOController.getIndividualsByCenterIdAndIndividualId(centerId,individualId);
    }
}
