package org.egreen.opensms.server.dao.impl;

import org.egreen.opensms.server.dao.TypeDAOController;
import org.egreen.opensms.server.entity.Type;
import org.springframework.stereotype.Repository;

/**
 * Created by ruwan on 2/11/16.
 */
@Repository
public class TypeDAOControllerImpl extends AbstractDAOController<Type,String> implements TypeDAOController{
    public TypeDAOControllerImpl() {
        super(Type.class, String.class);
    }
}
