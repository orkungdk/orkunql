package com.roche.dia.dashboardbe.mappers;

import com.roche.dia.dashboardbe.models.DataTransferObject;
import com.roche.dia.dashboardbe.persistence.entities.AbstractEntity;
import com.roche.dia.dashboardbe.utils.ListUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract mapper class for the project.
 *
 * @author orkun.gedik
 * @version 0.0.1-SNAPSHOT
 * @implNote All mappers need to extend this class
 * @implNote Mappers needs to be generated with mapstruct
 */
public abstract class EntityMapper<Entity extends AbstractEntity, Model extends DataTransferObject> extends Mapper<Entity, Model> {

    public List<Model> convertToModelList(List<Entity> entities) {
        return ListUtils.emptyIfNull(entities).stream().map(entity -> convert(entity)).collect(Collectors.toList());
    }

    public List<Entity> convertToEntityList(List<Model> models) {
        return ListUtils.emptyIfNull(models).stream().map(entity -> convert(entity)).collect(Collectors.toList());
    }
}
