package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.LoanRequest;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.LoanRequestDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ReturnIdModel save(@RequestBody LoanRequest loanRequest) {
        String res = loanRequestDAOService.save(loanRequest);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update LoanRequest
     *
     * @param loanRequest
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody LoanRequest loanRequest) {
        String res = loanRequestDAOService.update(loanRequest);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

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
     * Get All LoanRequesters By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllLoanRequestersByPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<LoanRequest> getAllLoanRequestByPagination(@RequestParam("type") Integer type,
                                                           @RequestParam("limit") Integer limit,
                                                           @RequestParam("offset") Integer offset) {
        return loanRequestDAOService.getAllLoanRequestByPagination(type, limit, offset);
    }

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
