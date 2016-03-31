package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.ChitDAOController;
import org.egreen.opensms.server.dao.EnvelopeDAOController;
import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.*;
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

    @Autowired
    private TransactionDAOService transactionDAOService;

    @Autowired
    private AccountDAOService accountDAOService;

    @Autowired
    private CenterDAOService centerDAOService;

    @Autowired
    private IndividualDAOService individualDAOService;

    @Autowired
    private EnvelopeDAOService envelopeDAOService;

    private List<Chit> all;
    private String id;


    /**
     * chit SignUp
     *
     * @param chit
     * @return
     */
    public String save(Chit chit) {
        String newid = idCreation();
        chit.setChitId(newid);

//        chit.setDatetime(new Timestamp(new Date().getTime()));
        chit.setDatetime(chit.getDatetime());
        //checking weather the chit individualId & date finished or not
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
//        String formatedDate = simpleDateFormat.format(chit.getDatetime());
//        List<Chit> chitList = chitDAOController.getAllChithsByFormattedDateNIndividualId(formatedDate, chit.getIndividualId());
        Envelope envelope = envelopeDAOController.read(chit.getEnvelopeId());
        String s = null;

//        boolean ok=false;
//        Chit ch=null;
//        for (Chit chit1 : chitList) {
//            ch=chit1;
//        }
//        if(chitList.size() > 0){
//            if(ch.getFinish() == null ){
//                ok=true;
//            }
//            if(ch.getFinish() == false){
//                ok=true;
//            }
//        }
//        if(chitList.size() == 0){
//            ok=true;
//        }
//        if(chitList.size() > 0){
//            if(ch.getFinish() == true){
//                ok=false;
//            }
//        }
//        if(ok == true){
//            setLessComSingleVal(chit);
//            chit.setFinish(false);
//            chitDAOController.create(chit);
//            s= "1";
//        }
//        if(ok == false){
//            s="0";
//        }
        if(envelope.getFinished() == false){
            setLessComSingleVal(chit);
            chit.setFinish(false);
            chitDAOController.create(chit);
            s= "1";
        }
        if(envelope.getFinished() == true){
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
     * New transactions begin less commision single or not commision are adding
     *
     * @param chit
     * @return
     */
    private void setLessComSingleVal(Chit chit){
        BigDecimal lessCommisionSingle = BigDecimal.ZERO;
        BigDecimal notCommision = BigDecimal.ZERO;
        Transaction transaction =null;
        if (chit.getLCS() != null && chit.getLCS() == true) {
            if (chit.getCenterid() != null) {
                Center centerById = centerDAOService.getCenterById(chit.getCenterid());
                if (centerById != null && centerById.getLessComissionSingle() != null && centerById.getLessComissionSingle().doubleValue() > 0.0) {
                    transaction = new Transaction();
                    String newid1 = idCreation();
                    transaction.setTransactionId(newid1);
                    transaction.setTime(chit.getDatetime());
                    transaction.setTypeId("LCS");
                    lessCommisionSingle = envelopeDAOService.calculateLessCommisionSingle(chit.getNcOLCValue(), centerById.getLessComissionSingle());
                    transaction.setDebit(lessCommisionSingle);
                    Account account = accountDAOService.getAccountByCenterOIndividualId(chit.getCenterid());
                    transaction.setAccountNo(account.getAccountNo());
                    transactionDAOService.save(transaction);
                }

            }
            if (chit.getIndividualId() != null) {
                Individual individualById = individualDAOService.getBranchById(chit.getIndividualId());
                if (individualById != null && individualById.getLessComissionSingle() != null && individualById.getLessComissionSingle().doubleValue() > 0.0) {
                    transaction=new Transaction();
                    String newid1=idCreation();
                    transaction.setTransactionId(newid1);
                    transaction.setTime(chit.getDatetime());
                    transaction.setTypeId("LCS");
                    lessCommisionSingle = envelopeDAOService.calculateLessCommisionSingle(chit.getNcOLCValue(), individualById.getLessComissionSingle());
                    transaction.setDebit(lessCommisionSingle);
                    Account account = accountDAOService.getAccountByCenterOIndividualId(chit.getIndividualId());
                    transaction.setAccountNo(account.getAccountNo());
                    transactionDAOService.save(transaction);
                }
            }
        }

        if (chit.getNC() != null && chit.getNC() == true) {
            if (chit.getCenterid() != null) {
                Center centerById = centerDAOService.getCenterById(chit.getCenterid());
                if (centerById != null && centerById.getNotCommisionPersentage() != null && centerById.getNotCommisionPersentage().doubleValue() > 0.0) {
                    transaction = new Transaction();
                    String newid1 = idCreation();
                    transaction.setTransactionId(newid1);
                    transaction.setTime(chit.getDatetime());
                    transaction.setTypeId("NC");
                    notCommision = envelopeDAOService.calculateNotCommision(chit.getNcOLCValue(), centerById.getNotCommisionPersentage());
                    transaction.setDebit(notCommision);
                    Account account = accountDAOService.getAccountByCenterOIndividualId(chit.getCenterid());
                    transaction.setAccountNo(account.getAccountNo());
                    transactionDAOService.save(transaction);
                }

            }
            if (chit.getIndividualId() != null) {
                Individual individualById = individualDAOService.getBranchById(chit.getIndividualId());
                if (individualById != null && individualById.getNotCommisionPersentage() != null && individualById.getNotCommisionPersentage().doubleValue() > 0.0) {
                    transaction=new Transaction();
                    String newid1=idCreation();
                    transaction.setTransactionId(newid1);
                    transaction.setTime(chit.getDatetime());
                    transaction.setTypeId("NC");
                    notCommision = envelopeDAOService.calculateNotCommision(chit.getNcOLCValue(), individualById.getNotCommisionPersentage());
                    transaction.setDebit(notCommision);
                    Account account = accountDAOService.getAccountByCenterOIndividualId(chit.getIndividualId());
                    transaction.setAccountNo(account.getAccountNo());
                    transactionDAOService.save(transaction);
                }
            }
        }

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
        return chitDAOController.removeChitById(chitId);
    }

    public Chit getChitById(String chitid) {

        return chitDAOController.read(chitid);
    }

    public List<Chit> getAllChitById(Integer limit, Integer offset, String id, Integer type, String date) {

        return chitDAOController.getAllChitById(limit, offset, id, type, date);
    }

    public Long getAllCount(String id) {

        return chitDAOController.getAllChitCount(id);
    }

    public List<Chit> getAllChitsByEnvelopeId(String envelopeId) {

        return chitDAOController.getAllChitsByEnvelopeId(envelopeId);
    }

    public Long getAllChitByIdCount(String id, Integer type, Date date1) {
        return chitDAOController.getAllChitByIdCount(id, type, date1);
    }

    public List<Chit> getAllChithsByFormattedDateNIndividualId(String formatedDate, String individualId) {
        return chitDAOController.getAllChithsByFormattedDateNIndividualId(formatedDate, individualId);
    }

}
