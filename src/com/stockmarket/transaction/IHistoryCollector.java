package com.stockmarket.transaction;

import java.util.List;

public interface IHistoryCollector {
    public void logTransaction(List<Transaction> transactions) ;
    public List<Transaction> getHistory();

    }
