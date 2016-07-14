package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.models.InOutModel;
import org.egreen.opensms.server.models.SpecifyDateInOutModel;
import org.egreen.opensms.server.service.*;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pramoda on 6/21/16.
 */

@Controller
@RequestMapping("booking/v1/subreport")
public class SubReportController {

    @Autowired
    private AccountDAOService accountDAOService;

    @Autowired
    private TransactionDAOService transactionDAOService;


    @Autowired
    private EnvelopeDAOService envelopeDAOService;

    @Autowired
    private IndividualDAOService individualDAOService;

    @Autowired
    private ChitDAOService  chitDAOService;


    @RequestMapping(value = "getIncomeAndOutComeReport", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public  List<InOutModel> getIncomeAndOutComeReport(@RequestParam("centerId") String centerId,
                                                       @RequestParam("firstDate") String firstDate,
                                                       @RequestParam("secondDate") String secondDate) {


        List<InOutModel> inOutModelList =  new ArrayList<InOutModel>();

        List<Individual> individualsByCenterId = individualDAOService.getIndividualsByCenterId(centerId);
        for (Individual individual : individualsByCenterId) {

            InOutModel inOutModel =  new InOutModel();
            inOutModel.setIndividualName(individual.getName());



            List<Envelope> envelopeList = envelopeDAOService.getEnvelopeByDateRange(centerId,individual.getIndividualId(),firstDate,secondDate);
            Double totalChitCount = 0.0;
            Integer winTotalChitCount = 0;
            for (Envelope envelope : envelopeList) {

                totalChitCount += envelope.getChitCount().doubleValue();

                winTotalChitCount += chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId()).size();

            }

            inOutModel.setWinChitCount(winTotalChitCount);
            inOutModel.setTotalChitCount(totalChitCount);

            Account account = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());

            if (account != null) {
                Double totalInvesment = 0.0;
                Double totalPayment = 0.0;
                Double totalCash = 0.0;
                List<Transaction> transaActionByDateRange = transactionDAOService.getTransaActionByDateRange(account.getAccountNo(), firstDate, secondDate);
                for (Transaction transaction : transaActionByDateRange) {

                    if (transaction.getTypeId().equals("Inv")) {

                        totalInvesment += transaction.getDebit().doubleValue();
                    }

                    if (transaction.getTypeId().equals("PAY")) {
                        totalPayment += transaction.getCredit().doubleValue();
                    }

                    if (transaction.getTypeId().equals("CSH")) {
                        totalCash += transaction.getCredit().doubleValue();
                    }
                }
                inOutModel.setTotalInvesment(totalInvesment);
                inOutModel.setTotalPayment(totalPayment);
                inOutModel.setTotalCash(totalCash);
            }
            inOutModelList.add(inOutModel);
        }

        return  inOutModelList;

    }

    /**
     *
     *
     * getIncomeAndOutComeJasperReport
     *
     * @param centerId
     * @param firstDate
     * @param secondDate
     * @return
     */
    @RequestMapping(value = "getIncomeAndOutComeJasperReport", method = RequestMethod.GET, headers = "Accept=application/json")
    public void getIncomeAndOutComeJasperReport(HttpSession session, HttpServletResponse response, @RequestParam("centerId") String centerId,
                                                 @RequestParam("firstDate") String firstDate,
                                                 @RequestParam("secondDate") String secondDate) {

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("individual");
        model.addColumn("investment");
        model.addColumn("pay");
        model.addColumn("chitCount");
        model.addColumn("cash");
        model.addColumn("winChits");

        Double invesmentTotal= 0.0;
        Double payTotal= 0.0;
        Double cashTotal= 0.0;

        List<InOutModel> incomeAndOutComeReport = getIncomeAndOutComeReport(centerId, firstDate, secondDate);
        for (InOutModel inOutModel : incomeAndOutComeReport) {
            model.addRow(new Object[]{inOutModel.getIndividualName() + "", inOutModel.getTotalInvesment() + "", inOutModel.getTotalPayment() + "",
                    inOutModel.getTotalChitCount() + "",inOutModel.getTotalCash() + "",inOutModel.getWinChitCount() + ""});

            invesmentTotal += inOutModel.getTotalInvesment().doubleValue();
            payTotal += inOutModel.getTotalPayment().doubleValue();
            cashTotal += inOutModel.getTotalCash().doubleValue();
        }

        map.put("invTot", invesmentTotal + "");
        map.put("payTot", payTotal + "");
        map.put("totCash", cashTotal + "");


        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("IncomeOutcome.jrxml");
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






}
