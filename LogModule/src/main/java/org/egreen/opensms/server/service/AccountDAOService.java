package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.AccountDAOController;
import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.models.AccountDetail;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ruwan on 2/11/16.
 */
@Service
public class AccountDAOService {

    @Autowired
    private AccountDAOController accountDAOController;

    /**
     * save Account
     *
     * @param account
     * @return
     */
    public String save(Account account) {
        String id = new Date().getTime() + "";
        Hashids hashids = new Hashids(id);
        String hexaid = hashids.encodeHex(String.format("%040x", new BigInteger(1, id.getBytes())));
        String newid = hexaid + "" + randomString(10);
        account.setAccountNo(newid);
        return accountDAOController.create(account);
    }

    /**
     * update Account
     *
     * @param account
     * @return
     */
    public String update(Account account) {
        return accountDAOController.update(account);
    }

    /**
     * getAll accounts
     *
     * @return
     */
    public List<Account> getAll() {
        return accountDAOController.getAll();
    }



    public Account getById(String id){return  accountDAOController.read(id);}


    /**
     * Get Random String
     *
     * @param len
     * @return
     * @author Pramoda Nadeeshan Fernando
     * @version 1.0
     * @since 2015-02-12 4.26PM
     */
    private String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    /**
     * get acocunt by center or individual id
     *
     * @param memberId
     * @return
     */
    public Account getAccountByCenterOIndividualId(String memberId){
        return accountDAOController.getAccountByCenterOIndividualId(memberId);
    }

    /**
     * Updating account amout by individualId and amount
     *
     * @param accountDetail
     * @return
     */
    public String updateAccountByIndividualIdNAmount(AccountDetail accountDetail) {
        Account accountByIndividualId = accountDAOController.getAccountByCenterOIndividualId(accountDetail.getIndividualId());
        accountByIndividualId.setAmount(accountDetail.getAmount());
        return accountDAOController.update(accountByIndividualId);
    }

}
