package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.AccountDAOController;
import org.egreen.opensms.server.entity.Account;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Repository
public class AccountDAOControllerImpl extends AbstractDAOController<Account,String> implements AccountDAOController{
    public AccountDAOControllerImpl() {
        super(Account.class, String.class);
    }

    @Override
    public Account getAccountByCenterOIndividualId(String memberid) {
        Account foAccount=new Account();
        Criteria criteria = getSession().createCriteria(entityType);
        criteria.add(Restrictions.eq("memberId", memberid));
        List<Account> accountList= criteria.list();
        if(accountList.size()>0){
            for (Account account:accountList) {
                foAccount=account;
            }
        }
        return foAccount;
    }

    @Override
    public Account getAccountByMemberId(String memberId) {
        return null;
    }


}
