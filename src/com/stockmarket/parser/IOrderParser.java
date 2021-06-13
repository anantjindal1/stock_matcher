package com.stockmarket.parser;

import com.stockmarket.order.Order;

import java.io.FileNotFoundException;

public interface IOrderParser {
    Order getNextOrder();
    void initialiseFileReader(String filePath) throws FileNotFoundException;
}
