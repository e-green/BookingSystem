package org.egreen.opensms.server.controller;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.commons.io.IOUtils;
import org.egreen.opensms.server.entity.Chit;
import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.service.ChitDAOService;
import org.egreen.opensms.server.service.EnvelopeDAOService;
import org.egreen.opensms.server.service.IndividualDAOService;
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
import java.sql.Timestamp;
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
        Date date1 =  new Date(timestamp.getTime());

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("centerName", centerId);
        map.put("date", date+"");


        List<Envelope> envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId,null,null,date1);

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
     *
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


        Timestamp timestamp = new Timestamp(date);
        Date date1 =  new Date(timestamp.getTime());

        JRTableModelDataSource ds = null;
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();


        map.put("date", date+"");


        map.put("nc", "--");
        map.put("pc", "--");
        map.put("ln", "--");
        map.put("pd", "--");
        map.put("com","--");
        map.put("exp","--");
        map.put("lon","--");
        map.put("csh","--");

        List<Envelope> envelopesByCenterId = null;

        if (type==0) {
            map.put("Individual", centerId==null ? "--" :centerId);
           envelopesByCenterId = envelopeDAOService.getEnvelopesByCenterId(centerId, null, null, date1);
        }else if(type==1){
            map.put("Individual", individualId==null ? "--" :individualId);
            envelopesByCenterId = envelopeDAOService.getEnvelopesByIndividualIdByDate(individualId, null, null, date1);
        }

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("no");
        model.addColumn("Inv");
        model.addColumn("Pay");



        Double totPayment = 0.0;
        Double totInvesment = 0.0;
        for (Envelope envelope : envelopesByCenterId) {

            List<Chit> chits = chitDAOService.getAllChitsByEnvelopeId(envelope.getEnvelopId());
            Double payment = null;

            for (Chit chit : chits) {


                model.addRow(new Object[]{chit.getNumber(), chit.getInvesment()==null ? "--" :chit.getInvesment()+"", chit.getAmount() ==null ? "--" :chit.getAmount()+""});

                if (chit.getAmount() != null) {
                    totPayment += chit.getAmount().doubleValue();
                }

                if (chit.getInvesment() != null) {
                    totInvesment += chit.getInvesment().doubleValue();
                }
            }

        }

        map.put("totInv", totInvesment==null ? "--" :totInvesment+"");
        map.put("totPay", totPayment==null ? "--" :totPayment+"");


        map.put("tpyPay", totPayment==null ? "--" :totPayment+"");
        map.put("tpyInv", totInvesment==null ? "--" :totInvesment+"");

        Double dueAmount=0.0;

        if (totInvesment!=null && totPayment!=null){
            dueAmount = totInvesment.doubleValue()-totPayment.doubleValue();
        }

        map.put("due", dueAmount==null ? "--" :dueAmount+"");


        ds = new JRTableModelDataSource(model);
        try {
            InputStream systemResourceAsStream = this.getClass().getClassLoader().getResourceAsStream("GenaralSummaryOfIndividual.jrxml");
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
