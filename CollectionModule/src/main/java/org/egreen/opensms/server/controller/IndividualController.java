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

        Transaction tra = new Transaction();
        Map<String, Object> summaryReceipt = getGeneralSummaryReceipt(generalSummaryReceiptModel);

        Object due = summaryReceipt.get("dueBalance");
        Double dueValue = 0.0;
        try {
            dueValue = Double.parseDouble(due + "");
        } catch (Exception e) {

        }

        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;

        Double totPayment = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;

        Transaction transaction = null;
        String indiviId = generalSummaryReceiptModel.getIndividualId();
        Account account = accountDAOService.getAccountByCenterOIndividualId(indiviId);
        if (dueValue > 0) {
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(generalSummaryReceiptModel.getBalance()));
            transaction.setTypeId("Balance");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);

            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(account.getAccountNo());
            double pd=0.0;
            pd=dueValue-generalSummaryReceiptModel.getBalance();
            transaction.setDebit(BigDecimal.valueOf(pd));
            if(dueValue<0){
                pd=dueValue+generalSummaryReceiptModel.getBalance();
                transaction.setDebit(BigDecimal.valueOf(pd));
            }
            transaction.setTypeId("PD");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);

        }
        if(dueValue < 0){
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
            pd=dueValue+generalSummaryReceiptModel.getBalance();
            transaction.setCredit(BigDecimal.valueOf(pd));
            transaction.setTypeId("PD");
            transaction.setTime(generalSummaryReceiptModel.getDate());
            transactionDAOService.save(transaction);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());
        String individualId = generalSummaryReceiptModel.getIndividualId();
        List<Chit> chitList = chitDAOService.getAllChithsByFormattedDateNIndividualId(formatedDate, individualId);

        if (chitList.size() > 0) {
            for (Chit chit : chitList) {
                System.out.println(chit);
                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }
                if (chit.getFinish() == null || chit.getFinish() == false) {
                    chit.setFinish(true);
                    System.out.println(chit);
                    chitDAOService.update(chit);

                }
            }

        }
        tpyPayment += totPayment;

        Double dueAmount = dueValue-generalSummaryReceiptModel.getBalance();

//        individualDAOService.closeDayBalance(generalSummaryReceiptModel.getIndividualId(),generalSummaryReceiptModel.getDate());


        return ResponseMessage.SUCCESS;
    }


    @RequestMapping(value = "getGeneralSummaryReceiptModel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getGeneralSummaryReceipt(@RequestBody GeneralSummaryReceiptModel generalSummaryReceiptModel) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());

        System.out.println(generalSummaryReceiptModel);

        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;
        double balance = 0.0;

        Date date1 = generalSummaryReceiptModel.getDate();

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        long milliseconds = date1.getTime();
        map.put("date", milliseconds + "");


        Transaction tra = new Transaction();
        Account account = accountDAOService.getAccountByCenterOIndividualId(generalSummaryReceiptModel.getIndividualId());
        Individual individual = individualDAOService.getBranchById(generalSummaryReceiptModel.getIndividualId());


        map.put("pc", "");
        map.put("ln", "--");
        map.put("pd", "--");
        map.put("com", "--");
        map.put("nc", "--");
        map.put("lcs", "--");
        map.put("lon", "--");
        map.put("sal", "--");
        map.put("overPay", "--");
        map.put("exces", "--");

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

                if (tra.getTypeId().equals("Balance")) {
                    balance = tra.getDebit().doubleValue();
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

                if (tra.getCredit() != null && tra.getTypeId().equals("Balance")) {
                    perDue = tra.getCredit().doubleValue();
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
            BigDecimal notCommisionsTot = BigDecimal.ZERO;
            BigDecimal lessCommisionSingleTot = BigDecimal.ZERO;
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
//                    System.out.println("LCS ->"+chit.getNcOLCValue());
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
                map.put("lcs", lessCommisionSingleTot.doubleValue() + "");
            }
            if (notCommisionsTot != null && notCommisionsTot.doubleValue() > 0.0) {
                map.put("nc", notCommisionsTot + "");
            }
            if (individual1 != null && individual1.getCommision() == null && individual1.getFixedSalary() == null && individual1.getCommision() == null) {
                if (individual1.isSalaryPay() == false) {
                    BigDecimal salary = envelopeDAOService.calculateSalary(BigDecimal.valueOf(totInvesment));
                    map.put("sal", salary + "");

                }
            }


            if (individual1 != null && individual1.getCommision() == null && individual1.getFixedSalary() != null && individual1.getCommision() == null) {

                if (individual1.isSalaryPay() && individual1.getFixedSalary().doubleValue() > 0.0) {

                    BigDecimal salary = individual1.getFixedSalary();
                    System.out.println("inside the condition" + individual1.getFixedSalary());
                    map.put("sal", salary + "");

                }
            }


        }

        tpyPayment += totPayment;

        map.put("totInv", totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");


        dueAmount = tpyInvestment-tpyPayment ;
//
//        if (totInvesment != null && totPayment != null) {
//            dueAmount = tpyInvestment.doubleValue() - totPayment.doubleValue();
//        }


        map.put("dueBalance", dueAmount == null ? "--" : dueAmount + "");

//        ApprovedLoan approvedLoan = approvedLoanDAOService.getOpenLoanDetailByIndividualId(individualId);
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
