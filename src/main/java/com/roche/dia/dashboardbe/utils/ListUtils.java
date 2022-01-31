package com.roche.dia.dashboardbe.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class for {@link List}.
 *
 * @author orkun.gedik
 */
@UtilityClass
public class ListUtils {

    /**
     * Returns true if given list is null or empty
     *
     * @param args {@link List}
     * @param <I>  any type
     * @return boolean
     */
    public static <I> boolean notEmpty(List<I> args) {
        return !isEmpty(args);
    }

    /**
     * Returns true if given list is null or empty
     *
     * @param args {@link List}
     * @param <I>  any type
     * @return boolean
     */
    public static <I> boolean isEmpty(List<I> args) {
        return args == null || args.isEmpty();
    }

    /**
     * Returns an empty list if the given array is null
     *
     * @param args array of given type
     * @param <I>  any type
     * @return {@link List}
     */
    public static <I> List<I> emptyIfNull(I[] args) {
        if (args == null) {
            return List.of();
        } else {
            return List.of(args);
        }
    }

    /**
     * Returns an empty list if the given list is null
     *
     * @param list {@link List}
     * @param <I>  any type
     * @return {@link List}
     */
    public static <I> List<I> emptyIfNull(List<I> list) {
        if (list == null) {
            return List.of();
        } else {
            return list;
        }
    }

    /**
     * Returns if the given list contains the result of the given resolver. Returns false if any of them is null.
     *
     * @param list {@link List}
     * @param <I>  any type
     * @return {@link List}
     */
    public static <I> boolean contains(List<I> list, Supplier<I> resolver) {
        if (list != null) {
            Optional optional = ObjectUtils.getNestedObjectOfNullable(resolver);

            if (optional.isPresent()) {
                return list.contains(optional.get());
            }
        }
        return false;
    }

    /**
     * Calls the {@link #contains(List, Supplier)} and returns the other way around.
     *
     * @param list {@link List}
     * @param <I>  any type
     * @return {@link List}
     */
    public static <I> boolean notContains(List<I> list, Supplier<I> resolver) {
       return !contains(list, resolver);
    }

}
