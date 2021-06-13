package com.stockmarket.orderexecutor;

import com.stockmarket.order.Order;
import com.stockmarket.order.OrderHeaps;
import com.stockmarket.transaction.Transaction;

import java.util.List;

public interface IOrderExecutor {
    List<Transaction> executorOrder(Order order, OrderHeaps stockHeaps);
}
