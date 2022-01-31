package com.roche.dia.dashboardbe.mappers;

import com.roche.dia.dashboardbe.helpers.MetaDataHelper;
import com.roche.dia.dashboardbe.models.AuditDTO;
import com.roche.dia.dashboardbe.models.MetaData;
import com.roche.dia.dashboardbe.models.ResourceAudit;
import com.roche.dia.dashboardbe.persistence.entities.AuditableEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * @author orkun.gedik
 */
public abstract class AuditableEntityMapper<Entity extends AuditableEntity, Model extends AuditDTO> extends EntityMapper<Entity, Model> {

    @Autowired
    private MetaDataHelper metaDataHelper;

    @AfterMapping
    public void audit(@MappingTarget Entity entity, Model model) {
        if (Objects.isNull(entity) || Objects.isNull(model)) {
            return;
        }

        if (Objects.isNull(model.getMeta())) {
            metaDataHelper.fillMeta(model);
        }

        MetaData metaData = model.getMeta();

        ResourceAudit created = metaData.getCreated();
        ResourceAudit updated = metaData.getUpdated();

        if (Objects.nonNull(created) && Objects.isNull(entity.getCreatedAt()) && Objects.isNull(entity.getCreatedBy())) {
            entity.setCreatedAt(created.getAt());
            entity.setCreatedBy(created.getBy());
        }
        if (Objects.nonNull(updated)) {
            entity.setUpdatedAt(updated.getAt());
            entity.setUpdatedBy(updated.getBy());
        }
    }

    @AfterMapping
    public void audit(@MappingTarget Model model, Entity entity) {
        if (Objects.isNull(entity) || Objects.isNull(model)) {
            return;
        }
        MetaData metaData = new MetaData();
        ResourceAudit created = new ResourceAudit();
        ResourceAudit updated = new ResourceAudit();

        created.setAt(entity.getCreatedAt());
        created.setBy(entity.getCreatedBy());

        updated.setAt(entity.getUpdatedAt());
        updated.setBy(entity.getUpdatedBy());

        metaData.setCreated(created);
        metaData.setUpdated(updated);

        model.setMeta(metaData);
    }
}
