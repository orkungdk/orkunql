package com.roche.dia.dashboardbe.persistence.preparestatements;

import com.roche.dia.dashboardbe.persistence.entities.AbstractEntity;
import lombok.Getter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.util.List;

/**
 * @author orkun.gedik
 */
@Getter
public abstract class AbstractBatchPreparedStatementSetter<Entity extends AbstractEntity> implements BatchPreparedStatementSetter {

    private List<Entity> entities;

    public AbstractBatchPreparedStatementSetter(List<Entity> entities) {
        super();
        this.entities = entities;
    }

    @Override
    public int getBatchSize() {
        return entities.size();
    }

    public Entity getEntity(int index) {
        return entities.get(index);
    }
}
