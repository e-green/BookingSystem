package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.TransactionDAOController;
import org.egreen.opensms.server.entity.Transaction;
import org.springframework.stereotype.Repository;

/**
 * Created by ruwan on 2/11/16.
 */
@Repository
public class TransactionDAOControllerImpl extends  AbstractDAOController<Transaction,String> implements TransactionDAOController{
    public TransactionDAOControllerImpl() {
        super(Transaction.class, String.class);
    }
}
