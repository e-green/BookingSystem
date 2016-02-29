package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.EnvelopeDetailModel;
import org.egreen.opensms.server.models.GeneralSummaryReceiptModel;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            if (account!=null){
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

        System.out.println(new GeneralSummaryReceiptModel());

        Map<String, Object> summaryReceipt = getGeneralSummaryReceipt(generalSummaryReceiptModel);

        Object due = summaryReceipt.get("due");
        Double dueValue=0.0;
        try {
            dueValue=Double.parseDouble(due+"");
        }catch (Exception e){

        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());
        String individualId = generalSummaryReceiptModel.getIndividualId();
        List<Chit> chitList=chitDAOService.getAllChithsByFormattedDateNIndividualId(formatedDate,individualId);

        for (Chit chit : chitList) {
            if(chit.getFinish()== false){
                chit.setFinish(true);
                chitDAOService.update(chit);
            }
        }

//        individualDAOService.closeDayBalance(generalSummaryReceiptModel.getIndividualId(),generalSummaryReceiptModel.getDate());




        return ResponseMessage.SUCCESS;
    }


    @RequestMapping(value = "getGeneralSummaryReceiptModel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getGeneralSummaryReceipt(@RequestBody GeneralSummaryReceiptModel generalSummaryReceiptModel) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
        String formatedDate = simpleDateFormat.format(generalSummaryReceiptModel.getDate());
//        GenralSummeryReportModel summery = new GenralSummeryReportModel();

        System.out.println(generalSummaryReceiptModel);

        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;

        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("date", formatedDate + "");

        map.put("pc", "--");
        map.put("ln", "--");
        map.put("pd", "--");
        map.put("com", "--");

        map.put("lon", "--");

        Account account = accountDAOService.getAccountByCenterOIndividualId(generalSummaryReceiptModel.getIndividualId());
        Individual individual = individualDAOService.getBranchById(generalSummaryReceiptModel.getIndividualId());


        List<Transaction> transactionList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(formatedDate, account.getAccountNo());
        for (Transaction transaction : transactionList) {

            Transaction tra = new Transaction();
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

                map.put(tra.getTypeId().toLowerCase(), value + "");

//                System.out.println(tra);

//                if(tra.getTypeId().equals("NC")){
//
//                }
//                if(tra.getTypeId().equals("CASH")){
//                    map.put("csh",tra.getCredit()+"");
//                }
//                if(tra.getTypeId().equals("exp")){
//                    map.put("exp",tra.getCredit()+"");
//                }

                if (tra.getTypeId().equals("Inv")) {
                    totInvesment = tra.getDebit().doubleValue();
                }
            }
        }


        List<Envelope> envelopesByCenterId = null;

        if (generalSummaryReceiptModel.getType() == 0) {
            map.put("Individual", generalSummaryReceiptModel.getCenterId() == null ? "--" : generalSummaryReceiptModel.getCenterId());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(generalSummaryReceiptModel.getCenterId(), null, null, generalSummaryReceiptModel.getDate());
        } else if (generalSummaryReceiptModel.getType() == 1 && individual != null) {
            map.put("Individual", individual.getName());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(generalSummaryReceiptModel.getIndividualId(), null, null, generalSummaryReceiptModel.getDate()+"");
        }

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("Inv");
        model.addColumn("Pay");


        Double totPayment = 0.0;
//        Double totInDouble = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;


        for (Envelope envelope : envelopesByCenterId) {

            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());
            Double payment = null;

            for (Chit chit : chits) {


                model.addRow(new Object[]{chit.getNumber(), chit.getInvesment() == null ? "--" : chit.getInvesment() + "", chit.getAmount() == null ? "--" : chit.getAmount() + ""});

                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }

//                if (chit.getInvesment() != null) {
//                    totInvesment += chit.getInvesment().doubleValue();
//                }
            }

        }

        tpyPayment += totPayment;

        map.put("totInv", totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");

        Double dueAmount = tpyInvestment - tpyPayment;
//
//        if (totInvesment != null && totPayment != null) {
//            dueAmount = tpyInvestment.doubleValue() - totPayment.doubleValue();
//        }

        map.put("due", dueAmount == null ? "--" : dueAmount + "");


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


}
