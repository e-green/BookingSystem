package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.*;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.EnvelopeDetailModel;
import org.egreen.opensms.server.models.FinishEnvelopeModel;
import org.egreen.opensms.server.models.TransactionModel;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
        String indId=envelope.getIndividualId();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(envelope.getDate());
        Envelope envelopesByDateNByIndividualId = envelopeDAOController.getEnvelopesByDateNByIndividualId(indId, formattedDate);
        String s=null;
        if(envelopesByDateNByIndividualId == null){
            envelope.setEnvelopId(newid);

            if(envelope.getChitCount() == null){
                envelope.setChitCount(new BigInteger("0"));
            }

            s = envelopeDAOController.create(envelope);
            if (s != null) {
                BigDecimal invesment = envelope.getInvesment();
                String individualId = envelope.getIndividualId();
                String centerId = envelope.getCenter();

                Account account = accountDAOController.getAccountByCenterOIndividualId(individualId);
                Individual individual = individualDAOController.read(individualId);

                Center center=centerDAOController.read(centerId);
                String accountNo = null;
                Transaction transaction = null;

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


                if (individual.getCommision() != null && envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0) {
                    transaction = new Transaction();
                    createTransactinonAccount(individualId, transaction);
                    String newid1 = getStringID(id, hashids, hexaid);
                    transaction.setTransactionId(newid1);
                    transaction.setTypeId("COM");
                    double investmentValue = envelope.getInvesment().doubleValue();
                    BigDecimal commision = calculateCommision(invesment, individual.getCommision());
                    transaction.setCredit(commision);
                    transaction.setTime(envelope.getDate());
                    transactionDAOController.create(transaction);
                }

                if (envelope.getRentDeduct() != null && envelope.getRentDeduct()== true && individual.getRent()!= null) {
                    transaction = new Transaction();
                    createTransactinonAccount(individualId, transaction);
                    String newid1 = getStringID(id, hashids, hexaid);
                    transaction.setTransactionId(newid1);
                    transaction.setTypeId("RENT");
                    transaction.setDebit(individual.getRent());
                    transaction.setTime(envelope.getDate());
                    transactionDAOController.create(transaction);
                }

                if(center.getCommision() != null && envelope.getInvesment() != null && centerId != null && envelope.getInvesment().doubleValue() != 0){
                    transaction = new Transaction();
                    createTransactinonAccount(centerId, transaction);
                    String newid1 = getStringID(id, hashids, hexaid);
                    transaction.setTransactionId(newid1);
                    transaction.setTypeId("COM");
                    double investmentValue = envelope.getInvesment().doubleValue();
                    BigDecimal comm = calculateCommision(invesment, center.getCommision());
                    transaction.setCredit(comm);
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
                    transaction.setCredit(approvedLoan.getDueamount());
                    transaction.setTime(envelope.getDate());
                    transactionDAOController.create(transaction);
                } else if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {

                    transaction = new Transaction();
                    createTransactinonAccount(individualId, transaction);
                    String newid1 = getStringID(id, hashids, hexaid);
                    transaction.setTransactionId(newid1);
                    transaction.setTypeId("LN");
                    BigDecimal dueAmount=null;
                    if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null && envelope.getLoanDeduct() != null && envelope.getLoanDeduct() == true){
                        dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());
                    }

                    if(envelope.getLoanDeduct() != null && envelope.getLoanDeduct() == true){
                        transaction.setDebit(approvedLoan.getDeductionPayment());
                        transaction.setTime(envelope.getDate());
                        transactionDAOController.create(transaction);
                    }

                    if(approvedLoan.getDueamount() != null && dueAmount != null){
                        approvedLoan.setDueamount(dueAmount);
                    }
                    approvedLoanDAOService.update(approvedLoan);

                }
            }
        }

        return s;
    }

    /**
     *
     * @param id
     * @param hashids
     * @param hexaid
     * @return
     */
    private String getStringID(String id, Hashids hashids, String hexaid) {
        String id1 = new Date().getTime() + "";
        Hashids hashids1 = new Hashids(id);
        String hexaid1 = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        return hexaid + "" + randomString(10);
    }

    /**
     *
     * @param individualId
     * @param transaction
     */
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

    /**
     * Get all envelopes
     * @return
     */
    public List<Envelope> getAll() {
        return envelopeDAOController.getAll();
    }

    public List<Envelope> getAllBranchersByPagination(Integer limit, Integer offset) {
        return envelopeDAOController.getAllBranchersByPagination(limit, offset);
    }

    /**
     * Update Envelope
     *
     * @param envelope
     * @return
     */
    public String update(Envelope envelope) {
        String indId=envelope.getIndividualId();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(envelope.getDate());
        Envelope envelopesByDateNByIndividualId = envelopeDAOController.getEnvelopesByDateNByIndividualId(indId, formattedDate);
        String s=null;
        if(envelopesByDateNByIndividualId != null){

            if(envelope.getChitCount() == null){
                envelope.setChitCount(BigInteger.ZERO);
            }
            if(envelopesByDateNByIndividualId.getEnvelopId() != null){
                envelope.setEnvelopId(envelopesByDateNByIndividualId.getEnvelopId());
            }

            s = envelopeDAOController.update(envelope);
            if (s != null) {
                BigDecimal invesment = envelope.getInvesment();
                String individualId = envelope.getIndividualId();
                String centerId = envelope.getCenter();

                Account account = accountDAOController.getAccountByCenterOIndividualId(individualId);
                Account centerAccount = accountDAOController.getAccountByCenterOIndividualId(centerId);
                Individual individual = individualDAOController.read(individualId);
                List<Transaction> transactionList = transactionDAOController.getTodayTransactionDetailByDateNAccountNo(formattedDate, account.getAccountNo());
                List<Transaction> centerTransactionList = transactionDAOController.getTodayTransactionDetailByDateNAccountNo(formattedDate, centerAccount.getAccountNo());
                Center center=centerDAOController.read(centerId);
                for(Transaction transaction:transactionList){
                    if (envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0 && transaction.getTypeId().equals("Inv")) {
                        transaction.setDebit(envelope.getInvesment());
                        transactionDAOController.update(transaction);
                    }
                    if (envelope.getCash() != null && envelope.getCash().doubleValue() != 0 && transaction.getTypeId().equals("CSH") ) {
                        transaction.setCredit(envelope.getCash());
                        transactionDAOController.update(transaction);
                    }
                    if (envelope.getExpences() != null && envelope.getExpences().doubleValue() != 0 && transaction.getTypeId().equals("EXP")) {
                        transaction.setCredit(envelope.getExpences());
                        transactionDAOController.update(transaction);
                    }
                    if (individual.getPcChargers() != null && individual.getPcChargers().doubleValue() != 0 && transaction.getTypeId().equals("PC")) {
                        transaction.setDebit(individual.getPcChargers());
                        transactionDAOController.update(transaction);
                    }
                    if (individual.getCommision() != null && envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0 && transaction.getTypeId().equals("COM") && transaction.getAccountNo().equals(account.getAccountNo()) ) {
                        BigDecimal con = calculateCommision(invesment, individual.getCommision());
                        transaction.setCredit(con);
                        transactionDAOController.update(transaction);
                    }
                }
                if(centerTransactionList.size() > 0){
                    for(Transaction transaction:centerTransactionList){
                        if(center.getCommision() != null && envelope.getInvesment() != null && centerId != null && envelope.getInvesment().doubleValue() != 0 && transaction.getTypeId().equals("COM") && transaction.getAccountNo().equals(centerAccount.getAccountNo())){
                            BigDecimal comm = calculateCommision(invesment, center.getCommision());
                            transaction.setCredit(comm);
                            transactionDAOController.update(transaction);
                        }
                    }
                }
            }

        }


        return s;
    }

    /**
     * remove Envelope by individualId
     * @param individualId
     * @return
     */
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

    /**
     * get Envelope by envelopeId
     *
     * @param individualid
     * @return
     */
    public Envelope getBranchById(String individualid) {

        return envelopeDAOController.read(individualid);
    }


    public String getNextID() {
        return envelopeDAOController.getNextId();
    }

    /**
     * Get Envelope list
     *
     * @param limit
     * @param offset
     * @return
     */
    public List<Envelope> getAllByPagination(Integer limit, Integer offset) {
        return envelopeDAOController.getAll(offset, limit, "name");
    }

    public boolean checkIfExist(String centerName) {
        return envelopeDAOController.checkIfExist(centerName);
    }

    /**
     * Get Envelope list by Center Id
     *
     * @param centerId
     * @param limit
     * @param offset
     * @param date
     * @return
     */
    public List<Envelope> getEnvelopesByCenterId(String centerId, Integer limit, Integer offset, Date date) {

        return envelopeDAOController.getEnvelopesByCenterId(centerId, limit, offset, date);
    }

    /**
     * Get Envelope list by CenterId without liitation or offset
     *
     * @param centerId
     * @return
     */
    public List<Envelope> getEnvelopesByCenterId(String centerId) {

        return envelopeDAOController.getAllByPropertyByStringValue(centerId, "center");
    }

    /**
     * Get Envelopes list by individualId & date
     *
     * @param individualId
     * @param limit
     * @param offset
     * @param date
     * @return
     */
    public List<Envelope> getEnvelopesByIndividualIdByDate(String individualId, Integer limit, Integer offset, String date) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDate(individualId, limit, offset, date);
    }


    /**
     * Get Envelope by centerId & date
     *
     * @param center
     * @param limit
     * @param offset
     * @param formatedDate
     * @return
     */
    public List<Envelope> getEnvelopeByCenterIdDate(String center, Integer limit, Integer offset, String formatedDate) {
        return envelopeDAOController.getEnvelopeByCenterIdDate(center, limit, offset, formatedDate);
    }

    /**
     * Get Envelope by centerId , individualId & date
     *
     * @param center
     * @param individualId
     * @param formatedDate
     * @return
     */
    public FinishEnvelopeModel getEnvelopeByCenterIdNIndividualIdDate(String center, String individualId, String formatedDate) {
        FinishEnvelopeModel envelopeByCenterIdNIndividualIdDate = envelopeDAOController.getEnvelopeByCenterIdNIndividualIdDate(center, individualId, formatedDate);
        return envelopeByCenterIdNIndividualIdDate;
    }

    /**
     * Save any Transaction
     *
     * @param transactionModel
     * @return
     */
    public String saveAnyTransaction(TransactionModel transactionModel) {
        Envelope envelope=null;
        if(!transactionModel.getEnvelope().getInvesment().equals(null)){
            envelope=transactionModel.getEnvelope();
        }
        String s=null;
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = getStringID(id, hashids, hexaid);
        if(envelope!= null){
            envelope.setEnvelopId(newid);
            s=envelopeDAOController.create(envelope);
        }

        BigDecimal invesment = envelope.getInvesment();
        String individualId = envelope.getIndividualId();
        if(envelope.getIndividualId() == null ){
            individualId=transactionModel.getIndividualId();
        }

        Account account = accountDAOController.getAccountByCenterOIndividualId(individualId);
        Individual individual = individualDAOController.read(individualId);

        Center center;
        String accountNo = null;
        Transaction transaction = null;

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
            BigDecimal notCommision=calculateNotCommision(envelope.getNotCommision(),transactionModel.getNotCommisionPresentage());
            transaction.setDebit(notCommision);
            transaction.setTime(envelope.getDate());
            transactionDAOController.create(transaction);
        }
        if (envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0
                && envelope.getInvesment() != null && envelope.getInvesment().doubleValue() != 0) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);


            transaction.setTypeId("COM");
            BigDecimal commision=calculateCommision(envelope.getInvesment(),transactionModel.getCommisionPresentage());
            transaction.setCredit(commision);
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

        if (envelope.getSalary() != null && envelope.getSalary().doubleValue() != 0) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);

            transaction.setTypeId("Salary");
            BigDecimal salary = calculateSalary(envelope.getInvesment());
            transaction.setCredit(salary);
            transaction.setTime(envelope.getDate());
            transactionDAOController.create(transaction);
        }

        if (envelope.getLessCommisionSingle() != false ) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);

            transaction.setTypeId("LCS");
//            BigDecimal lessCommisionSingle = calculateLessCommisionSingle();
//            transaction.setDebit(lessCommisionSingle);
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
            transaction.setTime(transactionModel.getDate());
            transactionDAOController.create(transaction);
        }

        if (transactionModel.getLoanCharges() != null && transactionModel.getLoanCharges().doubleValue() != 0) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);

            transaction.setTypeId("LCharges");
            transaction.setDebit(transactionModel.getLoanCharges());
            transaction.setTime(transactionModel.getDate());
            transactionDAOController.create(transaction);
        }

        if (transactionModel.getChit().getAmount() != null && transactionModel.getChit().getAmount().doubleValue() != 0) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);


            transaction.setTypeId("PAY");
            transaction.setCredit(transactionModel.getChit().getAmount());
            transaction.setTime(transactionModel.getDate());
            transactionDAOController.create(transaction);
        }

        if (transactionModel.getExcess() != null && transactionModel.getExcess().doubleValue() != 0) {
            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);


            transaction.setTypeId("Excess");
            transaction.setCredit(transactionModel.getExcess());
            transaction.setTime(envelope.getDate());
            transactionDAOController.create(transaction);
        }

        ApprovedLoan approvedLoan = approvedLoanDAOService.getOpenLoanDetailByIndividualId(individualId);

        if (approvedLoan!=null&&approvedLoan.getDatetime().getYear() == transactionModel.getDate().getYear()
                && approvedLoan.getDatetime().getMonth() == transactionModel.getDate().getMonth()
                && approvedLoan.getDatetime().getDate() == transactionModel.getDate().getDate()
                ) {

            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);

            transaction.setTypeId("LON");


            transaction.setCredit(approvedLoan.getAmount());
            transaction.setTime(transactionModel.getDate());
            transactionDAOController.create(transaction);
        } else if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {


            transaction = new Transaction();
            createTransactinonAccount(individualId, transaction);
            String newid1 = getStringID(id, hashids, hexaid);
            transaction.setTransactionId(newid1);


            transaction.setTypeId("LN");


            BigDecimal dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());

            transaction.setDebit(dueAmount);
            transaction.setTime(transactionModel.getDate());
            transactionDAOController.create(transaction);


            approvedLoan.setDueamount(approvedLoan.getDueamount().subtract(dueAmount));
            approvedLoanDAOService.update(approvedLoan);

        }

        return s;
    }

    /**
     * calculate salary
     *
     * @param invesment
     * @return
     */
    public BigDecimal calculateSalary(BigDecimal invesment) {
        BigDecimal salary=null;
        if(invesment.doubleValue() < 1500.00){
            salary=new BigDecimal(300.00);
        }
        if(invesment.doubleValue() >= 1500.00 && invesment.doubleValue() <= 2000.00){
            salary=new BigDecimal(400.00);
        }
        if(invesment.doubleValue() > 2000.00 && invesment.doubleValue() <= 3000.00){
            salary=new BigDecimal(500.00);
        }
        if(invesment.doubleValue() > 3000.00 && invesment.doubleValue() <= 6000.00){
            salary=new BigDecimal(600.00);
        }
        if(invesment.doubleValue() > 6000.00){
            salary=new BigDecimal(600).add(BigDecimal.valueOf((invesment.doubleValue()-6000.00)/100*5));
        }
        double doubleValue = salary.doubleValue();
        long round = Math.round(doubleValue);
        BigDecimal sal = BigDecimal.valueOf(round);
        return sal;
    }

    /**
     * calculate commision
     * @param invesment
     * @param commisionPresentage
     * @return
     */
    public BigDecimal calculateCommision(BigDecimal invesment, BigDecimal commisionPresentage) {
        BigDecimal commision=invesment.divide(BigDecimal.valueOf(100)).multiply(commisionPresentage);
        double doubleValue = commision.doubleValue();
        long l = Math.round(doubleValue);
        BigDecimal bigDecimal = BigDecimal.valueOf(l);
        return bigDecimal;
    }

    /**
     * calculate not commision
     *
     * @param notCommision
     * @param notCommisionPresentage
     * @return
     */
    public BigDecimal calculateNotCommision(BigDecimal notCommision, BigDecimal notCommisionPresentage) {
        BigDecimal nCommision=notCommision.divide(BigDecimal.valueOf(100)).multiply(notCommisionPresentage);
        double doubleValue = nCommision.doubleValue();
        long val = Math.round(doubleValue);
        BigDecimal nCom = BigDecimal.valueOf(val);
        return nCom;
    }

    /**
     * calculating less commision single
     *
     * @param lessCommisionSingle
     * @return
     */
    public BigDecimal calculateLessCommisionSingle(BigDecimal lessCommisionSingle,BigDecimal lessCommisionSinglePersentageForCenter){
        BigDecimal lcs=lessCommisionSingle.divide(BigDecimal.valueOf(100)).multiply(lessCommisionSinglePersentageForCenter);
        double doubleValue = lcs.doubleValue();
        long round = Math.round(doubleValue);
        BigDecimal lcsVal = BigDecimal.valueOf(round);
        return lcsVal;
    }

    /**
     * get envelopeDetailModel view before enter envelope
     *
     * @param envelopeDetailModel
     * @return
     */
    public EnvelopeDetailModel getEnvelopesDetailModel(EnvelopeDetailModel envelopeDetailModel) {
        EnvelopeDetailModel model=new EnvelopeDetailModel();
        String individualId = envelopeDetailModel.getIndividualId();
        Individual individual = individualDAOController.read(individualId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(envelopeDetailModel.getDate());
        //Envelope envelope = envelopeDAOController.getEnvelopesByDateNByIndividualId(individualId, formatedDate);
        List<ApprovedLoan> unpaidLoanList = approvedLoanDAOService.getUnpaidLoansByIndividualId(individualId);
        Account account = accountDAOController.getAccountByCenterOIndividualId(individualId);
        BigDecimal commision=null;
        BigDecimal salary=null;
        BigDecimal lessCommissionSingle=null;
        BigDecimal dueLoanValue=BigDecimal.ZERO;
            if(individual != null ){
                model.setIndividualId(individual.getIndividualId());
                model.setDate(envelopeDetailModel.getDate());
                if(envelopeDetailModel.getInvestment() != null) {
                    model.setInvestment(envelopeDetailModel.getInvestment());
                    if (individual.getCommision() != null) {
                        commision = calculateCommision(envelopeDetailModel.getInvestment(), individual.getCommision());
                    }
                    if (individual.isSalaryPay() == true) {
                        salary = individual.getFixedSalary();
                        model.setSalary(salary);
                    }
                    if (individual.getFixedSalary() == null && individual.getCommision() == null && individual.isSalaryPay() == false) {
                        salary = calculateSalary(envelopeDetailModel.getInvestment());
                        model.setSalary(salary);
                    }
                }
                if(commision != null ){
                    model.setCommision(commision);
                }

                if(account != null && account.getAmount() != null){
                    model.setDueVale(account.getAmount());
                }
                for(ApprovedLoan approvedLoan:unpaidLoanList){
                    if(approvedLoan != null && approvedLoan.getDueamount() != null){
                        dueLoanValue=dueLoanValue.add(approvedLoan.getDueamount());
                        model.setLoanCharges(approvedLoan.getDeductionPayment());
                    }
                }

                model.setLdPayment(dueLoanValue);

                if(individual.getPcChargers() !=null){
                    model.setPcCharges(individual.getPcChargers());
                }
            }

        return model;
    }

    public Envelope getEnvelopesByIndividualIdByDateNCenterId(String individualId, String formatedDate, String center) {
        return envelopeDAOController.getEnvelopesByIndividualIdByDateNCenterId(individualId,formatedDate,center);
    }

    public Envelope getEnvelopeById(String envelopeId) {
        return envelopeDAOController.read(envelopeId);
    }

    public String checkEnvelopeIsFinished(String envelopeId, String formatedDate) {
        return envelopeDAOController.checkEnvelopeIsFinished(envelopeId,formatedDate);
    }
}
