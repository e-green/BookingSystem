package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.Transaction;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Repository
public class TransactionDAOControllerImpl extends  AbstractDAOController<Transaction,String> implements TransactionDAOController{
    public TransactionDAOControllerImpl() {
        super(Transaction.class, String.class);
    }

    @Override
    public Transaction getTransactionsByDateNAccountNoNType(String date, String accountNo, String typeId) {
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND t.typeId = :typeId AND t.sTime = :formattedDate");
        query.setString("accountNo", accountNo);
        query.setString("typeId", typeId);
        query.setString("formattedDate", date);

        Transaction transaction=new Transaction();
        List<Transaction> transactionList= query.list();
        if(!transactionList.isEmpty()){
            for (Transaction tr:transactionList ) {
                transaction=tr;
            }
        }
        return transaction;
    }

    @Override
    public List<Transaction> getTodayTransactionDetailByDateNAccountNo(String formatedDate, String accountNo) {
        /**
         * please use below code when you try to get with unique string Id(date)
         */
        Query query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND t.sTime = :formattedDate");
        query.setString("accountNo", accountNo);
        query.setString("formattedDate", formatedDate);
        List<Transaction> transactionList= query.list();
        return transactionList;
    }

    @Override
    public List<Transaction> getTransaActionByDateRange(String accountNo, String firstDate, String secondDate) {

        Query query;
        List<Transaction> transactionList;

        if (secondDate!=null && !secondDate.isEmpty()) {
            query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND t.sTime BETWEEN :firstDate AND :secondDate");
            query.setString("accountNo", accountNo);
            query.setString("firstDate", firstDate);
            query.setString("secondDate", secondDate);
           transactionList = query.list();
        }else {
            query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND t.sTime =:firstDate");
            query.setString("accountNo", accountNo);
            query.setString("firstDate", firstDate);
            transactionList = query.list();
        }
        return transactionList;
    }


}
