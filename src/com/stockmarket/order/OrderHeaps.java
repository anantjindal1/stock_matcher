package com.stockmarket.order;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * OrderQueues holds Buy and sell pending heaps
 */
public class OrderHeaps {
    PriorityQueue<Order> buyMaxHeap;
    PriorityQueue<Order> sellMinHeap;

    public PriorityQueue<Order> getBuyMaxHeap() {
        return buyMaxHeap;
    }

    public PriorityQueue<Order> getSellMinHeap() {
        return sellMinHeap;
    }

    public OrderHeaps(Order order) {
        buyMaxHeap = new PriorityQueue<>(new BuyOrderComparator());
        sellMinHeap = new PriorityQueue<>(new SellOrderComparator());
        assignOrder(order);
    }

    public void assignOrder(Order order) {
        if (order.getType() == OrderType.BUY) {
            buyMaxHeap.add(order);
        } else if (order.getType() == OrderType.SELL) {
            sellMinHeap.add(order);
        }
    }

    public static class BuyOrderComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            //same order
            if (o1.getOrderId().equals(o2.getOrderId())) {
                return 0;
            }
            int priceCompare = o2.getPrice().compareTo(o1.getPrice());
            int timeCompare = o1.getRequestTime().compareTo(o2.getRequestTime());
            return priceCompare != 0 ? priceCompare : timeCompare;
        }
    }

    public static class SellOrderComparator implements Comparator<Order> {

        @Override
        public int compare(Order o1, Order o2) {
            //if same object
            if (o1.getOrderId().equals(o2.getOrderId())) {
                return 0;
            }
            int priceCompare = o1.getPrice().compareTo(o2.getPrice());
            int timeCompare = o1.getRequestTime().compareTo(o2.getRequestTime());
            return priceCompare != 0 ? priceCompare : timeCompare;

        }
    }


}