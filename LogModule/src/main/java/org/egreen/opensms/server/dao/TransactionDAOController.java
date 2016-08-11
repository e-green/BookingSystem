package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Transaction;
import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
public interface TransactionDAOController extends DAOController<Transaction,String> {
    Transaction getTransactionsByDateNAccountNoNType(String date, String accountNo, String type);

    List<Transaction> getTodayTransactionDetailByDateNAccountNo(String date, String accountNo);

    List<Transaction> getTransaActionByDateRange(String accountNo, String firstDate, String secondDate);

    Transaction getTodayTranseActionByTypeId(String date, String accountNo,String typeId);
}
