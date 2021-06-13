package com.stockmarket.transaction;


import com.stockmarket.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton Transaction History class
 */
public class TransactionHistoryCollector implements IHistoryCollector,ITransactionLogger{
    private final List<Transaction> transactionList;
    private static TransactionHistoryCollector history;
    Logger log = Logger.getLogger("TransactionHistoryCollector");

    private TransactionHistoryCollector() {
        transactionList = new ArrayList<>();
    }

    public static TransactionHistoryCollector getInstance() {
        if (history == null) {
            history = new TransactionHistoryCollector();
        }
        return history;
    }


    public void logTransaction(Transaction transaction) {
        transactionList.add(transaction);
        printTransaction(transaction);
    }

    public void logTransaction(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            logTransaction(transaction);
        }
    }

    public void printTransaction(Transaction transaction) {
        // record it in order entry
        log.debug("Transaction = : " + transaction.toString());
        String transactionLog = "" + transaction.getBuyerId() + " " + transaction.getPrice() + " " + transaction.getQty() + " " + transaction.getSellerId();
        System.out.println(transactionLog);
        log.debug(transactionLog);
    }

    public List<Transaction> getHistory() {
        return Collections.unmodifiableList(transactionList);
    }
}
