package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.EnvelopeDetailModel;
import org.egreen.opensms.server.models.GeneralSummaryReceiptModel;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.*;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/individual")
public class IndividualController {

    @Autowired
    private IndividualDAOService individualDAOService;

    @Autowired
    private AccountDAOService accountDAOService;
    //
    @Autowired
    private EnvelopeDAOService envelopeDAOService;

    @Autowired
    private ChitDAOService chitDAOService;

    @Autowired
    private TransactionDAOService transactionDAOService;

    @Autowired
    private CenterDAOService centerDAOService;

    @Autowired
    private ApprovedLoanDAOService approvedLoanDAOService;


    /**
     * save Branch
     *
     * @param individual
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Individual individual) {
        String individualId = individualDAOService.save(individual);
        String res = null;
        if (null != individualId) {
            Account account = new Account();
            account.setMemberId(individualId);
            res = accountDAOService.save(account);
        }
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * create Accounts
     *
     * @return
     */
    @RequestMapping(value = "createAccounts", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage createAccount() {

        List<Individual> all = individualDAOService.getAll();
        for (Individual individual : all) {
            Account account = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            String individualId = individual.getIndividualId();
            if (account != null) {
                if (null != individualId) {
                    account = new Account();
                    account.setMemberId(individualId);
                    String res = accountDAOService.save(account);
                }
            }
        }

        return ResponseMessage.SUCCESS;

    }
//
//

    /**
     * Update Branch
     *
     * @param individual
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Individual individual) {
        String res = individualDAOService.update(individual);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

    /**
     * getAll
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAll(@RequestParam("limit") Integer limit,
                                   @RequestParam("offset") Integer offset) {
        List<Individual> all = individualDAOService.getAllByPagination(limit, offset);
        return all;
    }


    /**
     * checkIfExist
     *
     * @param individualName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("individualName") String individualName) {
        boolean all = individualDAOService.checkIfExist(individualName);
        return all;
    }
//
//    /**
//     * Get package Id
//     *
//     * @return
//     */
//    @RequestMapping(value = "getId", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public ReturnIdModel1 getId(@RequestParam("name") String name) {
//        String nameNew=name.substring(0, 3);
//        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
//        returnIdModel1.setId(nameNew);
//        return returnIdModel1;
//
//    }

    /**
     * getIndividualsByCenterIdAndIndividualId
     *
     * @param centerId
     * @param individualId
     * @return
     */
    @RequestMapping(value = "getIndividualsByCenterIdAndIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Individual getIndividualsByCenterIdAndIndividualId(@RequestParam("centerId") String centerId,
                                                              @RequestParam("individualId") String individualId) {
        Individual individualList = individualDAOService.getIndividualsByCenterIdAndIndividualId(centerId, individualId);
        return individualList;

    }

    /**
     * getIndividualsByCenterId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getIndividualsByCenterId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getIndividualsByCenterId(@RequestParam("centerId") String centerId) {
        List<Individual> individualList = individualDAOService.getIndividualsByCenterId(centerId);
        return individualList;

    }


    /**
     * Get All
     *
     * @return
     */
//    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<Individual> getAll() {
//        return individualDAOService.getAll();
//    }

    /**
     * Get All Branchers By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAllBranchersByPagination(@RequestParam("limit") Integer limit,
                                                        @RequestParam("offset") Integer offset) {
        return individualDAOService.getAllBranchersByPagination(limit, offset);
    }

    /***
     * getAllBranches
     *
     * @param limit
     * @param offset
     * @param type
     * @return
     */
    @RequestMapping(value = "getAllBranches", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAllBranches(@RequestParam("limit") Integer limit,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("type") Integer type) {
        return individualDAOService.getAllBranchersByPagination(limit, offset);
    }

    /**
     * removeBranchById
     *
     * @param individualId
     * @return
     */
    @RequestMapping(value = "removeBranchById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeBranchById(@RequestParam("individualId") String individualId) {
        Integer res = individualDAOService.removeBranchById(individualId);
        return res;
    }


    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Individual getob() {
        return new Individual();
    }

    @RequestMapping(value = "GeneralSummaryReceiptModelOB", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public GeneralSummaryReceiptModel getGeneralSummaryReceiptModelOB() {
        return new GeneralSummaryReceiptModel();
    }


    /**
     * getBranchCode
     *
     * @param locationName
     * @return
     */
    @RequestMapping(value = "getBranchCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getBranchCode(@RequestParam("locationName") String locationName) {

        String id = individualDAOService.getNextID();
        String name = locationName.substring(0, 2) + id;
        return name;
    }


    @RequestMapping(value = "closeGenralSummery", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage closeGenralSummery(@RequestBody GeneralSummaryReceiptModel generalSummaryReceiptModel) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=generalSummaryReceiptModel.getDate();
        Date tomorrow=new Date(curDate.getTime() + (1000 * 60 * 60 * 24));
        Timestamp tomorrowTimeStamp = new java.sql.Timestamp(tomorrow.getTime());

        Transaction tra = new Transaction();
        Map<String, Object> summaryReceipt = getGeneralSummaryReceipt(generalSummaryReceiptModel);

        Object due = summaryReceipt.get("dueBalance");
        Object pay = summaryReceipt.get("totPay");
        Double dueValue = 0.0;
        Double payValue = 0.0;
        try {
            dueValue = Double.parseDouble(due + "");
        } catch (Exception e) {

        }
        try {
            payValue = Double.parseDouble(pay + "");
        } catch (Exception e) {

        }
        double nc = generalSummaryReceiptModel.getNc();
        double lcs = generalSummaryReceiptModel.getLcs();

        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;

        Double totPayment = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;

        Transaction transaction = null;
        String indiviId = generalSummaryReceiptModel.getIndividualId();
        Account account = accountDAOService.getAccountByCenterOIndividualId(indiviId);

        if(generalSummaryReceiptModel.getPay() > 0.0 && payValue == 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getPay()));
            totPayment+=generalSummaryReceiptModel.getPay();
            dueValue-=totPayment;
            transaction.setTypeId("PAY");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }
        if(payValue > 0.0 && generalSummaryReceiptModel.getPay() == 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(payValue));
            transaction.setTypeId("PAY");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }
        if(payValue > 0.0 && generalSummaryReceiptModel.getPay() > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getPay()));
            Double changeOfPayValue= 0.0;
            changeOfPayValue=payValue-generalSummaryReceiptModel.getPay();
            if(changeOfPayValue > 0.0){
                dueValue+=changeOfPayValue;
            }
            if(changeOfPayValue < 0.0){
                dueValue-=changeOfPayValue;
            }
            transaction.setTypeId("PAY");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        if (dueValue > 0) {
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setDebit(BigDecimal.valueOf(generalSummaryReceiptModel.getBalance()));
            transaction.setTypeId("Balance");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);


            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            double pd=0.0;
            pd=generalSummaryReceiptModel.getPd();
            transaction.setDebit(BigDecimal.valueOf(pd));
            transaction.setTypeId("PD");
            transaction.setTime(tomorrowTimeStamp);
            transactionDAOService.save(transaction);

        }
        if(dueValue < 0 && generalSummaryReceiptModel.getPayment() > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getBalance()));
            transaction.setTypeId("Balance");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);

            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getPayment()*-1));
            transaction.setTypeId("Payment");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        if(generalSummaryReceiptModel.getSalary() > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getSalary()));
            transaction.setTypeId("Salary");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        if(generalSummaryReceiptModel.getOverPayment() > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setDebit(BigDecimal.valueOf(generalSummaryReceiptModel.getOverPayment()));
            transaction.setTypeId("OverPayment");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        if(generalSummaryReceiptModel.getExcess() > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getExcess()));
            transaction.setTypeId("Excess");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());
        String individualId = generalSummaryReceiptModel.getIndividualId();
        List<Chit> chitList = chitDAOService.getAllChithsByFormattedDateNIndividualId(formatedDate, individualId);
        List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(generalSummaryReceiptModel.getEnvelopeId());
        Envelope envelopeById = envelopeDAOService.getEnvelopeById(generalSummaryReceiptModel.getEnvelopeId());
        if(envelopeById != null){
            envelopeById.setFinished(true);
            envelopeDAOService.update(envelopeById);
        }

        Center center = centerDAOService.getCenterById(generalSummaryReceiptModel.getCenterId());
        Individual individual1 = individualDAOService.getBranchById(generalSummaryReceiptModel.getIndividualId());
        BigDecimal notcommisionPersentageForCenter = BigDecimal.ZERO;
        BigDecimal notcommisionPersentageForIndividual = BigDecimal.ZERO;
        BigDecimal notCommisionsTot = BigDecimal.ZERO;
        BigDecimal lessCommisionSinglePersentageForCenter = BigDecimal.ZERO;
        BigDecimal lessCommisionSingleTot = BigDecimal.ZERO;
        BigDecimal lessCommisionSinglePersentageForIndividual = BigDecimal.ZERO;
        List<Transaction> todayTransactionDetailByDateNAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(formatedDate, account.getAccountNo());
        boolean ncAvailable=false;
        boolean lcsAvailable=false;
        List<Transaction> ncTransactionList= new ArrayList<Transaction>();
        List<Transaction> lcsTransactionList= new ArrayList<Transaction>();
        for(Transaction transaction1:todayTransactionDetailByDateNAccountNo){
            if(transaction1.getTypeId().equals("NC")){
                if(nc > 0.0){
                    if (center != null && center.getNotCommisionPersentage() != null && center.getNotCommisionPersentage().doubleValue() > 0.0) {
                        notcommisionPersentageForCenter = center.getNotCommisionPersentage();
                        notCommisionsTot = envelopeDAOService.calculateNotCommision(BigDecimal.valueOf(nc), notcommisionPersentageForCenter);
                    }
                    if (individual1 != null && individual1.getNotCommisionPersentage() != null && individual1.getNotCommisionPersentage().doubleValue() > 0.0) {
                        notcommisionPersentageForIndividual = individual1.getNotCommisionPersentage();
                        notCommisionsTot = envelopeDAOService.calculateNotCommision(BigDecimal.valueOf(nc), notcommisionPersentageForIndividual);
                    }
                    transaction1.setDebit(notCommisionsTot);
                    ncTransactionList.add(transaction1);
                    transactionDAOService.update(transaction1);
                }
                ncAvailable=true;
            }
            if(transaction1.getTypeId().equals("LCS")){
                if(lcs > 0.0){
                    if (center != null && center.getLessComissionSingle() != null  && center.getLessComissionSingle().doubleValue() > 0.0) {
                        lessCommisionSinglePersentageForCenter = center.getLessComissionSingle();
                        lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(BigDecimal.valueOf(lcs), lessCommisionSinglePersentageForCenter);
                    }
                    if (individual1 != null && individual1.getLessComissionSingle() != null && individual1.getLessComissionSingle().doubleValue() > 0.0) {
                        lessCommisionSinglePersentageForIndividual = individual1.getLessComissionSingle();
                        lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(BigDecimal.valueOf(lcs), lessCommisionSinglePersentageForIndividual);
                    }
                    transaction1.setDebit(lessCommisionSingleTot);
                    lcsTransactionList.add(transaction1);
                    transactionDAOService.update(transaction1);
                }
                lcsAvailable=true;
            }

        }
        if(ncTransactionList.size() > 1){
                int count=0;
                for(Transaction transaction1:ncTransactionList){
                    if(ncTransactionList.size()-count > 1){
                        transactionDAOService.delete(transaction1);
                        count++;
                    }
                }
        }
        if(lcsTransactionList.size() > 1){
            int count=0;
            for(Transaction transaction1:lcsTransactionList){
                if(lcsTransactionList.size()-count > 1){
                    transactionDAOService.delete(transaction1);
                    count++;
                }
            }
        }
        if(ncAvailable == false && nc > 0.0){
            if (center != null && center.getNotCommisionPersentage() != null && center.getNotCommisionPersentage().doubleValue() > 0.0) {
                notcommisionPersentageForCenter = center.getNotCommisionPersentage();
                notCommisionsTot = envelopeDAOService.calculateNotCommision(BigDecimal.valueOf(nc), notcommisionPersentageForCenter);
            }
            if (individual1 != null && individual1.getNotCommisionPersentage() != null && individual1.getNotCommisionPersentage().doubleValue() > 0.0) {
                notcommisionPersentageForIndividual = individual1.getNotCommisionPersentage();
                notCommisionsTot = envelopeDAOService.calculateNotCommision(BigDecimal.valueOf(nc), notcommisionPersentageForIndividual);
            }
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setDebit(notCommisionsTot);
            transaction.setTypeId("NC");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }
        if(lcsAvailable == false && lcs > 0.0){
            if (center != null && center.getLessComissionSingle() != null  && center.getLessComissionSingle().doubleValue() > 0.0) {
                lessCommisionSinglePersentageForCenter = center.getLessComissionSingle();
                lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(BigDecimal.valueOf(lcs), lessCommisionSinglePersentageForCenter);
            }
            if (individual1 != null && individual1.getLessComissionSingle() != null && individual1.getLessComissionSingle().doubleValue() > 0.0) {
                lessCommisionSinglePersentageForIndividual = individual1.getLessComissionSingle();
                lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(BigDecimal.valueOf(lcs), lessCommisionSinglePersentageForIndividual);
            }
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setDebit(lessCommisionSingleTot);
            transaction.setTypeId("LCS");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }


        if (chitList.size() > 0) {
            for (Chit chit : chitList) {
                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }
                if (chit.getFinish() == null || chit.getFinish() == false) {
                    chit.setFinish(true);
                    chitDAOService.update(chit);

                }
            }

        }


        return ResponseMessage.SUCCESS;
    }


    @RequestMapping(value = "getGeneralSummaryReceiptModel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getGeneralSummaryReceipt(@RequestBody GeneralSummaryReceiptModel generalSummaryReceiptModel) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());

        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;
        double balance = 0.0;
        double paymentToDeduct = 0.0;

        Date date1 = generalSummaryReceiptModel.getDate();

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        long milliseconds = date1.getTime();
        map.put("date", milliseconds + "");


        Transaction tra = new Transaction();
        Account account = accountDAOService.getAccountByCenterOIndividualId(generalSummaryReceiptModel.getIndividualId());
        Individual individual = individualDAOService.getBranchById(generalSummaryReceiptModel.getIndividualId());


        map.put("pc", "0.00");
        map.put("ln", "0.00");
        map.put("pd", "0.00");
        map.put("com", "0.00");
        map.put("nc", "0.00");
        map.put("lcs", "0.00");
        map.put("lon", "0.00");
        map.put("sal", "0.00");
        map.put("overPay", "0.00");
        map.put("exces", "0.00");

        Double dueAmount=0.0;
        Double perDue=0.0;
        List<Transaction> transactionList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(formatedDate, account.getAccountNo());
        for (Transaction transaction : transactionList) {
            if (transaction != null) {
                tra = transaction;

                double value = 0;
                if (tra.getCredit() != null && tra.getCredit().doubleValue() != 0) {
                    value = tra.getCredit().doubleValue();
                    outValue += value;

                } else if (tra.getDebit() != null && tra.getDebit().doubleValue() != 0) {
                    value = tra.getDebit().doubleValue();
                    inValue += value;
                }


                String ty = tra.getTypeId().toLowerCase();
                if (tra.getDebit() != null) {
                    Object put = map.put(ty, tra.getDebit() + "");
                }
                if (tra.getCredit() != null) {
                    Object put = map.put(ty, tra.getCredit() + "");
                }

                if (tra.getTypeId().equals("Inv")) {
                    totInvesment = tra.getDebit().doubleValue();
                }
                if(tra.getCredit() != null && tra.getTypeId().equals("Payment")){
                    paymentToDeduct= tra.getCredit().doubleValue()*-1;
                }

            }
        }

        Date curDate=generalSummaryReceiptModel.getDate();
        Date yesterday=new Date(curDate.getTime() - (1000 * 60 * 60 * 24));
        String yesterdayfD = simpleDateFormat.format(yesterday);

        //yesterday transation
        List<Transaction> trlist = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(yesterdayfD, account.getAccountNo());
        for (Transaction transaction : trlist) {
            if (transaction != null) {
                tra = transaction;

                if (tra.getDebit() != null && tra.getTypeId().equals("Balance")) {
                    perDue = tra.getDebit().doubleValue();
                    map.put("pd",perDue);
                }
            }
        }
//        map.put("csh",234234234+"");
        List<Envelope> envelopesByCenterId = null;

        if (generalSummaryReceiptModel.getType() == 0) {
            map.put("Individual", generalSummaryReceiptModel.getCenterId() == null ? "--" : generalSummaryReceiptModel.getCenterId());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(generalSummaryReceiptModel.getCenterId(), null, null, date1);
        } else if (generalSummaryReceiptModel.getType() == 1 && individual != null) {
            String fD = simpleDateFormat.format(date1);
            map.put("Individual", individual.getName());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(generalSummaryReceiptModel.getIndividualId(), null, null, fD);
        }

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("Inv");
        model.addColumn("Pay");
        model.addColumn("ncOfTable");


        Double totPayment = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;
        Double salary=0.0;
        BigDecimal notCommisionsTot = BigDecimal.ZERO;
        BigDecimal lessCommisionSingleTot = BigDecimal.ZERO;

        for (Envelope envelope : envelopesByCenterId) {

            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());
            Double payment = null;
            double ncValue = 0.0;
            double lcsValue = 0.0;
            BigDecimal notCommisionValue = BigDecimal.ZERO;
            BigDecimal notcommisionPersentageForCenter = BigDecimal.ZERO;
            BigDecimal lessCommisionSinglePersentageForIndividual = BigDecimal.ZERO;
            BigDecimal notcommisionPersentageForIndividual = BigDecimal.ZERO;
            BigDecimal lessCommisionSingleValue = BigDecimal.ZERO;
            BigDecimal lessCommisionSinglePersentageForCenter = BigDecimal.ZERO;


            for (Chit chit : chits) {
                String ncOLCS = null;
                if (chit.getNC() != null && chit.getNC() == true) {
                    ncOLCS = "NC";
                } else if (chit.getLCS() != null && chit.getLCS() == true) {
                    ncOLCS = "LCS";
                } else if (chit.getNC() == null && chit.getLCS() == null) {
                    ncOLCS = "";
                }
                model.addRow(new Object[]{chit.getNumber(), chit.getInvesment() == null ? "--" : chit.getInvesment() + "", chit.getAmount() == null ? "--" : chit.getAmount() + "", ncOLCS});
                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }
                if (chit != null && chit.getNC() != null && chit.getNC() == true && chit.getNcOLCValue() != null && chit.getNcOLCValue().doubleValue() > 0) {
                    ncValue += Double.parseDouble(chit.getNcOLCValue() + "");
                }
                if (chit != null && chit.getLCS() != null && chit.getLCS() == true && chit.getNcOLCValue() != null && chit.getNcOLCValue().doubleValue() > 0) {
                    lcsValue += Double.parseDouble(chit.getNcOLCValue() + "");
                }
            }
            notCommisionValue = BigDecimal.valueOf(ncValue);
            lessCommisionSingleValue = BigDecimal.valueOf(lcsValue);
            //this must be change after center persentage addded
            Center center = centerDAOService.getCenterById(generalSummaryReceiptModel.getCenterId());
            Individual individual1 = individualDAOService.getBranchById(generalSummaryReceiptModel.getIndividualId());

            if (center != null && center.getNotCommisionPersentage() != null) {
                notcommisionPersentageForCenter = center.getNotCommisionPersentage();
                notCommisionsTot = envelopeDAOService.calculateNotCommision(notCommisionValue, notcommisionPersentageForCenter);
            }
            if (center != null && center.getLessComissionSingle() != null) {
                lessCommisionSinglePersentageForCenter = center.getLessComissionSingle();
                lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(lessCommisionSingleValue, lessCommisionSinglePersentageForCenter);
            }
            if (individual1 != null && individual1.getNotCommisionPersentage() != null) {
                notcommisionPersentageForIndividual = individual1.getNotCommisionPersentage();
                notCommisionsTot = envelopeDAOService.calculateNotCommision(notCommisionValue, notcommisionPersentageForIndividual);
            }
            if (individual1 != null && individual1.getLessComissionSingle() != null) {
                lessCommisionSinglePersentageForIndividual = individual1.getLessComissionSingle();
                lessCommisionSingleTot = envelopeDAOService.calculateLessCommisionSingle(lessCommisionSingleValue, lessCommisionSinglePersentageForIndividual);
            }
            if(lessCommisionSingleTot != null && notCommisionsTot.doubleValue() > 0.0){
                map.put("lcs", lessCommisionSingleTot + "");
            }
            if (notCommisionsTot != null && notCommisionsTot.doubleValue() > 0.0) {
                map.put("nc", notCommisionsTot + "");
            }
            if (individual1 != null && individual1.getCommision() == null && individual1.getFixedSalary() == null && individual1.getCommision() == null) {
                if (individual1.isSalaryPay() == false) {
                    salary = envelopeDAOService.calculateSalary(BigDecimal.valueOf(totInvesment)).doubleValue();
                    map.put("sal", salary + "");
                }
            }


            if (individual1 != null && individual1.getCommision() == null && individual1.getFixedSalary() != null && individual1.getCommision() == null) {

                if (individual1.isSalaryPay() && individual1.getFixedSalary().doubleValue() > 0.0) {
                    salary = individual1.getFixedSalary().doubleValue();
                    map.put("sal", salary + "");
                }
            }
        }

        tpyPayment += totPayment;
//        if(perDue > 0.0){
//            tpyInvestment+=perDue;
//        }

        if(salary > 0.0){
            tpyPayment+=salary;
        }
//        if(notCommisionsTot.doubleValue() > 0.0){
//            tpyInvestment+=notCommisionsTot.doubleValue();
//        }

        if(lessCommisionSingleTot.doubleValue() > 0.0){
            tpyInvestment+=lessCommisionSingleTot.doubleValue();
        }

        if(paymentToDeduct > 0.0){
            tpyPayment+=paymentToDeduct;
        }
        map.put("totInv", totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");


        dueAmount = tpyInvestment-tpyPayment ;

        map.put("dueBalance", dueAmount == null ? "--" : dueAmount + "");

        List<ApprovedLoan> approvedLoanList = approvedLoanDAOService.getUnpaidLoansByIndividualId(generalSummaryReceiptModel.getIndividualId());
        BigDecimal approveLoanDueAmount = BigDecimal.ZERO;
        for (ApprovedLoan approvedLoan : approvedLoanList) {
            if (approvedLoan != null && approvedLoan.getDueamount() != null) {
                approveLoanDueAmount = approveLoanDueAmount.add(approvedLoan.getDueamount());
                //map.put("ln", approvedLoan.getDeductionPayment()+"");
            }
        }
        map.put("loanDue", approveLoanDueAmount == null ? "--" : approveLoanDueAmount + "");


        return map;
    }

    /**
     * Get package Id
     *
     * @return
     */
    @RequestMapping(value = "getEnvelopeDetailModelByIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public EnvelopeDetailModel getEnvelopeDetailModelByIndividualId(@RequestParam("individualId") String individualId) {
        return null;
    }

    private String getNewId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    /**
     * get individual by individualId
     *
     * @param IndividualId
     * @return
     */
    @RequestMapping(value = "readById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Individual readById(@RequestParam("individualId") String IndividualId) {
        return individualDAOService.readById(IndividualId);
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

}
