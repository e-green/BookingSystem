package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.*;
import org.egreen.opensms.server.models.GeneralSummaryReportModel;
import org.egreen.opensms.server.models.IndividualReportModel;
import org.egreen.opensms.server.models.ReportChitModel;
import org.egreen.opensms.server.service.*;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
@RequestMapping("booking/v1/report")
public class ReportController {


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

    @Autowired
    private CenterSummeryCheckService centerSummeryCheckService;


    /**
     * get general summary receipt model
     *
     * @param session
     * @param response
     * @param centerId
     * @param individualId
     * @param date
     * @param type
     * @return
     */
    @RequestMapping(value = "getGeneralSummaryReceiptModel", method = RequestMethod.GET)
    public IndividualReportModel getGeneralSummaryReceipt(HttpSession session, HttpServletResponse response,
                                                          @RequestParam("centerId") String centerId,
                                                          @RequestParam("individualId") String individualId,
                                                          @RequestParam("sTime") String date,
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

        IndividualReportModel individualReportModel=new IndividualReportModel();


        Transaction tra = new Transaction();
        //get individual account by individualId
        Account account = accountDAOService.getAccountByCenterOIndividualId(individualId);
        //get individual by individualId
        Individual individual = individualDAOService.getBranchById(individualId);

        //inisializing empty values to individualReportModel attributes
        individualReportModel.setsTime(date);
        individualReportModel.setIndividual("");
        individualReportModel.setPc(BigDecimal.ZERO);
        individualReportModel.setLn(BigDecimal.ZERO);
        individualReportModel.setPd(BigDecimal.ZERO);
        individualReportModel.setCom(BigDecimal.ZERO);
        individualReportModel.setNc(BigDecimal.ZERO);
        individualReportModel.setLcs(BigDecimal.ZERO);
        individualReportModel.setLon(BigDecimal.ZERO);
        individualReportModel.setSal(BigDecimal.ZERO);
        individualReportModel.setOverPay(BigDecimal.ZERO);
        individualReportModel.setExces(BigDecimal.ZERO);
        individualReportModel.setExp(BigDecimal.ZERO);
        individualReportModel.setRent(BigDecimal.ZERO);
        individualReportModel.setCsh(BigDecimal.ZERO);
        individualReportModel.setDue(BigDecimal.ZERO);
        individualReportModel.setDueLable("");
        individualReportModel.setLoanDue(BigDecimal.ZERO);
        individualReportModel.setPaymentLable("");
        individualReportModel.setPayment(BigDecimal.ZERO);
        individualReportModel.setTotInv(BigDecimal.ZERO);
        individualReportModel.setTotPay(BigDecimal.ZERO);
        individualReportModel.setTpyInv(BigDecimal.ZERO);
        individualReportModel.setTpyPay(BigDecimal.ZERO);
        individualReportModel.setTpyInvDeduct(BigDecimal.ZERO);
        individualReportModel.setTpyPayDeduct(BigDecimal.ZERO);

        // get individual transaction list by string date(sTime) and individual accountId
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
            individualReportModel.setIndividual(centerId);
            envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date);
        } else if (type == 1 && individual != null) {
            individualReportModel.setIndividual(individual.getName());
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(individualId, null, null, date);
        }

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        List<ReportChitModel> reportChitModels= new ArrayList<ReportChitModel>();
        ReportChitModel reportChitModel=null;

        model.addColumn("no");
        model.addColumn("wT");
        model.addColumn("Inv");
        model.addColumn("Pay");
        model.addColumn("ncOfTable");


        for (Envelope envelope : envelopesByCenterId) {
            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());

            for (Chit chit : chits) {
                String ncOLCS=null;
                String wT=null;
                if(chit.getNC() != null && chit.getNC() == true){
                    ncOLCS="NC";
                }else if(chit.getLCS() != null && chit.getLCS() == true){
                    ncOLCS="LCS";
                }else if(chit.getNC() == null && chit.getLCS() == null){
                    ncOLCS="";
                }else if(chit.getNC() == false && chit.getLCS() == false){
                    ncOLCS="";
                }
                if(chit.getwT() != null && chit.getwT() == true){
                    wT="WT";
                }else if(chit.getwT() == false){
                    wT="";
                }
                reportChitModel=new ReportChitModel();
                reportChitModel.setChitNumber(chit.getNumber());
                reportChitModel.setwT(wT);
                if(chit.getInvesment() != null){
                    reportChitModel.setInvesment(chit.getInvesment());
                }
                if(chit.getAmount() != null){
                    reportChitModel.setAmount(chit.getAmount());
                }
                reportChitModel.setNcOLCS(ncOLCS);
                reportChitModels.add(reportChitModel);
            }
        }

        Double perDue=0.0;

        tpyPayment += totPayment;
        tpyInvestment+=totInvesment;
        if(perDue > 0.0){
            tpyInvestment+=perDue;
        }
        if(salary > 0.0){
            individualReportModel.setSal(BigDecimal.valueOf(salary));
            tpyPayment+=salary;
        }
        if(cash > 0.0){
            individualReportModel.setCsh(BigDecimal.valueOf(cash));
            tpyPayment+=cash;
        }
        if(commision > 0.0){
            individualReportModel.setCom(BigDecimal.valueOf(commision));
            tpyPayment+=commision;
        }
        if(ncValue > 0.0){
            individualReportModel.setNc(BigDecimal.valueOf(ncValue));
            tpyInvestment+=ncValue;
        }
        if(loanDeductionPayment > 0.0){
            individualReportModel.setLn(BigDecimal.valueOf(loanDeductionPayment));
            tpyInvestment+=loanDeductionPayment;
        }
        if(lcsValue > 0.0) {
            individualReportModel.setLcs(BigDecimal.valueOf(lcsValue));
            tpyInvestment += lcsValue;
        }
        if(paymentDue > 0.0){
            individualReportModel.setPd(BigDecimal.valueOf(paymentDue));
            tpyInvestment+=paymentDue;
        }
        if(overPayment > 0.0){
            individualReportModel.setOverPay(BigDecimal.valueOf(overPayment));
            tpyInvestment+=overPayment;
        }
        if(rent > 0.0){
            individualReportModel.setRent(BigDecimal.valueOf(rent));
            tpyInvestment+=rent;
        }
        if(pcCharges > 0.0){
            individualReportModel.setPc(BigDecimal.valueOf(pcCharges));
            tpyInvestment+=pcCharges;
        }
        if(excess > 0.0){
            individualReportModel.setExces(BigDecimal.valueOf(excess));
            tpyPayment+=excess;
        }
        if(expenses > 0.0){
            individualReportModel.setExp(BigDecimal.valueOf(expenses));
            tpyPayment+=expenses;
        }
        if(loan > 0.0){
            individualReportModel.setLon(BigDecimal.valueOf(loan));
            tpyPayment+=loan;
        }


        if(totInvesment != null){
            individualReportModel.setTotInv(BigDecimal.valueOf(totInvesment));
        }
        if(totPayment != null){
            individualReportModel.setTotPay(BigDecimal.valueOf(totPayment));
        }
        if(tpyPayment != null){
            individualReportModel.setTpyPay(BigDecimal.valueOf(tpyPayment));
        }
        if(tpyInvestment != null){
            individualReportModel.setTpyInv(BigDecimal.valueOf(tpyInvestment));
        }

        Double dueAmount = tpyInvestment - tpyPayment;

        if(dueAmount > 0.0){
            if(dueAmount != null){
                individualReportModel.setDue(BigDecimal.valueOf(dueAmount));
            }
            individualReportModel.setDueLable("Due");
            if(tpyPayment != null){
                individualReportModel.setTpyInvDeduct(BigDecimal.valueOf(tpyPayment));
            }
        }
        if(dueAmount < 0.0){
            if(dueAmount != null){
                individualReportModel.setPayment(BigDecimal.valueOf(dueAmount*-1));
            }
            individualReportModel.setPaymentLable("Payment");
            if(tpyInvestment != null){
                individualReportModel.setTpyPayDeduct(BigDecimal.valueOf(tpyInvestment));
            }
        }
        if(dueAmount == 0.0){
            individualReportModel.setPaymentLable("Balance");
            individualReportModel.setPayment(BigDecimal.ZERO);
        }

        List<ApprovedLoan> approvedLoanList = approvedLoanDAOService.getUnpaidLoansByIndividualId(individualId);
        BigDecimal approveLoanDueAmount =BigDecimal.ZERO;
        for (ApprovedLoan approvedLoan:approvedLoanList){
            if(approvedLoan != null && approvedLoan.getDueamount()!=null ){
                approveLoanDueAmount=approveLoanDueAmount.add(approvedLoan.getDueamount());
            }
        }
        individualReportModel.setLoanDue(approveLoanDueAmount);

        return  individualReportModel;
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
