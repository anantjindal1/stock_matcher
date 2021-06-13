package com.stockmarket.utils;

import com.stockmarket.dataobj.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputParser {
    public static List<Order> readInputFromFile(String filePath){
        File file = new File(filePath);

        List<Order> orderList = new ArrayList<>();
        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                Order order = createOrder(scan.nextLine());
                //make the order queue
                orderList.add(order);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public static Order createOrder(String item)
    {
        //System.out.println(item);
        String[] items = item.trim().split("\\s+");
        String orderId,time,stock,type,price,qty;
        orderId = items[0];
        time = items[1];
        stock = items[2];
        type = items[3];
        price = items[4];
        qty = items[5];
        //System.out.println("id : "+orderId + " qty : "+qty);
        Order order = new Order();
        order.setOrderId(orderId);
        order.setTradetime(LocalTime.parse(time));
        order.setStock(stock);
        order.setType(type.equalsIgnoreCase("buy")? Type.BUY:Type.SELL);
        order.setPrice(new BigDecimal(price));
        order.setQty(Integer.parseInt(qty));

        //it should have 6 things;

        return order;
    }


}
