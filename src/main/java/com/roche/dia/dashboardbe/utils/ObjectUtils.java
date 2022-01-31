package com.roche.dia.dashboardbe.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author orkun.gedik
 */
@UtilityClass
public final class ObjectUtils {

    /**
     * Returns corresponding result of the given resolver. Returns null if NPE is thrown.
     *
     * @param resolver {@link Supplier}
     * @param <T> any type
     * @return {@link Optional}
     */
    public static <T> Optional<T> getNestedObjectOfNullable(Supplier<T> resolver) {
        try {
            T result = resolver.get();
            return Optional.ofNullable(result);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    /**
     * Checks the given array of objects one by one. If any of them is null, then returns true.
     * Otherwise, returns false.
     *
     * @param objects
     * @return boolean
     */
    public static boolean anyNull(Object... objects) {
        for (Object o : objects) {
            if (Objects.isNull(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the all given arguments in {@code args} array equal to {@code value}.
     *
     * @param value base value of the equality check
     * @param args the values to be compared with {@code value}
     * @return boolean
     */
    public static <I extends Object> boolean equals(I value, I... args) {
        for (I arg : args) {
            if (!Objects.equals(value, arg)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the any given arguments in {@code args} array equal to {@code value}.
     *
     * @param value base value of the equality check
     * @param args the values to be compared with {@code value}
     * @return boolean
     */
    public static <I extends Object> boolean anyEquals(I value, I... args) {
        for (I arg : args) {
            if (Objects.equals(value, arg)) {
                return true;
            }
        }
        return false;
    }
}
