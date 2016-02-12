package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.AccountDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Controller
@RequestMapping(value = "booking/v1/account")
public class AccountController {

    @Autowired
    private AccountDAOService accountDAOService;

    /**
     * save Account
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Account account) {
        String res = accountDAOService.save(account);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update Branch
     *
     * @param account
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Account account) {
        String res = accountDAOService.update(account);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

    /**
     * Get All
     *
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Account> getAll() {
        List<Account> all = accountDAOService.getAll();
        return all;
    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Account getob() {
        return new Account();
    }
}
