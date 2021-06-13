package com.stockmarket.dataobj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ObjectProvider {
    private static ObjectProvider instance;
    private final Map<String, OrderQueues> orderQueueMap;
    private final List<Transaction> transactionList;

    private ObjectProvider(){
        orderQueueMap = new HashMap<>(); //we have to make it singleton (maybe define another class to get the object.
        transactionList = new ArrayList<Transaction>();
    }

    public Map<String,OrderQueues> getOrderQueueMap()
    {
        return orderQueueMap;
    }
    public List<Transaction> getTransactionList(){
        return transactionList;
    }
    public static ObjectProvider getInstance(){
        if(instance==null)
        {
            instance = new ObjectProvider();
        }
        return instance;
    }
}
