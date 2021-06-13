package com.stockmarket;

import com.stockmarket.dataobj.ObjectProvider;
import com.stockmarket.dataobj.Order;
import com.stockmarket.dataobj.OrderQueues;
import com.stockmarket.dataobj.Transaction;
import com.stockmarket.utils.Type;

import java.util.*;

public class TradeFacilitator {

    private final Map<String, OrderQueues> orderQueueMap;
    private final List<Transaction> transactionList;

    public TradeFacilitator(List<Order> orders) {

        orderQueueMap = ObjectProvider.getInstance().getOrderQueueMap();
        transactionList = ObjectProvider.getInstance().getTransactionList();
        addOrders(orders);
    }

    /**
     * Facilitates the trade
     * @return - list of transactions based on orders
     */
    public List<Transaction> trade()
    {
        //addOrders(orders);
        executeOrders();
        return transactionList;
    }

    /**
     * Adds orders to stock based map of priority queues
     * @param orders
     */
    private void addOrders(List<Order> orders){

        if (orders == null || orders.isEmpty()) {
            return;
        }

        for (Order order : orders) {
            if (order == null || order.getStock()==null) {
                continue;
            }
            PriorityQueue<Order> orderQueue = null;
            OrderQueues orderQueues = orderQueueMap.get(order.getStock());
            if(orderQueues == null)
            {
                orderQueues = new OrderQueues();
                orderQueueMap.put(order.getStock(), orderQueues);
            }
            if (order.getType() == Type.BUY) {

                orderQueue = orderQueues.getBuyQueue();
            } else if (order.getType() == Type.SELL) {

                orderQueue = orderQueues.getSellQueue();
            }

            //if order is not already added.
            if (!orderQueue.contains(order)) {
                orderQueue.add(order);

            }
        }

    }

    /**
     * Based on Map data,
     */
    private void executeOrders(){
        // process buy orders
        for(Map.Entry<String , OrderQueues> entry : orderQueueMap.entrySet()){
            OrderQueues orderQueues = entry.getValue();
            String stock = entry.getKey();
            PriorityQueue<Order> buyOrderQueue = orderQueues.getBuyQueue();
            
            if (buyOrderQueue == null || buyOrderQueue.isEmpty()) {
                return ;
            }

            PriorityQueue<Order> sellOrderQueue = orderQueues.getSellQueue();
            if (sellOrderQueue == null || sellOrderQueue.isEmpty()) {
                return ;
            }

            Iterator<Order> iterator = buyOrderQueue.iterator();
            while (iterator.hasNext()) {
                Order buy = iterator.next();
                if (buy.getQty() > 0) {

                    //System.out.println("BUY order - id - " + buy.getOrderId() + " price - " + buy.getPrice() + " time " + buy.getTradetime());
                    for (Order sell : sellOrderQueue) {
                        //System.out.println("Sell order - id - " + sell.getOrderId() + " price - " + sell.getPrice() + " time " + sell.getTradetime());
                        if (sell.getQty() > 0 && buy.getPrice().compareTo(sell.getPrice()) >= 0) {
                            //System.out.println("If matched order - id - " + sell.getOrderId() + " price - " + sell.getPrice() + " time " + sell.getTradetime());

                            int qty = 0;
                            if (sell.getQty() > buy.getQty()) {
                                qty = buy.getQty();
                                sell.setQty(sell.getQty() - buy.getQty());
                                buy.setQty(0);
                            } else {
                                qty = sell.getQty();
                                buy.setQty(buy.getQty() - sell.getQty());
                                sell.setQty(0);
                            }

                            // record it in order entry
                            Transaction entry1 = new Transaction(sell.getOrderId(), buy.getOrderId(), sell.getPrice(), qty);
                            //System.out.println("Transaction = : " + entry1.toString());
                            transactionList.add(entry1);
                        }
                    }
                }
            }
        }



    }

}
