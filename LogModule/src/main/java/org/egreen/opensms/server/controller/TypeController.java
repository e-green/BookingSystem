package org.egreen.opensms.server.controller;

import org.egreen.opensms.server.entity.Type;
import org.egreen.opensms.server.models.ReturnIdModel1;
import org.egreen.opensms.server.service.TypeDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ruwan on 2/11/16.
 */
@Controller
@RequestMapping(value = "booking/v1/type")
public class TypeController {

    @Autowired
    private TypeDAOService typeDAOService;

    /**
     * save Type
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 save(@RequestBody Type type) {
        String res = typeDAOService.save(type);
        ReturnIdModel1 returnIdModel1 = new ReturnIdModel1();
        returnIdModel1.setId(res);
        return returnIdModel1;

    }


    /**
     * Update Branch
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ReturnIdModel1 update(@RequestBody Type type) {
        String res = typeDAOService.update(type);
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
    public List<Type> getAll() {
        List<Type> all = typeDAOService.getAll();
        return all;
    }

    @RequestMapping(value = "ob", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Type getob() {
        return new Type();
    }
}
