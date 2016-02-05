package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Center;
import org.egreen.opensms.server.entity.Individual;
import org.egreen.opensms.server.models.ReturnIdModel;
import org.egreen.opensms.server.service.IndividualDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * Created by dewmal on 7/17/14.
 */
@Controller
@RequestMapping("booking/v1/individual")
public class IndividualController {

    @Autowired
    private IndividualDAOService individualDAOService;

    /**
     * save Branch
     *
     * @param individual
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel save(@RequestBody Individual individual) {
        String res = individualDAOService.save(individual);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }


    /**
     * Update Branch
     *
     * @param individual
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel update(@RequestBody Individual individual) {
        String res = individualDAOService.update(individual);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(res);
        return returnIdModel;

    }

    /**
     *
     * getAll
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAll( @RequestParam("limit") Integer limit,
                                @RequestParam("offset") Integer offset) {
        List<Individual> all = individualDAOService.getAllByPagination(limit,offset);
        return all;
    }


    /**
     *
     * checkIfExist
     *
     * @param individualName
     * @return
     */
    @RequestMapping(value = "checkIfExist", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean checkIfExist(@RequestParam("individualName") String individualName) {
        boolean all = individualDAOService.checkIfExist(individualName);
        return all;
    }

    /**
     * Get package Id
     *
     * @return
     */
    @RequestMapping(value = "getId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ReturnIdModel getId(@RequestParam("name") String name) {
        String nameNew=name.substring(0, 3);
        ReturnIdModel returnIdModel = new ReturnIdModel();
        returnIdModel.setId(nameNew);
        return returnIdModel;

    }

    /**
     *
     * getIndividualsByCenterId
     *
     * @param centerId
     * @return
     */
    @RequestMapping(value = "getIndividualsByCenterId", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getIndividualsByCenterId(@RequestParam("centerId") String centerId) {
        List<Individual>individualList =  individualDAOService.getIndividualsByCenterId(centerId);
        return individualList;

    }


    /**
     * Get All
     *
     * @return
     */
//    @RequestMapping(value = "getAll", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public List<Individual> getAll() {
//        return individualDAOService.getAll();
//    }

    /**
     * Get All Branchers By Pagination
     *
     * @param limit
     * @param offset
     * @return
     */
    @RequestMapping(value = "getAllPagination", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAllBranchersByPagination(@RequestParam("limit") Integer limit,
                                                        @RequestParam("offset") Integer offset) {
        return individualDAOService.getAllBranchersByPagination(limit, offset);
    }

    /***
     *
     * getAllBranches
     *
     *
     * @param limit
     * @param offset
     * @param type
     * @return
     */
    @RequestMapping(value = "getAllBranches", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Individual> getAllBranches(@RequestParam("limit") Integer limit,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("type") Integer type) {
        return individualDAOService.getAllBranchersByPagination(limit, offset);
    }

    /**
     * removeBranchById
     *
     * @param individualId
     * @return
     */
    @RequestMapping(value = "removeBranchById", method = RequestMethod.POST)
    @ResponseBody
    public Integer removeBranchById(@RequestParam("individualId") String individualId) {
        Integer res = individualDAOService.removeBranchById(individualId);
        return res;
    }


    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Individual getob() {
        return new Individual();
    }


    /**
     *
     *
     * getBranchCode
     *
     * @param locationName
     * @return
     */
    @RequestMapping(value = "getBranchCode", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public String getBranchCode(@RequestParam("locationName") String locationName) {

        String id = individualDAOService.getNextID();
        String name=locationName.substring(0, 2)+id;
        return name;
    }








}
