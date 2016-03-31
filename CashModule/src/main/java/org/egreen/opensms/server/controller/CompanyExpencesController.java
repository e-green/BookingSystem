package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.CompanyExpences;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.CompanyExpencesDAOService;
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
@RequestMapping("booking/v1/companyExpences")
public class CompanyExpencesController {


    @Autowired
    private CompanyExpencesDAOService companyExpencesDAOService;

    /**
     * save CompanyExpences
     *
     * @param companyExpences
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody CompanyExpences companyExpences) {
        String res = companyExpencesDAOService.save(companyExpences);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update CompanyExpences
     *
     * @param companyExpences
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody CompanyExpences companyExpences) {
        String res = companyExpencesDAOService.update(companyExpences);
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
    public List<CompanyExpences> getAll() {
        List<CompanyExpences> all = companyExpencesDAOService.getAll();
        return all;
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
     * get All Company Expencess By Center
     *
     * @param center
     * @return
     */
    @RequestMapping(value = "getAllCompanyExpencessByCenter", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<CompanyExpences> getAllCompanyExpencessByCenter(@RequestParam("center") String center,
                                                                @RequestParam(value = "firstDate") String firstDate,
                                                                @RequestParam(value = "secondDate") String secondDate,
                                                                @RequestParam(value = "limit") Integer limit,
                                                                @RequestParam(value = "offset") Integer offset) {
        return companyExpencesDAOService.getAllCompanyExpencessByCenter(center,firstDate,secondDate,limit,offset);
    }

    /**
     * get CompanyExpences ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public CompanyExpences getob() {
        return new CompanyExpences();
    }


}
