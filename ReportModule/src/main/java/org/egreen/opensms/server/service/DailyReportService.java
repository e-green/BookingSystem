package org.egreen.opensms.server.service;

import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.models.ChitCountListModel;
import org.egreen.opensms.server.models.SpecifyDateInOutModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    private ChitDAOService chitDAOService;

    /**
     * get Input & Output transaction summary By Specified Date For Individuals
     *
     * @param sTime
     * @return
     */
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
        double ncValue=0.0;
        double lcsValue=0.0;
        double paymentDue=0.0;
        double loanDeductionPayment=0.0;
        double loan=0.0;
        double commision=0.0;
        double pay=0.0;
        double investment=0.0;
        double salary=0.0;
        double overPayment=0.0;
        double rent=0.0;
        double excess=0.0;
        double expenses=0.0;
        double pcCharges=0.0;
        double cash=0.0;
        double payment=0.0;

        //have to put input values and out values in the transaction below
        for (Transaction transaction:transactionList) {
            if(transaction != null){
                if(transaction.getDebit() != null && transaction.getTypeId().equals("NC")){
                    ncValue+=transaction.getDebit().doubleValue();
                }
                if(transaction.getDebit() != null && transaction.getTypeId().equals("LCS")){
                    lcsValue+=transaction.getDebit().doubleValue();
                }
                if(transaction.getDebit() != null && transaction.getTypeId().equals("PD")){
                    paymentDue+=transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("LN")){
                    loanDeductionPayment+=transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("LON")){
                    loan+=transaction.getCredit().doubleValue();
                }
                if(transaction.getCredit() != null && transaction.getTypeId().equals("COM")){
                    commision+=transaction.getCredit().doubleValue();
                }
                if(transaction.getTypeId().equals("PAY")){
                    pay+=transaction.getCredit().doubleValue();
                }
                if (transaction.getTypeId().equals("Inv")) {
                    investment += transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("Salary")){
                    salary+=transaction.getCredit().doubleValue();
                }
                if(transaction.getTypeId().equals("OverPayment")){
                    overPayment+=transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("RENT")){
                    rent+=transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("Excess")){
                    excess+=transaction.getCredit().doubleValue();
                }
                if(transaction.getTypeId().equals("EXP")){
                    expenses+=transaction.getCredit().doubleValue();
                }
                if(transaction.getTypeId().equals("PC")){
                    pcCharges+=transaction.getDebit().doubleValue();
                }
                if(transaction.getTypeId().equals("CSH")){
                    cash+=transaction.getCredit().doubleValue();
                }
                if(transaction.getTypeId().equals("Payment")){
                    payment+=transaction.getCredit().doubleValue();
                }
            }
        }

        double inValue=0.0;
        double outValue=0.0;

        inValue=ncValue+lcsValue+paymentDue+loanDeductionPayment+investment+overPayment+rent+pcCharges;
        outValue=loan+commision+pay+salary+excess+expenses+cash+payment;

        SpecifyDateInOutModel specifyDateInOutModel=new SpecifyDateInOutModel();
        specifyDateInOutModel.setInValue(new BigDecimal(inValue));
        specifyDateInOutModel.setOutValue(new BigDecimal(outValue));
        return specifyDateInOutModel;

    }

    /**
     * get Chit Count List Of Individual For Spesific Date Range
     *
     * @param sTime
     * @param endSTime
     * @return
     */
    public List<ChitCountListModel> getChitCountListOfIndividualForSpesificDateRange(String individualId,String sTime, String endSTime) {
        return chitDAOService.getChitCountListOfIndividualForSpesificDateRange(individualId,sTime,endSTime);
    }
}
