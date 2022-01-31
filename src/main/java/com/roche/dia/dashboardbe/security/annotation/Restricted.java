package com.roche.dia.dashboardbe.security.annotation;

import java.lang.annotation.*;

/**
 * This annotation represents that the annotated field is restricted.
 * If current user is not eligible for both country and touchpoint, field value will be masked.
 *
 * @author orkun.gedik
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Restricted {


}
