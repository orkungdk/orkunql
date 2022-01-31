package com.roche.dia.dashboardbe.helpers;

import com.roche.dia.dashboardbe.models.AuditDTO;
import com.roche.dia.dashboardbe.models.MetaData;
import com.roche.dia.dashboardbe.models.ResourceAudit;
import com.roche.dia.dashboardbe.persistence.entities.AuditableEntity;
import com.roche.dia.dashboardbe.utils.ObjectUtils;
import com.roche.dia.dashboardbe.utils.TimeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * @author orkun.gedik
 */
@Component
public class MetaDataHelper {

    private static final Logger logger = LogManager.getLogger(MetaDataHelper.class);

    /**
     * Call this method for the new created object instances.
     * This method fills the {@link MetaData} object in the given auditable.
     *
     * @param object to be filled auditable
     * @param <I>    type
     */
    public <I extends AuditDTO> void fillMeta(I object) {
        if (Objects.isNull(object)) {
            logger.debug("Auditable resource is null. Fill meta process is terminated.");
            return;
        }
        ResourceAudit created = getResourceAudit();

        MetaData metaData = new MetaData();
        metaData.setCreated(created);

        object.setMeta(metaData);
    }

    /**
     * Call this method for the new created or updated object instances.
     * This method fills the {@link MetaData} object in the given auditable.
     *
     * @param object to be filled auditable
     * @param <I>    type
     */
    public <I extends AuditableEntity> void fillMeta(I object) {
        if (Objects.isNull(object)) {
            logger.debug("Auditable resource is null. Fill meta process is terminated.");
            return;
        }

        if(object.getId() == null){
            object.setCreatedBy("SYSTEM");
            object.setCreatedAt(TimeUtils.now());
        }
        else{
            object.setUpdatedBy("SYSTEM");
            object.setUpdatedAt(TimeUtils.now());
        }
    }

    /**
     * Call this method for the updated object instances.
     * This method fills the {@link MetaData} object in the given auditable.
     *
     * @param oldObject reference auditable
     * @param newObject target auditable
     * @param <I>       type
     */
    public <I extends AuditDTO> void fillMeta(I oldObject, I newObject) {
        if (Objects.isNull(newObject)) {
            logger.debug("Auditable new resource is null. Fill meta process is terminated.");
            return;
        }
        if (Objects.isNull(oldObject)) {
            logger.debug("Auditable old resource is null. Fill meta process will be executed for create case.");
            fillMeta(newObject);
            return;
        }
        ResourceAudit updated = getResourceAudit();

        MetaData metaData = oldObject.getMeta();
        metaData.setUpdated(updated);

        newObject.setMeta(metaData);
    }

    /**
     * Extracts meta data details from an entity and returns a meta data model.
     *
     * @param entity {@link AuditableEntity}
     * @param <I> anu type of auditable entity
     * @return {@link MetaData}
     */
    public <I extends AuditableEntity> MetaData getMetaData(I entity) {
        ResourceAudit created = new ResourceAudit();
        created.setAt(entity.getCreatedAt());
        created.setBy(entity.getCreatedBy());

        ResourceAudit updated = new ResourceAudit();
        updated.setAt(entity.getUpdatedAt());
        updated.setBy(entity.getUpdatedBy());

        MetaData metaData = new MetaData();
        metaData.setCreated(created);
        metaData.setUpdated(updated);

        return metaData;
    }


    private ResourceAudit getResourceAudit() {
        ResourceAudit created = new ResourceAudit();
        created.setAt(TimeUtils.now());

        Optional requestOwner = ObjectUtils.getNestedObjectOfNullable(() ->
                SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (requestOwner.isPresent()) {
            created.setBy(requestOwner.get().toString());
        }
        return created;
    }
}
