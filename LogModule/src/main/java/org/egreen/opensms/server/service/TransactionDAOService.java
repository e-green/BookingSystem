package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Service
public class TransactionDAOService {


    @Autowired
    private TransactionDAOController transactionDAOController;


    /**
     * save Transaction
     *
     * @param transaction
     * @return
     */
    public String save(Transaction transaction) {
        return transactionDAOController.create(transaction);
    }

    /**
     * update Transaction
     *
     * @param transaction
     * @return
     */
    public String update(Transaction transaction) {
        return transactionDAOController.update(transaction);
    }

    /**
     * getAll Transaction
     *
     * @return
     */
    public List<Transaction> getAll() {
        return transactionDAOController.getAll();
    }

    /**
     * Get transaction list by date & account no
     *
     * @param date
     * @param accountNo
     * @return
     */
    public List<Transaction> getTodayTransactionDetailByDateNAccountNo(String date, String accountNo) {
        return transactionDAOController.getTodayTransactionDetailByDateNAccountNo(date,accountNo);
    }

    /**
     * to delete transaction
     * @param transaction
     * @return
     */
    public int delete(Transaction transaction){
        return transactionDAOController.delete(transaction);
    }

}
