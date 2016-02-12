package org.egreen.opensms.server.dao;

import org.egreen.opensms.server.entity.Account;

/**
 * Created by ruwan on 2/11/16.
 */
public interface AccountDAOController extends DAOController<Account,String> {

    Account getAccountByCenterOIndividualId(String memberid);
}
