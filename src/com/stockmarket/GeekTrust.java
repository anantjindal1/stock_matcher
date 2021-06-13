package com.stockmarket;

import com.stockmarket.dataobj.Order;
import com.stockmarket.dataobj.Transaction;
import com.stockmarket.utils.InputParser;

import java.util.List;

public class GeekTrust {

    // Simple 1 thread solution -
    // 1. parse the input to make order queues
    // implement buy and sell order classes (which keep sell sorted by low to high, buy sorted by time (oldest on top)
    //

    public static void main(String[] args) {
        if(args[0]==null) {
            System.out.println();
            return;
        }

        List<Order> orderList = InputParser.readInputFromFile(args[0]);

        TradeFacilitator facilitator = new TradeFacilitator(orderList);
        List<Transaction> transactionList =  facilitator.trade();

        for(Transaction transaction :transactionList) {
            String transactionLog = ""+transaction.getBuyerId()+" "+transaction.getPrice()+" "+ transaction.getQty()+" "+ transaction.getSellerId();
            System.out.println(transactionLog);
        }

    }
}

