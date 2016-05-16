package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.GeneralSummaryReceiptModel;
import org.egreen.opensms.server.models.GeneralSummaryReportModel;
import org.egreen.opensms.server.models.ReportModel;
import org.egreen.opensms.server.service.*;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by pramoda-nf on 10/30/15.
 */


@Controller
@RequestMapping("booking/v1/jasperReport")
public class JasperReportController {


    @Autowired
    private IndividualDAOService individualDAOService;

    @Autowired
    private EnvelopeDAOService envelopeDAOService;

    @Autowired
    private ChitDAOService chitDAOService;

    @Autowired
    private TransactionDAOService transactionDAOService;

    @Autowired
    private AccountDAOService accountDAOService;

    @Autowired
    private ApprovedLoanDAOService approvedLoanDAOService;

    @Autowired
    private CenterDAOService centerDAOService;


    /**
     * Get General Summary Report Model
     *
     * @param centerIdFrom
     * @param sTime
     * @return
     */
    @RequestMapping(value = "getGeneralSummaryReportModel", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public GeneralSummaryReportModel getGeneralSummaryReportModel(@RequestParam("centerId") String centerIdFrom,@RequestParam("sTime") String sTime) {
        String stringTime = sTime;
        String centerId = centerIdFrom;


        Double totInvesment=0.0;
        Double totPayment=0.0;
        Double payment = 0.0;
        Double investment = 0.0;
        Double commision=0.0;
        Double cash=0.0;
        Double paymentDue=0.0;
        Double excess=0.0;
        Double expenses=0.0;
        Double loanDeductionPayment=0.0;
        Double tpyPayment=0.0;
        Double tpyInvestment=0.0;
        Double pcCharges=0.0;
        Double loan=0.0;
        Double loanDeductValue=0.0;
        double ncValue=0.0;
        double lcsValue=0.0;
        Timestamp timestamp=null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(stringTime);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e) {
        }

        Center center = centerDAOService.getCenterById(centerId);
        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, stringTime);

        // get individual list by centerId
        List<Individual> individualsByCenterId = individualDAOService.getIndividualsByCenterId(centerId);
        int individualCount=0;
        // each Individual total values calculating
        for (Individual individual:individualsByCenterId) {
            individualCount++;
            Account accountByIndividualId = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            List<Transaction> transactionListByAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(stringTime, accountByIndividualId.getAccountNo());
            if (!transactionListByAccountNo.isEmpty()) {
                for (Transaction transaction:transactionListByAccountNo) {
                    if(transaction != null){
                        if(transaction.getTypeId().equals("PAY")){
                            totPayment+=transaction.getCredit().doubleValue();
                            payment+=transaction.getCredit().doubleValue();
                        }
                        if (transaction.getTypeId().equals("Inv")) {
                            totInvesment += transaction.getDebit().doubleValue();
                            investment += transaction.getDebit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("LN")){
                            loanDeductValue+=transaction.getDebit().doubleValue();
                        }
                    }
                }
            }
        }

        Account accountByCenterId = accountDAOService.getAccountByCenterOIndividualId(centerId);
        List<Transaction> trList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(stringTime, accountByCenterId.getAccountNo());
        Transaction transaction=null;
        boolean isExistCash=false;
        boolean isExistExcess=false;
        boolean isExistExpenses=false;
        boolean isExistLoanDeductionPayment=false;
        boolean isExistLoan=false;
        boolean isExistDue=false;
        if (!trList.isEmpty()) {
            for (Transaction tran:trList) {
                if(tran != null){
                    if(tran.getDebit() != null && tran.getTypeId().equals("NC")){
                        ncValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LCS")){
                        lcsValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        paymentDue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LN")){
                        loanDeductionPayment+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LON")){
                        loan+=tran.getCredit().doubleValue();
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("COM")){
                        commision+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LN")){
                        isExistLoanDeductionPayment=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("LON")){
                        isExistLoan=true;
                    }
                }
            }
        }

        ApprovedLoan approvedLoan=null;
        if(centerId != null){
            approvedLoan = approvedLoanDAOService.getOpenLoanDetailByCenterlId(centerId);
        }
        //if there is no current LoanDeductionPayment value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoanDeductionPayment == false){
            if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {
                BigDecimal dueAmount=null;
                if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null){
                    dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());
                    loanDeductionPayment=approvedLoan.getDeductionPayment().doubleValue();
                }
            }
        }

        if(isExistLoan == false){
            if (approvedLoan!=null&&approvedLoan.getsTime().equals(stringTime)) {
                BigDecimal dueAmount=null;
                if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null){
                    loan = approvedLoan.getDueamount().doubleValue();
                }
            }
        }

        if(center.getPcChargers() != null){
            pcCharges=center.getPcChargers().doubleValue();
        }

        tpyInvestment+=totInvesment;
        tpyPayment+=totPayment;
        tpyInvestment+=paymentDue;
        tpyPayment+=cash;
        tpyPayment+=commision;
        tpyInvestment+=ncValue;

        tpyInvestment+=loanDeductionPayment;

        tpyInvestment+=loanDeductValue;
        tpyInvestment += lcsValue;
        tpyInvestment+=pcCharges;
        tpyPayment+=excess;
        tpyPayment+=expenses;
        tpyPayment+=loan;

        Double dueAmount = tpyInvestment - tpyPayment;

        GeneralSummaryReportModel reportModel=new GeneralSummaryReportModel();
        reportModel.setCenterId(centerId);
        reportModel.setsTime(stringTime);
        reportModel.setInvestment(investment);
        reportModel.setPay(payment);
        reportModel.setNc(ncValue);
        reportModel.setLcs(lcsValue);
        reportModel.setPc(pcCharges);
        reportModel.setPd(paymentDue);
        reportModel.setLn(loanDeductionPayment);
        reportModel.setLd(loanDeductValue);
        reportModel.setCommision(commision);
        reportModel.setCash(cash);
        reportModel.setExcess(excess);
        reportModel.setExpences(expenses);
        reportModel.setLoan(loan);
        reportModel.setTpyInv(tpyInvestment);
        reportModel.setTpyPay(tpyPayment);
        reportModel.setDueAmount(dueAmount);


        return reportModel;

    }
    
    
    /**
     * getGeneralSummaryReport
     *
     * @param session
     * @param response
     * @param centerId
     */
    @RequestMapping(value = "getGeneralSummaryReport", method = RequestMethod.GET)
    public void getCustomerReport(HttpSession session, HttpServletResponse response,
                                  @RequestParam("centerId") String centerId,
                                  @RequestParam("datetime") String date,
                                  @RequestParam("onCash") double onCash,
                                  @RequestParam("onExpenses") double onExpenses,
                                  @RequestParam("onExcess") double onExcess,
                                  @RequestParam("loanDeduct") boolean loanDeduct
    ) {

        double addCash=onCash;
        double addExpenses=onExpenses;
        double addExcess=onExcess;
        Double totInvesment=0.0;
        Double totPayment=0.0;
        Double payment = 0.0;
        Double investment = 0.0;
        Double commision=0.0;
        Double cash=0.0;
        Double paymentDue=0.0;
        Double excess=0.0;
        Double expenses=0.0;
        Double loanDeductionPayment=0.0;
        Double tpyPayment=0.0;
        Double tpyInvestment=0.0;
        Double pcCharges=0.0;
        Double loan=0.0;
        Double loanDeductValue=0.0;
        double ncValue=0.0;
        double lcsValue=0.0;
        Timestamp timestamp=null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){
        }

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        map.put("centerName", centerId);
        map.put("date", date + "");

        Center center = centerDAOService.getCenterById(centerId);
        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("name");
        model.addColumn("investment");
        model.addColumn("payment");


        // get individual list by centerId
        List<Individual> individualsByCenterId = individualDAOService.getIndividualsByCenterId(centerId);
        int individualCount=0;
        // each Individual total values calculating
        for (Individual individual:individualsByCenterId) {
            individualCount++;
            Account accountByIndividualId = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            List<Transaction> transactionListByAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByIndividualId.getAccountNo());
            if (!transactionListByAccountNo.isEmpty()) {
                for (Transaction transaction:transactionListByAccountNo) {
                    if(transaction != null){
                        if(transaction.getTypeId().equals("PAY")){
                            totPayment+=transaction.getCredit().doubleValue();
                            payment=transaction.getCredit().doubleValue();
                        }
                        if (transaction.getTypeId().equals("Inv")) {
                            totInvesment += transaction.getDebit().doubleValue();
                            investment = transaction.getDebit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("LN")){
                            loanDeductValue+=transaction.getDebit().doubleValue();
                        }
                    }
                }
                model.addRow(new Object[]{individualCount+"",  individual.getName()+ "",  investment+"", payment+""});
            }
        }

        Account accountByCenterId = accountDAOService.getAccountByCenterOIndividualId(centerId);
        List<Transaction> trList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByCenterId.getAccountNo());
        Transaction transaction=null;
        boolean isExistCash=false;
        boolean isExistExcess=false;
        boolean isExistExpenses=false;
        boolean isExistLoanDeductionPayment=false;
        boolean isExistLoan=false;
        boolean isExistDue=false;
        if (!trList.isEmpty()) {
            for (Transaction tran:trList) {
                if(tran != null){
                    if(tran.getDebit() != null && tran.getTypeId().equals("NC")){
                        ncValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LCS")){
                        lcsValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("CSH") && addCash > 0){
                        tran.setCredit(BigDecimal.valueOf(addCash));
                        transactionDAOService.update(tran);
                        cash+=addCash;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess") && addExcess > 0){
                        tran.setCredit(BigDecimal.valueOf(addExcess));
                        transactionDAOService.update(tran);
                        excess+=addExcess;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP") && addExpenses > 0){
                        tran.setCredit(BigDecimal.valueOf(addExpenses));
                        transactionDAOService.update(tran);
                        expenses+=addExpenses;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        paymentDue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LN")){
                        loanDeductionPayment+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LON")){
                        loan+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        isExistDue=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("CSH")){
                        isExistCash=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess")){
                        isExistExcess=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP")){
                        isExistExpenses=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("COM")){
                        commision+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LN")){
                        isExistLoanDeductionPayment=true;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LON")){
                        isExistLoan=true;
                    }
                }
            }
        }
        //if there is no current cash value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistCash == false && addCash > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("CSH");
            transaction.setCredit(BigDecimal.valueOf(addCash));
            transactionDAOService.save(transaction);
            cash+=addCash;
        }
        //if there is no current Excess value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExcess == false && addExcess > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("Excess");
            transaction.setCredit(BigDecimal.valueOf(addExcess));
            transactionDAOService.save(transaction);
            excess+=addExcess;
        }
        //if there is no current Expenses value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExpenses == false && addExpenses > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("EXP");
            transaction.setCredit(BigDecimal.valueOf(addExpenses));
            transactionDAOService.save(transaction);
            expenses+=addExpenses;
        }
        ApprovedLoan approvedLoan=null;
        if(centerId != null){
            approvedLoan = approvedLoanDAOService.getOpenLoanDetailByCenterlId(centerId);
        }
        //if there is no current LoanDeductionPayment value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoanDeductionPayment == false){
            if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LN");
                BigDecimal dueAmount=null;
                if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null && loanDeduct == true){
                    dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());
                }

                if( loanDeduct == true){
                    transaction.setDebit(approvedLoan.getDeductionPayment());
                    transaction.setTime(timestamp);
                    transaction.setsTime(date);
                    transactionDAOService.save(transaction);
                    loanDeductionPayment+=approvedLoan.getDeductionPayment().doubleValue();
                }
                if(approvedLoan.getDueamount() != null && dueAmount != null){
                    approvedLoan.setDueamount(dueAmount);
                }
                approvedLoanDAOService.update(approvedLoan);
            }
        }
        //if there is no current ExistLoan value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoan == false){
            if (approvedLoan!=null&&approvedLoan.getsTime().equals(date)) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LON");
                transaction.setCredit(approvedLoan.getDueamount());
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                loan+=approvedLoan.getDueamount().doubleValue();
            }
        }

        if(isExistDue == false){
            if(accountByCenterId != null && accountByCenterId.getAmount() != null && accountByCenterId.getAmount().doubleValue() > 0.0){
                transaction = new Transaction();
                transaction.setTransactionId(getNewId());
                transaction.setAccountNo(accountByCenterId.getAccountNo());
                transaction.setDebit(accountByCenterId.getAmount());
                transaction.setTypeId("PD");
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                paymentDue+=accountByCenterId.getAmount().doubleValue();
            }
        }

        if(center.getPcChargers() != null){
            pcCharges=center.getPcChargers().doubleValue();
        }

        map.put("totInv",totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");
        map.put("nc",ncValue+"");
        map.put("lcs", lcsValue + "");
        map.put("com",commision+"");
        map.put("csh",cash+"");
        map.put("pc",pcCharges+"");
        map.put("exces",excess+"");
        map.put("exp",expenses+"");
        map.put("due",paymentDue+"");
        map.put("ln",loanDeductionPayment+"");
        map.put("lon",loan+"");
        map.put("ld",loanDeductValue+"");

        tpyInvestment+=totInvesment;
        tpyPayment+=totPayment;
        tpyInvestment+=paymentDue;
        tpyPayment+=cash;
        tpyPayment+=commision;
        tpyInvestment+=ncValue;
        tpyInvestment+=loanDeductionPayment;
        tpyInvestment+=loanDeductValue;
        tpyInvestment += lcsValue;
        tpyInvestment+=pcCharges;
        tpyPayment+=excess;
        tpyPayment+=expenses;
        tpyPayment+=loan;


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");

        Double dueAmount = tpyInvestment - tpyPayment;


        if(dueAmount > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setDebit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);
            accountByCenterId.setAmount(BigDecimal.valueOf(dueAmount));
            accountDAOService.update(accountByCenterId);

            map.put("dueTot", dueAmount == null ? "--" : dueAmount + "");
            map.put("dueLable", "Due");
            map.put("paymentLable", "");
            map.put("payment", "");
            map.put("tpyInvDeduct", tpyPayment == null ? "--" : tpyPayment+"");
            map.put("tpyPayDeduct", "");
        }

        if(dueAmount < 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);

            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount*-1));
            transaction.setTypeId("Payment");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);

            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("payment", dueAmount == null ? "--" : dueAmount*-1 + "");
            map.put("dueLable", "");
            map.put("dueTot", "");
            map.put("paymentLable", "Payment");
            map.put("tpyPayDeduct", tpyInvestment == null ? "--" : tpyInvestment+"");
            map.put("tpyInvDeduct", "");
        }
        if(dueAmount == 0.0){
            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("dueTot","");
            map.put("paymentLable", "Balance");
            map.put("payment", "0.0");
            map.put("tpyInvDeduct", "");
            map.put("tpyPayDeduct", "");
            map.put("dueLable", "");
        }

        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GeneralSummary4.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
            // JasperViewer.viewReport(jp, false);
            File pdf = File.createTempFile("output.", ".pdf");
            JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
            try {
                InputStream inputStream = new FileInputStream(pdf);
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachment; filename=" + centerId + ".pdf");

                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * get Genaral Summary Of Salary
     *
     * @param session
     * @param response
     * @param centerId
     */
    @RequestMapping(value = "getGenaralSummaryOfSalary", method = RequestMethod.GET)
    public void getCustomerReport(HttpSession session, HttpServletResponse response,
                                  @RequestParam("centerId") String centerId,
                                  @RequestParam("datetime") String date,
                                  @RequestParam("onExpenses") double onExpenses,
                                  @RequestParam("onExcess") double onExcess,
                                  @RequestParam("loanDeduct") boolean loanDeduct
    ) {

        double addExpenses=onExpenses;
        double addExcess=onExcess;
        Double totInvesment=0.0;
        Double totPayment=0.0;
        Double investment = 0.0;
        Double commision=0.0;
        Double paymentDue=0.0;
        Double excess=0.0;
        Double expenses=0.0;
        Double loanDeductionPayment=0.0;
        Double tpyPayment=0.0;
        Double tpyInvestment=0.0;
        Double pcCharges=0.0;
        Double loan=0.0;
        Double loanDeductValue=0.0;
        double ncValue=0.0;
        double ncVal=0.0;
        double totNc=0.0;
        double lcsValue=0.0;
        double lcsVal=0.0;
        double totLcs=0.0;
        double tot=0.0;
        double total=0.0;


        Timestamp timestamp=null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){
        }

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        map.put("centerName", centerId);
        map.put("date", date + "");

        Center center = centerDAOService.getCenterById(centerId);
        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("name");
        model.addColumn("investment");
        model.addColumn("ncField");
        model.addColumn("lcsField");


        // get individual list by centerId
        List<Individual> individualsByCenterId = individualDAOService.getIndividualsByCenterId(centerId);
        int individualCount=0;
        // each Individual total values calculating
        for (Individual individual:individualsByCenterId) {
            List<Chit> chitList = chitDAOService.getAllChithsByFormattedDateNIndividualId(date, individual.getIndividualId());
            if(!chitList.isEmpty()){
                for(Chit chit:chitList){
                    if(chit.getNC() != null && chit.getNcOLCValue() != null && chit.getIndividualId().equals(individual.getIndividualId()) && chit.getNC() == true && chit.getNcOLCValue().doubleValue() > 0){
                        ncVal=chit.getNcOLCValue().doubleValue();
                        totNc+=ncVal;
                    }
                    if(chit.getLCS() != null && chit.getNcOLCValue() != null && chit.getIndividualId().equals(individual.getIndividualId()) && chit.getLCS() == true && chit.getNcOLCValue().doubleValue() > 0){
                        lcsVal=chit.getNcOLCValue().doubleValue();
                        totLcs+=lcsVal;
                    }
                }
            }

            individualCount++;
            Account accountByIndividualId = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            List<Transaction> transactionListByAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByIndividualId.getAccountNo());

            if (!transactionListByAccountNo.isEmpty()) {
                for (Transaction transaction:transactionListByAccountNo) {
                    if(transaction != null){
                        if (transaction.getTypeId().equals("Inv")) {
                            totInvesment += transaction.getDebit().doubleValue();
                            investment = transaction.getDebit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("LN")){
                            loanDeductValue+=transaction.getDebit().doubleValue();
                        }
                    }
                }
                model.addRow(new Object[]{individualCount+"",  individual.getName()+ "",  investment+"", ncVal+"", lcsVal+""});
                System.out.println("No:"+individualCount+" | Individual Name:"+individual.getName()+" | Investment :"+investment);
                investment=0.0;
                ncVal=0.0;
                lcsVal=0.0;
            }

        }


        Account accountByCenterId = accountDAOService.getAccountByCenterOIndividualId(centerId);
        List<Transaction> trList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByCenterId.getAccountNo());
        Transaction transaction=null;
        boolean isExistCash=false;
        boolean isExistExcess=false;
        boolean isExistExpenses=false;
        boolean isExistLoanDeductionPayment=false;
        boolean isExistLoan=false;
        boolean isExistDue=false;
        if (!trList.isEmpty()) {
            for (Transaction tran:trList) {
                if(tran != null){
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess") && addExcess > 0){
                        tran.setCredit(BigDecimal.valueOf(addExcess));
                        transactionDAOService.update(tran);
                        excess+=addExcess;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP") && addExpenses > 0){
                        tran.setCredit(BigDecimal.valueOf(addExpenses));
                        transactionDAOService.update(tran);
                        expenses+=addExpenses;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        paymentDue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LN")){
                        loanDeductionPayment+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LON")){
                        loan+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        isExistDue=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess")){
                        isExistExcess=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP")){
                        isExistExpenses=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("COM")){
                        commision+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LN")){
                        isExistLoanDeductionPayment=true;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LON")){
                        isExistLoan=true;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("NC")){
                        ncValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LCS")){
                        lcsValue+=tran.getDebit().doubleValue();
                    }
                }
            }
        }
        //if there is no current Excess value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExcess == false && addExcess > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("Excess");
            transaction.setCredit(BigDecimal.valueOf(addExcess));
            transactionDAOService.save(transaction);
            excess+=addExcess;
        }
        //if there is no current Expenses value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExpenses == false && addExpenses > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("EXP");
            transaction.setCredit(BigDecimal.valueOf(addExpenses));
            transactionDAOService.save(transaction);
            expenses+=addExpenses;
        }
        ApprovedLoan approvedLoan=null;
        if(centerId != null){
            approvedLoan = approvedLoanDAOService.getOpenLoanDetailByCenterlId(centerId);
        }
        //if there is no current LoanDeductionPayment value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoanDeductionPayment == false){
            if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LN");
                BigDecimal dueAmount=null;
                if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null && loanDeduct == true){
                    dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());
                }

                if( loanDeduct == true){
                    transaction.setDebit(approvedLoan.getDeductionPayment());
                    transaction.setTime(timestamp);
                    transaction.setsTime(date);
                    transactionDAOService.save(transaction);
                    loanDeductionPayment+=approvedLoan.getDeductionPayment().doubleValue();
                }
                if(approvedLoan.getDueamount() != null && dueAmount != null){
                    approvedLoan.setDueamount(dueAmount);
                }
                approvedLoanDAOService.update(approvedLoan);
            }
        }
        //if there is no current ExistLoan value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoan == false){
            if (approvedLoan!=null&& approvedLoan.getsTime().equals(date)
                    ) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LON");
                transaction.setCredit(approvedLoan.getDueamount());
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                loan+=approvedLoan.getDueamount().doubleValue();
            }
        }

        if(isExistDue == false){
            if(accountByCenterId != null && accountByCenterId.getAmount() != null && accountByCenterId.getAmount().doubleValue() > 0.0){
                transaction = new Transaction();
                transaction.setTransactionId(getNewId());
                transaction.setAccountNo(accountByCenterId.getAccountNo());
                transaction.setDebit(accountByCenterId.getAmount());
                transaction.setTypeId("PD");
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                paymentDue+=accountByCenterId.getAmount().doubleValue();
            }
        }

        if(center.getPcChargers() != null){
            pcCharges=center.getPcChargers().doubleValue();
        }
        tot=ncValue+lcsValue+pcCharges+loanDeductionPayment+excess+expenses;

        total=commision-tot;

        map.put("totInv",totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");
        map.put("totNc", totNc+"");
        map.put("totLcs", totLcs+"");
        map.put("nc",ncValue+"");
        map.put("lcs", lcsValue + "");
        map.put("com",commision+"");
        map.put("pc",pcCharges+"");
        map.put("exces",excess+"");
        map.put("exp",expenses+"");
        map.put("due",paymentDue+"");
        map.put("ln",loanDeductionPayment+"");
        map.put("lon",loan+"");
        map.put("ld",loanDeductValue+"");
        map.put("tot",tot+"");
        map.put("total",total+"");

        tpyInvestment+=totInvesment;
        tpyPayment+=totPayment;
        tpyInvestment+=paymentDue;
        tpyPayment+=commision;
        tpyInvestment+=ncValue;
        tpyInvestment+=loanDeductionPayment;
        tpyInvestment+=loanDeductValue;
        tpyInvestment += lcsValue;
        tpyInvestment+=pcCharges;
        tpyPayment+=excess;
        tpyPayment+=expenses;
        tpyPayment+=loan;


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");

        Double dueAmount = tpyInvestment - tpyPayment;


        if(dueAmount > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setDebit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);
            accountByCenterId.setAmount(BigDecimal.valueOf(dueAmount));
            accountDAOService.update(accountByCenterId);

            map.put("dueTot", dueAmount == null ? "--" : dueAmount + "");
            map.put("dueLable", "Due");
            map.put("paymentLable", "");
            map.put("payment", "");
            map.put("tpyInvDeduct", tpyPayment == null ? "--" : tpyPayment+"");
            map.put("tpyPayDeduct", "");
        }

        if(dueAmount < 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);

            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount*-1));
            transaction.setTypeId("Payment");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);

            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("payment", dueAmount == null ? "--" : dueAmount*-1 + "");
            map.put("dueLable", "");
            map.put("dueTot", "");
            map.put("paymentLable", "Payment");
            map.put("tpyPayDeduct", tpyInvestment == null ? "--" : tpyInvestment+"");
            map.put("tpyInvDeduct", "");
        }
        if(dueAmount == 0.0){
            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("dueTot","");
            map.put("paymentLable", "Balance");
            map.put("payment", "0.0");
            map.put("tpyInvDeduct", "");
            map.put("tpyPayDeduct", "");
            map.put("dueLable", "");
        }

        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryOfSalary2.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
            // JasperViewer.viewReport(jp, false);
            File pdf = File.createTempFile("output.", ".pdf");
            JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
            try {
                InputStream inputStream = new FileInputStream(pdf);
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachment; filename=" + centerId + ".pdf");

                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

 /**
     * get Genaral Summary Of Cash
     *
     * @param session
     * @param response
     * @param centerId
     */
    @RequestMapping(value = "getGenaralSummaryWithCash", method = RequestMethod.GET)
    public void getGenaralSummaryWithCash(HttpSession session, HttpServletResponse response,
                                  @RequestParam("centerId") String centerId,
                                  @RequestParam("datetime") String date,
                                  @RequestParam("onExpenses") double onExpenses,
                                  @RequestParam("onExcess") double onExcess,
                                  @RequestParam("loanDeduct") boolean loanDeduct
    ) {

        double addExpenses=onExpenses;
        double addExcess=onExcess;
        Double totInvesment=0.0;
        Double totPayment=0.0;
        Double totPay=0.0;
        Double totCash=0.0;
        Double investment = 0.0;
        Double commision=0.0;
        Double paymentDue=0.0;
        Double excess=0.0;
        Double expenses=0.0;
        Double loanDeductionPayment=0.0;
        Double tpyPayment=0.0;
        Double tpyInvestment=0.0;
        Double pcCharges=0.0;
        Double loan=0.0;
        Double loanDeductValue=0.0;
        Double payValue=0.0;
        Double cashValue=0.0;
        double ncValue=0.0;
        double ncVal=0.0;
        double totNc=0.0;
        double lcsValue=0.0;
        double lcsVal=0.0;
        double totLcs=0.0;
        double tot=0.0;
        double total=0.0;


        Timestamp timestamp=null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){
        }

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        map.put("centerName", centerId);
        map.put("date", date + "");

        Center center = centerDAOService.getCenterById(centerId);
        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("name");
        model.addColumn("investment");
        model.addColumn("payment");
        model.addColumn("cash");
        model.addColumn("nc");


        // get individual list by centerId
        List<Individual> individualsByCenterId = individualDAOService.getIndividualsByCenterId(centerId);
        int individualCount=0;
        // each Individual total values calculating
        for (Individual individual:individualsByCenterId) {

            List<Chit> chitList = chitDAOService.getAllChithsByFormattedDateNIndividualId(date, individual.getIndividualId());
            if(!chitList.isEmpty()){
                for(Chit chit:chitList){
                    if(chit.getNC() != null && chit.getNcOLCValue() != null && chit.getIndividualId().equals(individual.getIndividualId()) && chit.getNC() == true && chit.getNcOLCValue().doubleValue() > 0){
                        ncVal=chit.getNcOLCValue().doubleValue();
                        totNc+=ncVal;
                    }
                    if(chit.getLCS() != null && chit.getNcOLCValue() != null && chit.getIndividualId().equals(individual.getIndividualId()) && chit.getLCS() == true && chit.getNcOLCValue().doubleValue() > 0){
                        lcsVal=chit.getNcOLCValue().doubleValue();
                        totLcs+=lcsVal;
                    }
                }
            }

            individualCount++;
            Account accountByIndividualId = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            List<Transaction> transactionListByAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByIndividualId.getAccountNo());
            if (!transactionListByAccountNo.isEmpty()) {
                for (Transaction transaction:transactionListByAccountNo) {
                    if(transaction != null){
                        if (transaction.getTypeId().equals("Inv")) {
                            totInvesment += transaction.getDebit().doubleValue();
                            investment = transaction.getDebit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("LN")){
                            loanDeductValue+=transaction.getDebit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("PAY")){
                            totPay+=transaction.getCredit().doubleValue();
                            payValue=transaction.getCredit().doubleValue();
                        }
                        if(transaction.getTypeId().equals("CSH")){
                            totCash+=transaction.getCredit().doubleValue();
                            cashValue=transaction.getCredit().doubleValue();
                        }
                    }
                }
                model.addRow(new Object[]{individualCount+"",  individual.getName()+ "",  investment+"", payValue+"",cashValue+"",ncVal+""});
                investment=0.0;
                payValue=0.0;
                cashValue=0.0;
                ncVal=0.0;
            }
        }


        Account accountByCenterId = accountDAOService.getAccountByCenterOIndividualId(centerId);
        List<Transaction> trList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, accountByCenterId.getAccountNo());
        Transaction transaction=null;
        boolean isExistCash=false;
        boolean isExistExcess=false;
        boolean isExistExpenses=false;
        boolean isExistLoanDeductionPayment=false;
        boolean isExistLoan=false;
        boolean isExistDue=false;
        if (!trList.isEmpty()) {
            for (Transaction tran:trList) {
                if(tran != null){
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess") && addExcess > 0){
                        tran.setCredit(BigDecimal.valueOf(addExcess));
                        transactionDAOService.update(tran);
                        excess+=addExcess;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP") && addExpenses > 0){
                        tran.setCredit(BigDecimal.valueOf(addExpenses));
                        transactionDAOService.update(tran);
                        expenses+=addExpenses;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        paymentDue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LN")){
                        loanDeductionPayment+=tran.getDebit().doubleValue();
                    }
                    if(tran.getTypeId().equals("LON")){
                        loan+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("PD")){
                        isExistDue=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("Excess")){
                        isExistExcess=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("EXP")){
                        isExistExpenses=true;
                    }
                    if(tran.getCredit() != null && tran.getTypeId().equals("COM")){
                        commision+=tran.getCredit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LN")){
                        isExistLoanDeductionPayment=true;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LON")){
                        isExistLoan=true;
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("NC")){
                        ncValue+=tran.getDebit().doubleValue();
                    }
                    if(tran.getDebit() != null && tran.getTypeId().equals("LCS")){
                        lcsValue+=tran.getDebit().doubleValue();
                    }
                }
            }
        }
        //if there is no current Excess value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExcess == false && addExcess > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setTypeId("Excess");
            transaction.setsTime(date);
            transaction.setCredit(BigDecimal.valueOf(addExcess));
            transactionDAOService.save(transaction);
            excess+=addExcess;
        }
        //if there is no current Expenses value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistExpenses == false && addExpenses > 0){
            transaction=new Transaction();
            String newId = idCreation();
            transaction.setTransactionId(newId);
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transaction.setTypeId("EXP");
            transaction.setCredit(BigDecimal.valueOf(addExpenses));
            transactionDAOService.save(transaction);
            expenses+=addExpenses;
        }
        ApprovedLoan approvedLoan=null;
        if(centerId != null){
            approvedLoan = approvedLoanDAOService.getOpenLoanDetailByCenterlId(centerId);
        }
        //if there is no current LoanDeductionPayment value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoanDeductionPayment == false){
            if (approvedLoan!=null&&approvedLoan.getDueamount() != null && approvedLoan.getDueamount().doubleValue() != 0) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LN");
                BigDecimal dueAmount=null;
                if(approvedLoan.getDueamount() != null && approvedLoan.getDeductionPayment() != null && loanDeduct == true){
                    dueAmount = approvedLoan.getDueamount().subtract(approvedLoan.getDeductionPayment());
                }

                if( loanDeduct == true){
                    transaction.setDebit(approvedLoan.getDeductionPayment());
                    transaction.setTime(timestamp);
                    transaction.setsTime(date);
                    transactionDAOService.save(transaction);
                    loanDeductionPayment+=approvedLoan.getDeductionPayment().doubleValue();
                }
                if(approvedLoan.getDueamount() != null && dueAmount != null){
                    approvedLoan.setDueamount(dueAmount);
                }
                approvedLoanDAOService.update(approvedLoan);
            }
        }
        //if there is no current ExistLoan value for specified date for center adding new value to transaction table and to genaral summary report
        if(isExistLoan == false){
            if (approvedLoan!=null&&approvedLoan.getsTime().equals(date)) {
                String id = new Date().getTime() + "";
                Hashids hashids = new Hashids(id);
                String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
                transaction = new Transaction();
                createTransactinonAccount(centerId, transaction);
                String newid1 = getStringID(id, hashids, hexaid);
                transaction.setTransactionId(newid1);
                transaction.setTypeId("LON");
                transaction.setCredit(approvedLoan.getDueamount());
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                loan+=approvedLoan.getDueamount().doubleValue();
            }
        }

        if(isExistDue == false){
            if(accountByCenterId != null && accountByCenterId.getAmount() != null && accountByCenterId.getAmount().doubleValue() > 0.0){
                transaction = new Transaction();
                transaction.setTransactionId(getNewId());
                transaction.setAccountNo(accountByCenterId.getAccountNo());
                transaction.setDebit(accountByCenterId.getAmount());
                transaction.setTypeId("PD");
                transaction.setTime(timestamp);
                transaction.setsTime(date);
                transactionDAOService.save(transaction);
                paymentDue+=accountByCenterId.getAmount().doubleValue();
            }
        }

        if(center.getPcChargers() != null){
            pcCharges=center.getPcChargers().doubleValue();
        }
        tot=ncValue+lcsValue+pcCharges+loanDeductionPayment+excess+expenses;

        total=commision-tot;

        map.put("totInv",totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPay == null ? "--" : totPay + "");
        map.put("totCash", totCash == null ? "--" : totCash + "");
        map.put("totNc", totNc+"");
        map.put("totLcs", totLcs+"");
        map.put("nc",ncValue+"");
        map.put("lcs", lcsValue + "");
        map.put("com",commision+"");
        map.put("csh",totCash+"");
        map.put("pc",pcCharges+"");
        map.put("exces",excess+"");
        map.put("exp",expenses+"");
        map.put("due",paymentDue+"");
        map.put("ln",loanDeductionPayment+"");
        map.put("lon",loan+"");
        map.put("ld",loanDeductValue+"");
        map.put("tot",tot+"");
        map.put("total",total+"");

        tpyInvestment+=totInvesment;
        tpyPayment+=totPayment;
        tpyInvestment+=paymentDue;
        tpyPayment+=commision;
        tpyInvestment+=ncValue;
        tpyInvestment+=loanDeductionPayment;
        tpyInvestment+=loanDeductValue;
        tpyInvestment += lcsValue;
        tpyInvestment+=pcCharges;
        tpyPayment+=excess;
        tpyPayment+=expenses;
        tpyPayment+=loan;
        tpyPayment+=totCash;
        tpyPayment+=totPay;


        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");

        Double dueAmount = tpyInvestment - tpyPayment;


        if(dueAmount > 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setDebit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);
            accountByCenterId.setAmount(BigDecimal.valueOf(dueAmount));
            accountDAOService.update(accountByCenterId);

            map.put("dueTot", dueAmount == null ? "--" : dueAmount + "");
            map.put("dueLable", "Due");
            map.put("paymentLable", "");
            map.put("payment", "");
            map.put("tpyInvDeduct", tpyPayment == null ? "--" : tpyPayment+"");
            map.put("tpyPayDeduct", "");
        }

        if(dueAmount < 0.0){
            transaction = new Transaction();
            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount));
            transaction.setTypeId("Balance");
            transaction.setTime(timestamp);
            transaction.setsTime(date);
            transactionDAOService.save(transaction);

            transaction.setTransactionId(getNewId());
            transaction.setAccountNo(accountByCenterId.getAccountNo());
            transaction.setCredit(BigDecimal.valueOf(dueAmount*-1));
            transaction.setTypeId("Payment");
            transaction.setsTime(date);
            transaction.setTime(timestamp);
            transactionDAOService.save(transaction);

            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("payment", dueAmount == null ? "--" : dueAmount*-1 + "");
            map.put("dueLable", "");
            map.put("dueTot", "");
            map.put("paymentLable", "Payment");
            map.put("tpyPayDeduct", tpyInvestment == null ? "--" : tpyInvestment+"");
            map.put("tpyInvDeduct", "");
        }
        if(dueAmount == 0.0){
            accountByCenterId.setAmount(BigDecimal.ZERO);
            accountDAOService.update(accountByCenterId);

            map.put("dueTot","");
            map.put("paymentLable", "Balance");
            map.put("payment", "0.0");
            map.put("tpyInvDeduct", "");
            map.put("tpyPayDeduct", "");
            map.put("dueLable", "");
        }

        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryWithCash2.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
            // JasperViewer.viewReport(jp, false);
            File pdf = File.createTempFile("output.", ".pdf");
            JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
            try {
                InputStream inputStream = new FileInputStream(pdf);
                response.setContentType("application/pdf");

                response.setHeader("Content-Disposition", "attachment; filename=" + centerId + ".pdf");

                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    /**
     * * getGeneralSummaryReceipt
     * <p/>
     * 0== Center
     * 1== individual
     *
     * @param session
     * @param response
     * @param centerId
     * @param individualId
     * @param date
     */
    @RequestMapping(value = "getGeneralSummaryReceipt", method = RequestMethod.GET)
    public void getGeneralSummaryReceipt(HttpSession session, HttpServletResponse response,
                                         @RequestParam("centerId") String centerId,
                                         @RequestParam("individualId") String individualId,
                                         @RequestParam("datetime") String date,
                                         @RequestParam("type") Integer type
    ) {


        double inValue = 0;
        double outValue = 0;
        Double totInvesment = inValue;
        double ncValue=0.0;
        double lcsValue=0.0;
        Double totPayment = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;
        Double salary=0.0;
        Double overPayment=0.0;
        Double excess=0.0;
        Double expenses=0.0;
        Double pcCharges=0.0;
        Double loanDeductionPayment=0.0;
        Double paymentDue=0.0;
        Double cash=0.0;
        Double commision=0.0;
        Double loan=0.0;
        Double rent=0.0;

        Timestamp timestamp=null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){
        }

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        map.put("date", date + "");

        Transaction tra = new Transaction();
        Account account = accountDAOService.getAccountByCenterOIndividualId(individualId);
        Individual individual = individualDAOService.getBranchById(individualId);

        map.put("pc", "--");
        map.put("ln", "--");
        map.put("pd", "--");
        map.put("com", "--");
        map.put("nc","--");
        map.put("lcs","--");
        map.put("lon", "--");
        map.put("sal", "--");
        map.put("overPay", "--");
        map.put("exces", "--");
        map.put("exp", "--");
        map.put("rent", "--");

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String formatedDate = simpleDateFormat.format(new Date(date));

        List<Transaction> transactionList = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(date, account.getAccountNo());
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

                String ty=tra.getTypeId().toLowerCase();
                if(tra.getDebit()!= null){
                    Object put = map.put(ty, tra.getDebit() + "");
                }
                if(tra.getCredit()!= null){
                    Object put = map.put(ty, tra.getCredit() + "");
                }
                if (tra.getTypeId().equals("Inv")) {
                    totInvesment = tra.getDebit().doubleValue();
                }
                if(tra.getDebit() != null && tra.getTypeId().equals("NC")){
                    ncValue+=tra.getDebit().doubleValue();
                }
                if(tra.getDebit() != null && tra.getTypeId().equals("LCS")){
                    lcsValue+=tra.getDebit().doubleValue();
                }

                if(tra.getTypeId().equals("Salary")){
                    salary+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("OverPayment")){
                    overPayment+=tra.getDebit().doubleValue();
                }
                if(tra.getTypeId().equals("RENT")){
                    rent+=tra.getDebit().doubleValue();
                }
                if(tra.getTypeId().equals("Excess")){
                    excess+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("EXP")){
                    expenses+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("PC")){
                    pcCharges+=tra.getDebit().doubleValue();
                }
                if(tra.getTypeId().equals("LN")){
                    loanDeductionPayment+=tra.getDebit().doubleValue();
                }
                if(tra.getDebit() != null && tra.getTypeId().equals("PD")){
                    paymentDue+=tra.getDebit().doubleValue();
                }
                if(tra.getTypeId().equals("PAY")){
                    totPayment+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("CSH")){
                    cash+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("COM")){
                    commision+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("LON")){
                    loan+=tra.getCredit().doubleValue();
                }

                if(tra.getCredit() != null && tra.getTypeId().equals("Balance")){
                    tpyPayment+=tra.getCredit().doubleValue();
                }
                if(tra.getTypeId().equals("Payment")){
                    tpyPayment-=tra.getCredit().doubleValue();
                }
            }
        }

        List<Envelope> envelopesByCenterId = null;
        if (type == 0) {
            map.put("Individual", centerId == null ? "--" : centerId);
            envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date);
        } else if (type == 1 && individual != null) {
            map.put("Individual", individual.getName());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(individualId, null, null, date);
        }

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("Inv");
        model.addColumn("Pay");
        model.addColumn("ncOfTable");



        for (Envelope envelope : envelopesByCenterId) {
            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());

            for (Chit chit : chits) {
                String ncOLCS=null;
                if(chit.getNC() != null && chit.getNC() == true){
                    ncOLCS="NC";
                }else if(chit.getLCS() != null && chit.getLCS() == true){
                    ncOLCS="LCS";
                }else if(chit.getNC() == null && chit.getLCS() == null){
                    ncOLCS="";
                }else if(chit.getNC() == false && chit.getLCS() == false){
                    ncOLCS="";
                }
                model.addRow(new Object[]{chit.getNumber(), chit.getInvesment() == null ? "--" : chit.getInvesment() + "", chit.getAmount() == null ? "--" : chit.getAmount() + "", ncOLCS});
            }
        }

        Double perDue=0.0;

        tpyPayment += totPayment;
        tpyInvestment+=totInvesment;
        if(perDue > 0.0){
            tpyInvestment+=perDue;
        }
        if(salary > 0.0){
            map.put("sal",salary+"");
            tpyPayment+=salary;
        }

        if(cash > 0.0){
            map.put("csh",cash+"");
            tpyPayment+=cash;
        }
        if(commision > 0.0){
            map.put("com",commision+"");
            tpyPayment+=commision;
        }
        if(ncValue > 0.0){
            map.put("nc",ncValue+"");
            tpyInvestment+=ncValue;
        }
        if(loanDeductionPayment > 0.0){
            map.put("ln",loanDeductionPayment+"");
            tpyInvestment+=loanDeductionPayment;
        }
        if(lcsValue > 0.0) {
            map.put("lcs", lcsValue + "");
            tpyInvestment += lcsValue;
        }
        if(paymentDue > 0.0){
            map.put("pd",paymentDue+"");
            tpyInvestment+=paymentDue;
        }
        if(overPayment > 0.0){
            map.put("overPay",overPayment+"");
            tpyInvestment+=overPayment;
        }
        if(rent > 0.0){
            map.put("rent",rent+"");
            tpyInvestment+=rent;
        }
        if(pcCharges > 0.0){
            map.put("pc",pcCharges+"");
            tpyInvestment+=pcCharges;
        }
        if(excess > 0.0){
            map.put("exces",excess+"");
            tpyPayment+=excess;
        }
        if(expenses > 0.0){
            map.put("exp",expenses+"");
            tpyPayment+=expenses;
        }
        if(loan > 0.0){
            map.put("lon",loan+"");
            tpyPayment+=loan;
        }


        map.put("totInv", totInvesment == null ? "--" : totInvesment + "");
        map.put("totPay", totPayment == null ? "--" : totPayment + "");

        map.put("tpyPay", tpyPayment == null ? "--" : tpyPayment + "");
        map.put("tpyInv", tpyInvestment == null ? "--" : tpyInvestment + "");

        Double dueAmount = tpyInvestment - tpyPayment;

        if(dueAmount > 0.0){
            map.put("due", dueAmount == null ? "--" : dueAmount + "");
            map.put("dueLable", "Due");
            map.put("paymentLable", "");
            map.put("payment", "");
            map.put("tpyInvDeduct", tpyPayment == null ? "--" : tpyPayment+"");
            map.put("tpyPayDeduct", "");
        }

        if(dueAmount < 0.0){
            map.put("payment", dueAmount == null ? "--" : dueAmount*-1 + "");
            map.put("dueLable", "");
            map.put("due", "");
            map.put("paymentLable", "Payment");
            map.put("tpyPayDeduct", tpyInvestment == null ? "--" : tpyInvestment+"");
            map.put("tpyInvDeduct", "");
        }
        if(dueAmount == 0.0){
            map.put("due","");
            map.put("paymentLable", "Balance");
            map.put("payment", "0.0");
            map.put("tpyInvDeduct", "");
            map.put("tpyPayDeduct", "");
            map.put("dueLable", "");
        }

        List<ApprovedLoan> approvedLoanList = approvedLoanDAOService.getUnpaidLoansByIndividualId(individualId);
        BigDecimal approveLoanDueAmount =BigDecimal.ZERO;
        for (ApprovedLoan approvedLoan:approvedLoanList){
            if(approvedLoan != null && approvedLoan.getDueamount()!=null ){
                approveLoanDueAmount=approveLoanDueAmount.add(approvedLoan.getDueamount());
            }
        }
        map.put("loanDue", approveLoanDueAmount == null ? "--" : approveLoanDueAmount + "");

        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryOfIndividual5.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
            // JasperViewer.viewReport(jp, false);
            File pdf = File.createTempFile("output.", ".pdf");
            JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
            try {
                InputStream inputStream = new FileInputStream(pdf);
                response.setContentType("application/pdf");
                SimpleDateFormat sDF = new SimpleDateFormat("dd-MM-yyyy");
                response.setHeader("Content-Disposition", "attachment; filename=" + individual.getName() +" "+  date + ".pdf");
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println(ex);
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

    private String idCreation(){
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }

    /**
     *
     * @param individualId
     * @param transaction
     */
    private void createTransactinonAccount(String individualId, Transaction transaction) {
        String accountNo;
        if (null != individualId && transaction != null) {
            Account individualAccount = accountDAOService.getAccountByCenterOIndividualId(individualId);
            accountNo = individualAccount.getAccountNo();
            transaction.setAccountNo(accountNo);

        }
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
     * genarating new id
     * @return
     */
    private String getNewId() {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        return newid;
    }
}
