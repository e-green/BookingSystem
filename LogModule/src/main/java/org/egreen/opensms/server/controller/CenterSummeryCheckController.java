package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.CenterSummeryCheck;
import org.egreen.opensms.server.model.ReturnIdModel1;
import org.egreen.opensms.server.service.CenterSummeryCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ruwan on 5/20/16.
 */
@Controller
@RequestMapping(value = "booking/v1/centerSummeryCheck")
public class CenterSummeryCheckController {

    @Autowired
    private CenterSummeryCheckService centerSummeryCheckService;

    /**
     * save center summary check
     *
     * @param centerSummeryCheck
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody CenterSummeryCheck centerSummeryCheck) {
        String res = centerSummeryCheckService.save(centerSummeryCheck);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }

    /**
     * is center summery finish or not
     *
     * @param centerId
     * @param sTime
     * @return
     */
    @RequestMapping(value = "getCenterSummeryCheck", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public boolean getCenterSummeryCheck(@RequestParam("centerId") String centerId,@RequestParam("sTime") String sTime) {
        return centerSummeryCheckService.getCenterSummeryCheck(centerId,sTime);
    }

    /**
     * get CenterSummeryCheck ob
     *
     * @return
     */
    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public CenterSummeryCheck getOb() {
        return new CenterSummeryCheck();
    }
}
