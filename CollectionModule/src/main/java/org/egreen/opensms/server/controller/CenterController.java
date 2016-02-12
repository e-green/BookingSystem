package org.egreen.opensms.server.controller;
import org.egreen.opensms.server.entity.Account;
import org.egreen.opensms.server.entity.Center;

import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.AccountDAOService;
import org.egreen.opensms.server.service.CenterDAOService;
import org.egreen.opensms.server.service.IndividualDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/center")
public class CenterController {

    @Autowired
    private CenterDAOService centerDAOService;

    @Autowired
    private AccountDAOService accountDAOService;

    @Autowired
    private IndividualDAOService individualDAOService;

    /**
     * save Center
     *
     * @param center
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Center center) {
        String centerId = centerDAOService.save(center);
        String res=null;
        if(null!= centerId){
            Account account=new Account();
            account.setMemberId(centerId);
            res = accountDAOService.save(account);
        }
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }
//
//
    /**
     * Update Center
     *
     * @param center
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Center center) {
        String res = centerDAOService.update(center);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }
//
    /**
     * Get package Id
     *
     * @return
     */
    @RequestMapping(value = "getId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel1 getId(@RequestParam("branchCode") String branchID) {
        String res = centerDAOService.getNextId(branchID);
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
    public List<Center> getAll() {
        List<Center> all = centerDAOService.getAll();
        return all;
    }

    /**
     *
     * checkIfExist
     *
     * @param centerName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("centerName") String centerName) {
        boolean all = centerDAOService.checkIfExist(centerName);
        return all;
    }

    /**
     *
     * getAll
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Center> getAll( @RequestParam("limit") Integer limit,
                                @RequestParam("offset") Integer offset) {
        List<Center> all = centerDAOService.getAllByPagination(limit,offset);
        return all;
    }

    /**
     * Get All Centerers By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllCenterersByPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Center> getAllCenterByPagination(@RequestParam("quary") String quary,
                                                 @RequestParam("limit") Integer limit,
                                                 @RequestParam("offset") Integer offset) {

        return centerDAOService.getAllCenterByPagination(quary, limit, offset);
    }


    /**
     * getAllCentersByBranchId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getAllCentersById", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Center getAllCentersById(@RequestParam("centerId") String centerId) {
        return centerDAOService.getCenterById(centerId);
    }

    /**
     * removeCenterById
     *
     * @param centerid
     * @return
     */
    @RequestMapping(value = "removeCenterById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeCenterById(@RequestParam("centerid") String centerid) {
        Integer res = centerDAOService.removeCenterById(centerid);

        return res;

    }


    @RequestMapping(value = "getCenterOIndividualAccountNoByCenterNameOIndividualName", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel1 getCenterCode(@RequestParam("name") String memberName,@RequestParam("centerOIndividual") int centerOIndividual) {
        String name=null;
        if(0== centerOIndividual){
            name=centerDAOService.getCenterOIndividualAccountNoByCenterNameOIndividualName(memberName);
        }else if(1 == centerOIndividual){
            name=individualDAOService.getCenterOIndividualAccountNoByCenterNameOIndividualName(memberName);
        }
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(name);
        return returnIdModel1;

    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Center getob() {
        return new Center();
    }


}
