package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.*;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private AccountDAOController accountDAOController;

    @Autowired
    private TransactionDAOController transactionDAOController;


    @Autowired
    private ApprovedLoanDAOService approvedLoanDAOService;

    private List<Envelope> all;
    private String id;


    /**
     * envelope save
     *
     * @param envelope
     * @return
     */
    public String save(Envelope envelope) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        envelope.setEnvelopId(newid);

        String s = envelopeDAOController.create(envelope);
        if (s != null) {
//            Account account=new Account();
//            account.setAmount(envelope.getInvesment());
            BigDecimal invesment = envelope.getInvesment();
//            String centerId = envelope.getCenter();
            String individualId = envelope.getIndividualId();

            Account account = accountDAOController.getAccountByCenterOIndividualId(individualId);
            Individual individual = individualDAOController.read(individualId);

            Center center;
//            Individual individual;
            String accountNo = null;
            Transaction transaction = null;
//            if(null!= centerId){
//                Account centerAccount = accountDAOController.getAccountByCenterOIndividualId(centerId);
//                accountNo = centerAccount.getAccountNo();
//                transaction.setAccountNo(accountNo);
//            }

            ///////////////////////////////////////////
//            loggerDAOController.create(logger);


            if (envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);

                transaction.setTypeId("Inv");
                transaction.setDebit(envelope.getInvesment());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }


            if (envelope.getCash() != null && envelope.getCash().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);

                transaction.setTypeId("CSH");
                transaction.setCredit(envelope.getCash());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }


            if (envelope.getNotCommision() != null && envelope.getNotCommision().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                //genarate new transactionId for transaction and set it
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);

                transaction.setTypeId("NC");
                transaction.setDebit(envelope.getNotCommision());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }

            if (envelope.getExpences() != null && envelope.getExpences().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);

                transaction.setTypeId("EXP");
                transaction.setCredit(envelope.getExpences());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }


            if (individual.getPcChargers() != null && individual.getPcChargers().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);


                transaction.setTypeId("PC");
                transaction.setDebit(individual.getPcChargers());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }


            if (individual.getCommision() != null && individual.getCommision().doubleValue() != 0
                    && envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0) {
                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);


                transaction.setTypeId("COM");

                double investmentValue = envelope.getInvesment().doubleValue();

                transaction.setCredit(BigDecimal.valueOf(individual.getCommision().doubleValue() * investmentValue/100));
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            }

            ApprovedLoan approvedLoan = approvedLoanDAOService.getOpenLoanDetailByIndividualId(individualId);

            if (approvedLoan!=null&&approvedLoan.getDatetime().getYear() == envelope.getDate().getYear()
                    && approvedLoan.getDatetime().getMonth() == envelope.getDate().getMonth()
                    && approvedLoan.getDatetime().getDate() == envelope.getDate().getDate()
                    ) {

                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);

                transaction.setTypeId("LON");


                transaction.setCredit(approvedLoan.getAmount());
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);
            } else if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {


                transaction = new Transaction();
                createTransactinonAccount(individualId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);


                transaction.setTypeId("LN");


                BigDecimal dueAmount = approvedLoan.getDueamount().divide(BigDecimal.valueOf(Long.parseLong(approvedLoan.getDuration())));

                transaction.setDebit(dueAmount);
                transaction.setTime(envelope.getDate());
                transactionDAOController.create(transaction);


                approvedLoan.setDueamount(approvedLoan.getDueamount().subtract(dueAmount));
                approvedLoanDAOService.update(approvedLoan);
//                approvedLoan.set


            }


//            for (int i = 0; i < 4; i++) {
//                if (i == 0) {
//                    Transaction tr1 = transactionDAOController.getTransactionsByDateNAccountNoNType(envelope.getDate(), accountNo, "investment");
//                    if (tr1.getAccountNo() == null) {
//                        //genarate new transactionId for transaction and set it
//
//                    }
//                } else if (i == 1) {
//                    Transaction tr2 = transactionDAOController.getTransactionsByDateNAccountNoNType(envelope.getDate(), accountNo, "CASH");
//                    if (tr2.getAccountNo() == null) {
//                        if (null != envelope.getCash()) {
//                            //genarate new transactionId for transaction and set it
//                            String id1 = new Date().getTime() + "";
//                            Hashids hashids1 = new Hashids(id);
//                            String hexaid1 = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
//                            String newid1 = hexaid + "" + randomString(10);
//                            transaction.setTransactionId(newid1);
//
//                            transaction.setTypeId("CASH");
//                            transaction.setCredit(envelope.getCash());
//                            transaction.setTime(envelope.getDate());
//                            transactionDAOController.create(transaction);
//                        }
//                    }
//                } else if (i == 2) {
//                    Transaction tr3 = transactionDAOController.getTransactionsByDateNAccountNoNType(envelope.getDate(), accountNo, "NC");
//                    if (tr3.getAccountNo() == null) {
//                        if (null != envelope.getNotCommision()) {
//                            //genarate new transactionId for transaction and set it
//                            String id1 = new Date().getTime() + "";
//                            Hashids hashids1 = new Hashids(id);
//                            String hexaid1 = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
//                            String newid1 = hexaid + "" + randomString(10);
//                            transaction.setTransactionId(newid1);
//
//                            transaction.setTypeId("NC");
//                            transaction.setDebit(envelope.getNotCommision());
//                            transaction.setTime(envelope.getDate());
//                            transactionDAOController.create(transaction);
//                        }
//                    }
//                } else if (i == 3) {
//                    Transaction tr4 = transactionDAOController.getTransactionsByDateNAccountNoNType(envelope.getDate(), accountNo, "exp");
//                    if (tr4.getAccountNo() == null) {
//                        if (null != envelope.getExpences()) {
//                            //genarate new transactionId for transaction and set it
//                            String id1 = new Date().getTime() + "";
//                            Hashids hashids1 = new Hashids(id);
//                            String hexaid1 = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
//                            String newid1 = hexaid + "" + randomString(10);
//                            transaction.setTransactionId(newid1);
//
//                            transaction.setTypeId("exp");
//                            transaction.setCredit(envelope.getExpences());
//                            transaction.setTime(envelope.getDate());
//                            transactionDAOController.create(transaction);
//                        }
//                    }
//                }
//            }
        }
        return s;
    }

    private String getStringID(String id, Hashids hashids, String hexaid) {
        String id1 = new Date().getTime() + "";
        Hashids hashids1 = new Hashids(id);
        String hexaid1 = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        return hexaid + "" + randomString(10);
    }

    private void createTransactinonAccount(String individualId, Transaction transaction) {
        String accountNo;
        if (null != individualId && transaction != null) {
            Account individualAccount = accountDAOController.getAccountByCenterOIndividualId(individualId);
            accountNo = individualAccount.getAccountNo();
            transaction.setAccountNo(accountNo);

        }
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
        return envelopeDAOController.removeBranchById(individualId);
    }

    public String getId() {
        String id = new Date().getTime() + "";
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
        return envelopeDAOController.getAll(offset, limit, "name");
    }

    public boolean checkIfExist(String centerName) {
        return envelopeDAOController.checkIfExist(centerName);
    }

    public List<Envelope> getEnvelopesByCenterId(String centerId, Integer limit, Integer offset, Date date) {

        return envelopeDAOController.getEnvelopesByCenterId(centerId, limit, offset, date);
    }

    public List<Envelope> getEnvelopesByCenterId(String centerId) {

        return envelopeDAOController.getAllByPropertyByStringValue(centerId, "center");
    }

    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, Date date) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDate(individualId, limit, offset, date);
    }

    public Envelope getEnvelopesByIndividualIdByDateNCenterId(String individualId, String formatedDate, String centerId) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDateNCenterId(individualId, formatedDate, centerId);
    }

    public List<Envelope> getEnvelopeByCenterIdDate(String center, Integer limit, Integer offset, String formatedDate) {
        return envelopeDAOController.getEnvelopeByCenterIdDate(center, limit, offset, formatedDate);
    }

    public boolean getEnvelopeByCenterIdNIndividualIdDate(String center, String individualId, String formatedDate) {
        return envelopeDAOController.getEnvelopeByCenterIdNIndividualIdDate(center, individualId, formatedDate);
    }
}
