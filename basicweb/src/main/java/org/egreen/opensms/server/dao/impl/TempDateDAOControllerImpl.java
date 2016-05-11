package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.TempDataDAOController;
import org.egreen.opensms.server.entity.Tempdata;
import org.springframework.stereotype.Repository;

/**
 * Created by dewmal on 7/31/14.
 */
@Repository
public class TempDateDAOControllerImpl extends AbstractDAOController<Tempdata, String> implements TempDataDAOController {

    public TempDateDAOControllerImpl() {
        super(Tempdata.class, String.class);
    }
}
