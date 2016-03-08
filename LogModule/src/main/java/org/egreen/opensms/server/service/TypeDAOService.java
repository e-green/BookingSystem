package org.egreen.opensms.server.service;

import org.egreen.opensms.server.dao.TypeDAOController;
import org.egreen.opensms.server.entity.Type;
import org.egreen.opensms.server.utils.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ruwan on 2/11/16.
 */
@Service
public class TypeDAOService {

    @Autowired
    private TypeDAOController typeDAOController;

    /**
     * save Type
     *
     * @param type
     * @return
     */
    public String save(Type type) {
        return typeDAOController.create(type);
    }

    /**
     * update Type
     *
     * @param type
     * @return
     */
    public String update(Type type) {
        return typeDAOController.update(type);
    }

    /**
     * getAll Typies
     *
     * @return
     */
    public List<Type> getAll() {
        return typeDAOController.getAll();
    }


}
