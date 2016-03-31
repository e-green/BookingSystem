package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.ApprovedLoan;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.ApprovedLoanDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    public ReturnIdModel1 save(@RequestBody ApprovedLoan approvedLoan) {
        String res = approvedLoanDAOService.save(approvedLoan);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
    }


    /**
     * Update ApprovedLoan
     *
     * @param approvedLoan
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody ApprovedLoan approvedLoan) {
        String res = approvedLoanDAOService.update(approvedLoan);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

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
    }

    /**
     * get Approved Loan Detail By IndividualId
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
     * get all unpaid loans
     *
     * @return
     */
    @RequestMapping(value = "getAllUnpaidLoans", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<ApprovedLoan> getAll() {
        List<ApprovedLoan> unpaidLoans = approvedLoanDAOService.getUnpaidLoans();
        return unpaidLoans;
    }

    /**
     * get All Approved Loans By IndividualId
     *
     * @param individualId
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllApprovedLoansByIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<ApprovedLoan> getAllApprovedLoansByIndividualId(@RequestParam("individualId") String individualId ,
                                                        @RequestParam("limit") Integer limit,
                                                        @RequestParam("offset") Integer offset) {
        return approvedLoanDAOService.getAllApprovedLoansByIndividualId(individualId,limit,offset);
    }

    /**
     *get Unpaid Loans By IndividualId
     *
     * @param individualId
     * @return
     */
    @RequestMapping(value = "getUnpaidLoansByIndividualId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<ApprovedLoan> getUnpaidLoansByIndividualId(@RequestParam("individualId") String individualId){
        return approvedLoanDAOService.getUnpaidLoansByIndividualId(individualId);
    }

    /**
     * get approve loan ob
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ApprovedLoan getob() {
        return new ApprovedLoan();
    }
}
