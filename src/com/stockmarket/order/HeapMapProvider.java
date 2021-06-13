package com.stockmarket.order;

import java.util.HashMap;
import java.util.Map;

/**
 * class to provide singleton objects for map of pending queues.
 */
public final class HeapMapProvider {
    private static HeapMapProvider instance;
    private final Map<String, OrderHeaps> orderHeapMap;

    private HeapMapProvider() {
        orderHeapMap = new HashMap<>();
    }

    public Map<String, OrderHeaps> getOrderHeapMap() {
        return orderHeapMap;
    }

    public static HeapMapProvider getInstance() {
        if (instance == null) {
            instance = new HeapMapProvider();
        }
        return instance;
    }
}
