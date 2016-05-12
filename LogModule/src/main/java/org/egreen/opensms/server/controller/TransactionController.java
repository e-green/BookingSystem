package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Transaction;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.TransactionDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Controller
@RequestMapping(value = "booking/v1/transaction")
public class TransactionController {

    @Autowired
    private TransactionDAOService transactionDAOService;

    /**
     * save Transaction
     *
     * @param transaction
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Transaction transaction) {
        String res = transactionDAOService.save(transaction);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update Branch
     *
     * @param transaction
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Transaction transaction) {
        String res = transactionDAOService.update(transaction);
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
    public List<Transaction> getAll() {
        List<Transaction> all = transactionDAOService.getAll();
        return all;
    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Transaction getob() {
        return new Transaction();
    }
}
