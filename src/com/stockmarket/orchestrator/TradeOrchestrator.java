package com.stockmarket.orchestrator;

import com.stockmarket.logger.Logger;
import com.stockmarket.order.HeapMapProvider;
import com.stockmarket.order.Order;
import com.stockmarket.order.OrderHeaps;
import com.stockmarket.orderexecutor.IOrderExecutor;
import com.stockmarket.parser.IOrderParser;
import com.stockmarket.transaction.IHistoryCollector;
import com.stockmarket.transaction.Transaction;
import com.stockmarket.transaction.TransactionHistoryCollector;

import java.util.List;
import java.util.Map;

public class TradeOrchestrator {

    private static final Logger log = Logger.getLogger("TradeOrchestrator");
    private final Map<String, OrderHeaps> orderHeapMap;
    private IOrderExecutor executor;
    private IOrderParser parser;
    private IHistoryCollector historyCollector;

    public TradeOrchestrator(IOrderExecutor executor, IOrderParser parser, IHistoryCollector historyCollector) {
        this.parser = parser;
        this.executor = executor;
        this.historyCollector = historyCollector;
        orderHeapMap = HeapMapProvider.getInstance().getOrderHeapMap();
    }

    public void trade() {
        Order order;
        log.info("Initiating trade");
        while ((order = parser.getNextOrder()) != null) {
            if (orderHeapMap.containsKey(order.getStock())) {
                OrderHeaps stockHeaps = orderHeapMap.get(order.getStock());
                List<Transaction> transactionList = executor.executorOrder(order, stockHeaps);
                log.info("Trade executed");
                historyCollector.logTransaction(transactionList);
            } else {
                OrderHeaps stockHeap = new OrderHeaps(order);
                orderHeapMap.put(order.getStock(), stockHeap);
            }
        }
        log.info("Trading complete");
    }


}
