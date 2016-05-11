package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.TempDataDAOController;
import org.egreen.opensms.server.entity.Tempdata;
import org.egreen.opensms.server.model.ReturnIdModel1;
import org.egreen.opensms.server.model.TempDateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ruwan on 5/10/16.
 */
@Service
public class TempDateService {

    @Autowired
    private TempDataDAOController tempDataDAOController;

    /**
     * create new Tempdate
     *
     * @param tempdata
     * @return
     */
    public String save(Tempdata tempdata) {
        tempdata.setKey("currentDate");
        String s = tempDataDAOController.create(tempdata);
        return s;
    }

    /**
     * updating temp date
     *
     * @param tempdata
     * @return
     */
    public String update(Tempdata tempdata) {
        return tempDataDAOController.update(tempdata);
    }

    /**
     * get current date
     *
     * @return
     */
    public TempDateModel getCurrentDate() {
        List<Tempdata> tempdataList = tempDataDAOController.getAll();
        TempDateModel tDate=new TempDateModel();

        for(Tempdata tempdata:tempdataList){
            tDate.setKey(tempdata.getKey());
            tDate.setValue(tempdata.getValue());
        }
        return tDate;
    }
}
