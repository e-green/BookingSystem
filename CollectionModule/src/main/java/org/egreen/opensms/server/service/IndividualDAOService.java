package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.AccountDAOController;
import org.egreen.opensms.server.dao.IndividualDAOController;


import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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

    @Autowired
    private AccountDAOController accountDAOController;


    /**
     *
     * create individual
     *
     * @param individual
     * @return
     */
    public String save(Individual individual) {
        String id = new Date().getTime()+"";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        individual.setIndividualId(newid);
        if(individual.getLessComissionSingle() == null){
            individual.setLessComissionSingle(BigDecimal.ZERO);
        }
        if(individual.getNotCommisionPersentage() == null){
            individual.setNotCommisionPersentage(BigDecimal.ZERO);
        }

        if(individual.getCommision() == null){
            individual.setCommision(BigDecimal.ZERO);
        }
        if(individual.getPcChargers() == null){
            individual.setPcChargers(BigDecimal.ZERO);
        }

        if(individual.getFixedSalary() == null){
            individual.setFixedSalary(BigDecimal.ZERO);
        }
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

    /**
     * Get All individuals with out pagination
     *
     * @return
     */
    public List<Individual> getAll() {
        return individualDAOController.getAll();
    }

    /**
     * Get all branches by pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    public List<Individual> getAllBranchersByPagination(Integer limit, Integer offset) {
        return individualDAOController.getAllBranchersByPagination(limit, offset);
    }

    /**
     * Update individual
     *
     * @param individual
     * @return
     */
    public String update(Individual individual) {
        return individualDAOController.update(individual);
    }

    /**
     * remove individual by individualId
     *
     * @param individualId
     * @return
     */
    public Integer removeIndividualById(String individualId) {
        return individualDAOController.removeIndividualById(individualId) ;
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

    /**
     * check If Exist individual in specified center
     *
     * @param centerName
     * @return
     */
    public boolean checkIfExist(String centerName) {
        return individualDAOController.checkIfExist(centerName);
    }

    /**
     * get Individuals By CenterId
     *
     * @param centerId
     * @return
     */
    public List<Individual> getIndividualsByCenterId(String centerId) {
        List<Individual> individualList = individualDAOController.getIndividualsByCenterId(centerId);
        List<Individual> list=new ArrayList<Individual>();
        for (Individual individual:individualList ) {

            if(null== individual.getCommision()){
                individual.setCommision(BigDecimal.valueOf(0.0));
            }
            list.add(individual);
        }
        return list;
    }

    /**
     * get Individuals Count by CenterId
     *
     * @param centerId
     * @return
     */
    public int getIndividualCountByCenterId(String centerId) {
        List<Individual> individualList = individualDAOController.getAllByPropertyByStringValue(centerId, "center");
        return individualList.size();
    }

    /**
     * get Individuals By CenterId AndI ndividualId
     *
     * @param centerId
     * @param individualId
     * @return
     */
    public Individual getIndividualsByCenterIdAndIndividualId(String centerId, String individualId) {
        return individualDAOController.getIndividualsByCenterIdAndIndividualId(centerId,individualId);
    }

    /**
     * Get Individual account no by individual name
     *
     * @param memberName
     * @return
     */
    public String getCenterOIndividualAccountNoByCenterNameOIndividualName(String memberName) {
        Individual individual=individualDAOController.getIndividualByName(memberName);
        Account account=accountDAOController.getAccountByCenterOIndividualId(individual.getIndividualId());
        return account.getAccountNo();
    }

    /**
     * Get Individual account no by individual Id
     *
     * @param memberId
     * @return
     */
    public String getCenterOIndividualAccountNoByIndividualId(String memberId) {
        Individual individual=individualDAOController.read(memberId);
        Account account=accountDAOController.getAccountByCenterOIndividualId(individual.getIndividualId());
        return account.getAccountNo();
    }

    /**
     * get individual by individualId
     *
     * @param individualId
     * @return
     */
    public Individual readById(String individualId) {
        return individualDAOController.read(individualId);
    }


}
