package com.roche.dia.dashboardbe.utils;

import lombok.experimental.UtilityClass;

import java.util.LinkedHashMap;

/**
 * orkun.gedik
 */
@UtilityClass
public class MapUtils {

    public <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k1, V v1) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);

        return map;
    }

    public <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k1, V v1, K k2, V v2) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);

        return map;
    }


    public <K, V> LinkedHashMap<K, V> linkedHashMapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);

        return map;
    }
}
