package org.egreen.opensms.server.service;

import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.models.SpecifyDateInOutModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruwan on 5/12/16.
 */
@Service
public class DailyReportService {

    @Autowired
    private TransactionDAOService transactionDAOService;

    @Autowired
    private IndividualDAOService individualDAOService;

    @Autowired
    private  AccountDAOService accountDAOService;

    public SpecifyDateInOutModel getInputNOutputBySpecifiedDateForIndividuals(String sTime) {
        List<Individual> individualList = individualDAOService.getAll();
        List<Account> accountList = accountDAOService.getAll();
        List<Account> individualAccountList=new ArrayList<Account>();
        //this Transaction list have all the transactions of all the individuals given date
        List<Transaction> transactionList=new ArrayList<Transaction>();
        for (Individual individual:individualList) {
            String individualId = individual.getIndividualId();
            for (Account account:accountList) {
                if(account.getMemberId().equals(individualId)){
                    individualAccountList.add(account);
                }
            }
        }

        for (Account account:individualAccountList) {
            List<Transaction> todayTransactionDetailByDateNAccountNo = transactionDAOService.getTodayTransactionDetailByDateNAccountNo(sTime, account.getAccountNo());
            for (Transaction transaction:todayTransactionDetailByDateNAccountNo) {
                transactionList.add(transaction);
            }
        }

        //have to put input values and out values in the transaction below
        return null;



    }
}
