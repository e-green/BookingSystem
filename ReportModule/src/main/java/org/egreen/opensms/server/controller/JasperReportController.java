package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.ReportModel;
import org.egreen.opensms.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * getGeneralSummaryReport
     *
     * @param session
     * @param response
     * @param centerId
     */
    @RequestMapping(value = "getGeneralSummaryReport", method = RequestMethod.GET)
    public void getCustomerReport(HttpSession session, HttpServletResponse response,
                                  @RequestParam("centerId") String centerId,
                                  @RequestParam("datetime") Long date
    ) {


        Timestamp timestamp = new Timestamp(date);
        Date date1 = new Date(timestamp.getTime());

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("centerName", centerId);
        map.put("date", date + "");


        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date1);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("name");
        model.addColumn("investment");
        model.addColumn("payment");
        model.addColumn("remanks");


        for (Envelope envelope : envelopesByCenterId) {

            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());
            Double payment = null;
            for (Chit chit : chits) {
                if (chit.getAmount() != null) {
                    payment = chit.getAmount().doubleValue();
                }
            }
            model.addRow(new Object[]{envelope.getEnvelopId(), envelope.getName(), envelope.getInvesment() + "", payment + "", ""});
        }


        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GeneralSummary.jrxml");
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
                                         @RequestParam("datetime") Long date,
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

        Timestamp timestamp = new Timestamp(date);
        Date date1 = new Date(timestamp.getTime());
        SimpleDateFormat ssd=new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = ssd.format(date1);

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("date", formatDate + "");

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



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(new Date(date));

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
            envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date1);
        } else if (type == 1 && individual != null) {
            String fD = simpleDateFormat.format(date1);
            map.put("Individual", individual.getName());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(individualId, null, null, fD);
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
//        if(totPayment > 0.0){
//            map.put("totPay",totPayment+"");
//            tpyPayment+=totPayment;
//        }
//        if(totInvesment > 0.0){
//            map.put("totInv",totInvesment+"");
//            tpyInvestment+=totInvesment;
//        }
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
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryOfIndividual3.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, ds);
            // JasperViewer.viewReport(jp, false);
            File pdf = File.createTempFile("output.", ".pdf");
            JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));
            try {
                InputStream inputStream = new FileInputStream(pdf);
                response.setContentType("application/pdf");
                SimpleDateFormat sDF = new SimpleDateFormat("dd-MM-yyyy");
                String fDate = simpleDateFormat.format(new Date(date));
                response.setHeader("Content-Disposition", "attachment; filename=" + individual.getName() +" "+  fDate + ".pdf");
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
}
