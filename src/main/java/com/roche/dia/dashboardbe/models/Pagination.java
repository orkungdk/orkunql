package com.roche.dia.dashboardbe.models;

import com.roche.dia.dashboardbe.exception.ErrorType;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
public class Pagination extends PageRequest {

    private int totalPages;

    private long totalElements;

    private String sortBy;

    private Sort.Direction direction;

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     * @implNote This constructor should be called in this class only.
     *
     * @param pageNumber zero-based page index, must not be negative.
     * @param pageSize the size of the page to be returned, must be greater than 0.
     * @param sortBy ordering qualifier for pagination
     * @param direction the direction of the ordering
     */
    protected Pagination(int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
        super(pageNumber, pageSize, Sort.by(direction, sortBy));
        this.sortBy = sortBy;
        this.direction = direction;
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     * @implNote This constructor should be called in this class only.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()} instead.
     */
    protected Pagination(int page, int size, Sort sort) {
        super(page, size, sort);
    }


    /**
     * Creates a new sorted {@link Pagination}.
     *
     * @param pageNumber zero-based pageNumber index, must not be negative.
     * @param pageSize the pageSize of the pageNumber to be returned, must be greater than 0.
     * @param sortBy the sorting value of the pagination, must not be null.
     */
    public static Pagination of(int pageNumber, int pageSize, String sortBy) {
        AssertUtils.assertPositive(pageNumber, ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'pageNumber' must not be less than one!");
        AssertUtils.assertPositive(pageSize, ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'pageSize' must not be less than one!");
        AssertUtils.assertNotNull(ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'sortBy' must not be null!", sortBy);

        return new Pagination(pageNumber, pageSize, Sort.by(sortBy));
    }

    /**
     * Creates a new sorted {@link Pagination}.
     *
     * @param pageNumber zero-based pageNumber index, must not be negative.
     * @param pageSize the pageSize of the pageNumber to be returned, must be greater than 0.
     * @param sortBy the sorting value of the pagination, must not be null.
     */
    public static Pagination of(int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
        AssertUtils.assertPositive(pageNumber, ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'pageNumber' must not be less than one!");
        AssertUtils.assertPositive(pageSize, ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'pageSize' must not be less than one!");
        AssertUtils.assertNotNull(ErrorType.INVALID_REQUEST_PARAMETER, "Parameter 'sortBy' must not be null!", sortBy);

        return new Pagination(pageNumber, pageSize, sortBy, direction);
    }
}