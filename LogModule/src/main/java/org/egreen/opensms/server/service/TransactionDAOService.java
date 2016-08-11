package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        transaction.setTransactionId(newid);
        transaction.setTime(new Timestamp(new Date().getTime()));


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

    public List<Transaction> getTransaActionByDateRange(String accountNo, String firstDate, String secondDate) {
        return transactionDAOController.getTransaActionByDateRange(accountNo,firstDate,secondDate);
    }

    public Transaction getTodayTranseActionByTypeId(String date, String accountNo,String typeId) {
        return transactionDAOController.getTodayTranseActionByTypeId(date,accountNo,typeId);
    }


    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
