package com.stockmarket.dataobj;

import java.util.Comparator;
import java.util.PriorityQueue;

public class OrderQueues {
    PriorityQueue<Order> buyQueue;
    PriorityQueue<Order> sellQueue;

    public PriorityQueue<Order> getBuyQueue() {
        return buyQueue;
    }

    public PriorityQueue<Order> getSellQueue() {
        return sellQueue;
    }

    public OrderQueues(){
        buyQueue = new PriorityQueue<Order>(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                //same order
                if (o1.getOrderId().equals(o2.getOrderId())) return 0;
                //first comparison on time in buy queue (First come first serve)
                int timeCompare = o1.getTradetime().compareTo(o2.getTradetime());
                return timeCompare==0?o1.getOrderId().compareTo(o2.getOrderId()):timeCompare;

            }
        });

        sellQueue = new PriorityQueue<Order>(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                //same order
                if (o1.getOrderId().equals(o2.getOrderId())) return 0;
                int timeCompare = o1.getTradetime().compareTo(o2.getTradetime());
                //first take the lowest selling price
                //int priceCompare = o1.getPrice().compareTo(o2.getPrice());
               // return priceCompare==0?o1.getTradetime().compareTo(o2.getTradetime()):priceCompare;
                return timeCompare==0?o1.getPrice().compareTo(o2.getPrice()):timeCompare;
            }
        });
    }


}
