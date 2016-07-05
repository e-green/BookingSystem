package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Envelope;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.models.InOutModel;
import org.egreen.opensms.server.models.SpecifyDateInOutModel;
import org.egreen.opensms.server.service.AccountDAOService;
import org.egreen.opensms.server.service.EnvelopeDAOService;
import org.egreen.opensms.server.service.IndividualDAOService;
import org.egreen.opensms.server.service.TransactionDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

            Double totalChitCount = 0.0;
            System.out.println(centerId+"----"+individual.getIndividualId()+"----"+firstDate+"---"+secondDate);
            List<Envelope> envelopeList = envelopeDAOService.getEnvelopeByDateRange(centerId,individual.getIndividualId(),firstDate,secondDate);
            for (Envelope envelope : envelopeList) {
                totalChitCount = envelope.getChitCount().doubleValue();
            }
            System.out.println(totalChitCount);
            inOutModel.setChitCount(totalChitCount);
            Account account = accountDAOService.getAccountByCenterOIndividualId(individual.getIndividualId());
            if (account != null) {
                Double totalInvesment = 0.0;
                Double totalPayment = 0.0;
                Double totalCash = 0.0;
                List<Transaction> transaActionByDateRange = transactionDAOService.getTransaActionByDateRange(account.getAccountNo(), firstDate, secondDate);
                for (Transaction transaction : transaActionByDateRange) {
                    if (transaction.getTypeId().equals("Inv")) {
                        totalInvesment = transaction.getDebit().doubleValue();
                    }

                    if (transaction.getTypeId().equals("PAY")) {
                        totalPayment = transaction.getCredit().doubleValue();
                    }

                    if (transaction.getTypeId().equals("CSH")) {
                        totalCash = transaction.getCredit().doubleValue();
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
}
