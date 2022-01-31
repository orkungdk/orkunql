package com.roche.dia.dashboardbe.mappers;

import com.roche.dia.dashboardbe.models.DataTransferObject;

/**
 * @author orkun.gedik
 */
public abstract class Mapper<I, O extends DataTransferObject> {

    public abstract O convert(I object);

    public abstract I convert(O object);
}
