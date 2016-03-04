package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
     * getGeneralSummaryReceipt
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

        Timestamp timestamp = new Timestamp(date);
        Date date1 = new Date(timestamp.getTime());

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("date", date + "");




        Transaction tra = new Transaction();
        Account account = accountDAOService.getAccountByCenterOIndividualId(individualId);
        Individual individual = individualDAOService.getBranchById(individualId);


        map.put("pc", "");
        map.put("ln", "--");
        map.put("pd", "--");
        map.put("com", "--");
        map.put("nc","--");
        map.put("lcs","--");
        map.put("lon", "--");
        map.put("sal", "--");
        map.put("overPay", "--");
        map.put("exces", "--");



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM--dd");
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
            }
        }

//        map.put("csh",234234234+"");
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


        Double totPayment = 0.0;
        Double tpyPayment = outValue;
        Double tpyInvestment = inValue;


        for (Envelope envelope : envelopesByCenterId) {

            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());
            Double payment = null;
            double ncValue=0.0;
            double lcsValue=0.0;
            BigDecimal notCommisionValue=BigDecimal.ZERO;
            BigDecimal notcommisionPersentageForCenter=BigDecimal.ZERO;
            BigDecimal lessCommisionSinglePersentageForIndividual=BigDecimal.ZERO;
            BigDecimal notcommisionPersentageForIndividual=BigDecimal.ZERO;
            BigDecimal lessCommisionSingleValue=BigDecimal.ZERO;
            BigDecimal lessCommisionSinglePersentageForCenter=BigDecimal.ZERO;
            BigDecimal notCommisionsTot =BigDecimal.ZERO;
            BigDecimal lessCommisionSingleTot =BigDecimal.ZERO;
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
                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }
                if(chit != null && chit.getNC()!=null && chit.getNC() == true  && chit.getNcOLCValue()!= null && chit.getNcOLCValue().doubleValue() > 0){
                    ncValue+=Double.parseDouble(chit.getNcOLCValue()+"");
                }
                if(chit != null && chit.getLCS()!=null && chit.getLCS() == true  && chit.getNcOLCValue()!= null && chit.getNcOLCValue().doubleValue() > 0){
//                    System.out.println("LCS ->"+chit.getNcOLCValue());
                    lcsValue+=Double.parseDouble(chit.getNcOLCValue()+"");
                }
            }
            notCommisionValue= BigDecimal.valueOf(ncValue);
            lessCommisionSingleValue= BigDecimal.valueOf(lcsValue);
//            System.out.println("lessCommisionSingleValue"+lessCommisionSingleValue);
            //this must be change after center persentage addded
            Center center = centerDAOService.getCenterById(centerId);
            Individual individual1 = individualDAOService.getBranchById(individualId);

            if(center != null && center.getNotCommisionPersentage() != null){
                notcommisionPersentageForCenter=center.getNotCommisionPersentage();
                notCommisionsTot=envelopeDAOService.calculateNotCommision(notCommisionValue, notcommisionPersentageForCenter);
            }
            if(center != null && center.getLessComissionSingle() != null){
                lessCommisionSinglePersentageForCenter=center.getLessComissionSingle();
                System.out.println("lessCommisionSingleValue for center"+lessCommisionSingleValue);
                lessCommisionSingleTot=envelopeDAOService.calculateLessCommisionSingle(lessCommisionSingleValue, lessCommisionSinglePersentageForCenter);
                System.out.println("lessCommisionSingleTot for center"+lessCommisionSingleTot);
            }
            if(individual1 != null && individual1.getNotCommisionPersentage() != null){
                notcommisionPersentageForIndividual=individual1.getNotCommisionPersentage();
                notCommisionsTot=envelopeDAOService.calculateNotCommision(notCommisionValue, notcommisionPersentageForIndividual);
            }
            if(individual1 != null && individual1.getLessComissionSingle() != null){
                lessCommisionSinglePersentageForIndividual=individual1.getLessComissionSingle();
//                System.out.println("lessCommisionSingleValue for individual "+lessCommisionSinglePersentageForIndividual);
                lessCommisionSingleTot=envelopeDAOService.calculateLessCommisionSingle(lessCommisionSingleValue, lessCommisionSinglePersentageForIndividual);
//                System.out.println("lessCommisionSingleTot for individual "+lessCommisionSingleTot);
                map.put("lcs",lessCommisionSingleTot.doubleValue()+"");
            }
            if(notCommisionsTot != null && notCommisionsTot.doubleValue() >0.0){
                map.put("nc",notCommisionsTot+"");
            }
            if (individual1!=null&&individual1.getCommision() == null && individual1.getFixedSalary() == null && individual1.getCommision() == null) {
                if (individual1.isSalaryPay() == false  ) {
                    BigDecimal salary = envelopeDAOService.calculateSalary(BigDecimal.valueOf(totInvesment));
                    map.put("sal",salary+"");

                }
            }
//            System.out.println(individual1);
//            System.out.println(individual1.getFixedSalary());


            if (individual1!=null&&individual1.getCommision() == null && individual1.getFixedSalary() != null && individual1.getCommision() == null) {

                if (individual1.isSalaryPay() && individual1.getFixedSalary().doubleValue() > 0.0 ) {

                    BigDecimal salary = individual1.getFixedSalary();
                    System.out.println("inside the condition" + individual1.getFixedSalary());
                    map.put("sal", salary+"");

                }
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
//        ApprovedLoan approvedLoan = approvedLoanDAOService.getOpenLoanDetailByIndividualId(individualId);
        List<ApprovedLoan> approvedLoanList = approvedLoanDAOService.getUnpaidLoansByIndividualId(individualId);
        BigDecimal approveLoanDueAmount =BigDecimal.ZERO;
        for (ApprovedLoan approvedLoan:approvedLoanList){
//            System.out.println(approvedLoan);
//            System.out.println(approvedLoan.getDueamount());
            if(approvedLoan != null && approvedLoan.getDueamount()!=null ){
                approveLoanDueAmount=approveLoanDueAmount.add(approvedLoan.getDueamount());
                //map.put("ln", approvedLoan.getDeductionPayment()+"");
            }
        }
        map.put("loanDue", approveLoanDueAmount == null ? "--" : approveLoanDueAmount + "");

        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryOfIndividual1.jrxml");
            JasperReport jr = JasperCompileManager.compileReport(systemResourceAsStream);
            System.out.println(map);
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
}
