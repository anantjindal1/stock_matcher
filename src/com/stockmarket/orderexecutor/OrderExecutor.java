package com.stockmarket.orderexecutor;

import com.stockmarket.logger.Logger;
import com.stockmarket.order.Order;
import com.stockmarket.order.OrderHeaps;
import com.stockmarket.transaction.Transaction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class OrderExecutor implements IOrderExecutor {

    Logger log = Logger.getLogger("OrderExecutor");

    private boolean isTradePossible(Order buyOrder, Order sellOrder) {
        if (buyOrder == null || sellOrder == null) {
            return false;
        }
        return (buyOrder.getQty() > 0
                && sellOrder.getQty() > 0
                && buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0);

    }

    public List<Transaction> executorOrder(Order order, OrderHeaps stockHeaps) {
        log.info("Executing order : "+order.getOrderId());
        List<Transaction> transactionList = new ArrayList<>();
        while (order.getQty() > 0) {
            Order sellOrder = null, buyOrder = null;

            switch (order.getType()) {
                case BUY:
                    if (isTradePossible(order, stockHeaps.getSellMinHeap().peek())) {
                        sellOrder = stockHeaps.getSellMinHeap().poll();
                        buyOrder = order;
                    } else {
                        stockHeaps.getBuyMaxHeap().add(order);
                        return transactionList;
                    }
                    break;
                case SELL:
                    if (isTradePossible(stockHeaps.getBuyMaxHeap().peek(), order)) {
                        sellOrder = order;
                        buyOrder = stockHeaps.getBuyMaxHeap().poll();
                    } else {
                        stockHeaps.getSellMinHeap().add(order);
                        return transactionList;
                    }
                    break;
            }
            if (sellOrder == null || buyOrder == null) {
                return transactionList;
            }
            order = performTransaction(sellOrder, buyOrder, transactionList);

        }
        return transactionList;
    }

    private Order performTransaction(Order sellOrder, Order buyOrder, List<Transaction> transactionList) {
        log.info("Performing transaction between buyer - "+buyOrder.getOrderId() +" and seller - "+ sellOrder.getOrderId());
        Order returnOrder;
        int qty;
        if (sellOrder.getQty() > buyOrder.getQty()) {
            qty = buyOrder.getQty();
            sellOrder.setQty(sellOrder.getQty() - buyOrder.getQty());
            buyOrder.setQty(0);
            returnOrder = sellOrder;
        } else {
            qty = sellOrder.getQty();
            buyOrder.setQty(buyOrder.getQty() - sellOrder.getQty());
            sellOrder.setQty(0);
            returnOrder = buyOrder;
        }

        LocalTime requestTime = sellOrder.getRequestTime().compareTo(buyOrder.getRequestTime()) > 0 ?
                sellOrder.getRequestTime() : buyOrder.getRequestTime();
        transactionList.add(new Transaction(sellOrder.getOrderId(),
                buyOrder.getOrderId(), sellOrder.getPrice(), qty, requestTime));
        return returnOrder;
    }

}
