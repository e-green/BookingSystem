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
    public Transaction getTransactionsByDateNAccountNoNType(Timestamp date, String accountNo, String typeId) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(date);
        Query query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND t.typeId = :typeId AND DATE(t.time) = DATE( :formattedDate)");
        query.setString("accountNo", accountNo);
        query.setString("typeId", typeId);
        query.setString("formattedDate", formatedDate);
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
        Query query = getSession().createQuery("SELECT t FROM Transaction t WHERE t.accountNo = :accountNo AND DATE(t.time) = DATE( :formattedDate)");
        query.setString("accountNo", accountNo);
        query.setString("formattedDate", formatedDate);
        Transaction transaction=new Transaction();
        List<Transaction> transactionList= query.list();
        return transactionList;
    }
}
