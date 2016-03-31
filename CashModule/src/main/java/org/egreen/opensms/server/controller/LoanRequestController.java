package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.ApprovedLoan;

import org.egreen.opensms.server.entity.LoanRequest;
import org.egreen.opensms.server.models.LoanRequestModel;
import org.egreen.opensms.server.models.ReturnIdModel1;

import org.egreen.opensms.server.service.LoanRequestDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/loanRequest")
public class LoanRequestController {

 
    @Autowired
    private LoanRequestDAOService loanRequestDAOService;

    /**
     * save LoanRequest
     *
     * @param loanRequest
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody LoanRequest loanRequest) {
        String res = loanRequestDAOService.save(loanRequest);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
    }


    /**
     * Update LoanRequest
     *
     * @param loanRequest
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody LoanRequest loanRequest) {
        String res = loanRequestDAOService.update(loanRequest);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
    }

    /**
     *
     * get All LoanRequests By UserId
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "getAllLoanRequestsByUserId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<LoanRequest> getAllLoanRequestsByUserId(@RequestParam("userId") String userId ,
                                                        @RequestParam("limit") Integer limit,
                                                        @RequestParam("offset") Integer offset) {
        return loanRequestDAOService.getAllLoanRequestsByUserId(userId,limit,offset);
    }

    /**
     * Get All paid Loans and unpaid Loas by individualId and centerId
     *
     * @param centerId
     * @param individualId
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPaidLoansNDueLoansByCenterIdNIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<ApprovedLoan> getAllPaidLoansNDueLoansByCenterIdNIndividualId(@RequestParam("centerId") String centerId ,
                                                                                                  @RequestParam("individualId") String individualId,
                                                                                                  @RequestParam("limit") Integer limit,
                                                                                                  @RequestParam("offset") Integer offset) {
        return loanRequestDAOService.getAllPaidLoansNDueLoansByCenterIdNIndividualId(centerId,individualId,limit,offset);
    }

    /**
     * get LoanRequest ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public LoanRequest getob() {
        return new LoanRequest();
    }


}
