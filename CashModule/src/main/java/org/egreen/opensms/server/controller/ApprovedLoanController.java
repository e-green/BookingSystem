package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.ApprovedLoanDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/approvedLoan")
public class ApprovedLoanController {


    @Autowired
    private ApprovedLoanDAOService approvedLoanDAOService;

    /**
     * save ApprovedLoan
     *
     * @param approvedLoan
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel save(@RequestBody ApprovedLoan approvedLoan) {
        String res = approvedLoanDAOService.save(approvedLoan);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update ApprovedLoan
     *
     * @param approvedLoan
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody ApprovedLoan approvedLoan) {
        String res = approvedLoanDAOService.update(approvedLoan);
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
//    public List<ApprovedLoanModel> getAll() {
//
//        List<ApprovedLoanModel> centerModelList = new ArrayList<ApprovedLoanModel>();
//
//        List<ApprovedLoan> all = approvedLoanDAOService.getAll();
//
//
//
//
//        for (ApprovedLoan center : all) {
//            ApprovedLoanModel centerModel = new ApprovedLoanModel();
//            centerModel.setApprovedLoanid(center.getApprovedLoanid());
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

//    /**
//     * Get All ApprovedLoaners By Pagination
//     *
//     * @param limit
//     * @param offset
//     * @return
//     */
//    @RequestMapping(value = "getAllApprovedLoanersByPagination", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<ApprovedLoan> getAllApprovedLoanByPagination(@RequestParam("quary") String quary, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
//        return approvedLoanDAOService.getAllApprovedLoanByPagination(quary, limit, offset);
//    }


    @RequestMapping(value = "sorting", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public void sorting() {

        String text = "Car3, Car1, Car12, Car45";
        String[] str = text.split(", ");
        Map<Integer, String> myMap = new HashMap<Integer, String>();
        int[] numOnly = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            numOnly[i] = Integer.parseInt(str[i].replaceAll("\\D", ""));
            myMap.put(numOnly[i], str[i]);
        }
        System.out.println(myMap.values());
    }

    /**
     * getOpenLoanDetailByIndividualId
     *
     * @param individualId
     * @return
     */
    @RequestMapping(value = "getOpenLoanDetailByIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ApprovedLoan getOpenLoanDetailByIndividualId(@RequestParam("individualId") String individualId) {
        return approvedLoanDAOService.getOpenLoanDetailByIndividualId(individualId);
    }

    /**
     * removeApprovedLoanById
     * <p/>
     * //  * @param centerid
     *
     * @return
     */
//    @RequestMapping(value = "removeApprovedLoanById", method = RequestMethod.POST)
//    @ResponseBody
//    public Integer removeApprovedLoanById(@RequestParam("centerid") String centerid) {
//        Integer res = approvedLoanDAOService.removeApprovedLoanById(centerid);
//
//        return res;
//
//    }
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ApprovedLoan getob() {
        return new ApprovedLoan();
    }


}
