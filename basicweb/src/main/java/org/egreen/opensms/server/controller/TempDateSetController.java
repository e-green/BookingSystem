package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Tempdata;
import org.egreen.opensms.server.model.ReturnIdModel1;
import org.egreen.opensms.server.model.TempDateModel;
import org.egreen.opensms.server.service.TempDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ruwan on 5/10/16.
 */
@Controller
@RequestMapping("booking/v1/tempDate")
public class TempDateSetController {

    @Autowired
    private TempDateService tempDateService;

    /**
     * save temp date
     *
     * @param tempdata
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public  ReturnIdModel1 save(@RequestBody Tempdata tempdata) {
        String res = tempDateService.save(tempdata);
        ReturnIdModel1 returnIdModel1=new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
    }

    /**
     * update temp date
     *
     * @param tempdata
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Tempdata tempdata) {
        String res = tempDateService.update(tempdata);
        ReturnIdModel1 returnIdModel1=new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;
    }

    /**
     * get current temp date
     *
     * @return
     */
    @RequestMapping(value = "getCurrentTempDate", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public TempDateModel getCurrentTempDate() {
        return tempDateService.getCurrentDate();
    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Tempdata getob() {
        return new Tempdata();
    }


}
