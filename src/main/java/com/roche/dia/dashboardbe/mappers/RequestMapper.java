package com.roche.dia.dashboardbe.mappers;

import com.roche.dia.dashboardbe.models.DataTransferObject;
import com.roche.dia.dashboardbe.models.RequestObject;

/**
 * @author orkun.gedik
 */
public abstract class RequestMapper<Request extends RequestObject, Model extends DataTransferObject> extends Mapper<Request, Model> {

    /**
     * Do not return a request object in any response.
     */
    @Deprecated
    @Override
    public Request convert(Model object) {
        return null;
    }
}
