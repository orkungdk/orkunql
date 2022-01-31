package com.roche.dia.dashboardbe.persistence.repository;

import com.roche.dia.dashboardbe.exception.ErrorException;
import com.roche.dia.dashboardbe.exception.ErrorType;
import com.roche.dia.dashboardbe.models.Pagination;
import com.roche.dia.dashboardbe.persistence.entities.AbstractEntity;
import com.roche.dia.dashboardbe.persistence.entities.AuditableEntity;
import com.roche.dia.dashboardbe.persistence.preparestatements.AbstractBatchPreparedStatementSetter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Statement;
import java.util.List;

/**
 * @author orkun.gedik
 */
public abstract class AbstractRepository {

    private static final Logger logger = LogManager.getLogger(AbstractRepository.class);

    protected JdbcTemplate jdbcTemplate;

    public AbstractRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void fillPaginationDetails(String paginationQuery, Pagination pagination) {
        Long totalCount = jdbcTemplate.queryForObject(paginationQuery, Long.class);

        pagination.setTotalElements(totalCount);
        pagination.setTotalPages((int) Math.ceil(totalCount.floatValue() / (float) pagination.getPageSize()));
    }


    /**
     * Inserts a new record unless there is not exception during execution.
     *
     * @param sql generated query
     * @param obj to be inserted {@link AbstractEntity} object
     * @return inserted {@link AbstractEntity}
     * @throws ErrorException iff there is data duplication or any other database exception.
     */
    public <I extends AbstractEntity> I insert(String sql, I obj, Object... fields) {
        try {
            logger.info("User is being inserted with details : {}.", obj.toString());

            int result = jdbcTemplate.update(sql, fields);
            checkResult(result);

            return obj;
        } catch (DuplicateKeyException exception) {
            throw new ErrorException(ErrorType.DATA_DUPLICATION_ERROR, "Data is already exist");
        }
    }

    /**
     * Inserts a new record and returns the to be inserted auditable object with filling its auto generated ID field.
     *
     * @param sql generated query
     * @param obj to be inserted {@link AbstractEntity} object
     * @param fields query parameters
     * @return inserted {@link AbstractEntity}
     * @throws ErrorException if there is data duplication or any other database exception.
     */
    public <I extends AuditableEntity> I insertAndFillId(String sql, I obj, Object... fields){
        try {
            logger.info("User is being inserted with details : {}.", obj.toString());

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int result = jdbcTemplate.update(connection -> {
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                for(int i=0; i < fields.length; i++){
                    statement.setObject(i+1, fields[i]);
                }
                return statement;
            }, keyHolder);
            checkResult(result);
            obj.setId((Long) keyHolder.getKeys().get("id"));

            return obj;
        } catch (DuplicateKeyException exception) {
            throw new ErrorException(ErrorType.AUTHENTICATION_ERROR, "Data is already exist");
        }
    }

    /**
     * Inserts a records as a bulk operation.
     *
     * @param query             generated query
     * @param preparedStatement {@link AbstractBatchPreparedStatementSetter}
     * @return inserted {@link AbstractEntity}
     * @throws ErrorException iff there is data duplication or any other database exception.
     */
    public int[] bulkInsert(String query, AbstractBatchPreparedStatementSetter preparedStatement) {
        try {
            return jdbcTemplate.batchUpdate(query, preparedStatement);
        } catch (DuplicateKeyException exception) {
            throw new ErrorException(ErrorType.UNIQUE_CONSTRAINTS_VALIDATION_ERROR, exception.getLocalizedMessage());
        }
    }

    /**
     * Inserts a new record and returns the to be inserted auditable object with filling its auto generated ID field.
     *
     * @param object to be inserted {@link AuditableEntity} object
     * @param query generated query
     * @return inserted {@link AbstractEntity}
     * @throws ErrorException iff there is data duplication or any other database exception.
     */
    public <I extends AuditableEntity> I save(String query, I object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), keyHolder);
        object.setId((Long) keyHolder.getKeys().get("id"));

        return object;
    }

    /**
     * Executes the generated sql query.
     *
     * @param query sql query
     */
    public void delete(String query) {
        jdbcTemplate.execute(query);
    }


    /**
     * Checks the returned result from {@link JdbcTemplate#update(String, Object...)} to validate
     * whether is the entity persisted successfully or not
     *
     * @param result int value of persistence result
     */
    public void checkResult(int result) {
        if (result != 1) {
            throw new ErrorException(ErrorType.DATA_INTEGRITY_ERROR, "Error during saving user entity.");
        }
    }

    /**
     * Returns all found records.
     *
     * @param query generated query
     * @param clazz to be returned entity type
     */
    public <I extends AbstractEntity> List<I> execute(String query, Class<I> clazz) {
        return jdbcTemplate.query(query, new BeanPropertyRowMapper(clazz));
    }

    public <I extends AbstractEntity> I findSingle(String query, Class<I> clazz) {
        try{
            return (I)jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper(clazz));
        }
        catch (Exception e){
            logger.warn("Could not find manual distribution item.");
            return null;
        }
    }
}
