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

  

//
//    /**
//     * Get All
//     *
//     * @return
//     */
//    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<LoanRequestModel> getAll() {
//
//        List<LoanRequestModel> centerModelList = new ArrayList<LoanRequestModel>();
//
//        List<LoanRequest> all = loanRequestDAOService.getAll();
//
//
//
//
//        for (LoanRequest center : all) {
//            LoanRequestModel centerModel = new LoanRequestModel();
//            centerModel.setLoanRequestid(center.getLoanRequestid());
//            centerModel.setName(center.getName());
//            centerModel.setBranchid((center.getBranchid()));
//            LoanRequest branch = branchDAOService.getBranchById(center.getBranchid());
//            if (branch != null) {
//                centerModel.setBranchName(branch.getName());
//            }
//            centerModelList.add(centerModel);
//        }
//        return centerModelList;
//    }



    /**
     *
     * getAllLoanRequestsByUserId
     *
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
     * removeLoanRequestById
     *
   //  * @param centerid
     * @return
     */
//    @RequestMapping(value = "removeLoanRequestById", method = RequestMethod.POST)
//    @ResponseBody
//    public Integer removeLoanRequestById(@RequestParam("centerid") String centerid) {
//        Integer res = loanRequestDAOService.removeLoanRequestById(centerid);
//
//        return res;
//
//    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public LoanRequest getob() {
        return new LoanRequest();
    }


}
