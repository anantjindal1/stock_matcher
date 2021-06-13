package com.stockmarket.parser;

import com.stockmarket.logger.Logger;
import com.stockmarket.order.Order;
import com.stockmarket.order.OrderType;
import com.stockmarket.exception.IllegalTradeTypeException;
import com.stockmarket.exception.OrderFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class OrderParser implements IOrderParser {
    private static final Logger log = Logger.getLogger("OrderParser");
    static OrderParser parser;
    BufferedReader fileReader;

    private OrderParser() {
    }

    public static OrderParser getInstance() {
        if (parser == null) {
            parser = new OrderParser();
        }
        return parser;
    }

    public void initialiseFileReader(String filePath) throws FileNotFoundException {
        fileReader = new BufferedReader(new FileReader(filePath));
    }


    public Order getNextOrder() {
        log.info("Fetching next order");
        Order order = null;
        try {
            String currentLine = fileReader.readLine();
            if (currentLine != null) {
                order = createOrder(currentLine);
            }

        } catch (OrderFormatException | IllegalTradeTypeException e) {
            log.debug("1 Row is skipped due to incorrect input format ");
            log.debug("Right format is : <order-id> <time> <stock> <buy/sell> <price> <qty>");
            log.error(e.getMessage());
            return getNextOrder();
        } catch (IOException e) {
            //log.e- fatal error - io exception -
            log.error("IOException occurred during order creation");
            log.error(e.getMessage());
            return null;
        }
        return order;
    }

    private Order createOrder(String item) throws IllegalTradeTypeException, OrderFormatException {
        log.debug("Converting :: " + item);
        if (item == null) {
            throw new OrderFormatException("Empty order");
        }

        String[] items = item.trim().split("\\s+");
        validateOrder(items);

        //0: id, 1:time 2:stock name 3:order type 4:price 5:quantity
        Order order = Order.OrderBuilder.getBuilder(items[0])
                .setRequestTime(LocalTime.parse(items[1]))
                .setStock(items[2])
                .setType(OrderType.valueOf(items[3].toUpperCase()))
                .setPrice(new BigDecimal(items[4]))
                .setQty(Integer.parseInt(items[5]))
                .build();

        log.debug("Order created :: " + order.toString());
        return order;
    }


    private void validateOrder(String[] items) throws IllegalTradeTypeException, OrderFormatException {
        if (items.length != 6) {
            log.error("problem in parsing orders; too few arguments");
            throw new OrderFormatException("Order has insufficient items");
        }
        String order_id = items[0];
        validateType(items[3], order_id);
        validateQuantity(items[5], order_id);
        validatePrice(items[4], order_id);
        validateTime(items[1], order_id);
    }

    private void validateQuantity(String qty, String order_id) throws OrderFormatException {
        try {
            Integer.parseInt(qty);
        } catch (NumberFormatException e) {
            log.error("Wrong number format of price for order id " + order_id);
            log.error(e.getMessage());
            throw new OrderFormatException("Wrong number format of price for order id " + order_id);
        }
    }

    private void validatePrice(String price, String order_id) throws OrderFormatException {

        try {
            new BigDecimal(price);
        } catch (NumberFormatException e) {
            log.error("Wrong number format of price for order id " + order_id);
            log.error(e.getMessage());
            throw new OrderFormatException("Wrong number format of price for order id " + order_id);

        }
    }

    private void validateTime(String time, String order_id) throws OrderFormatException {
        try {
            LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            log.error("Wrong time format for order id " + order_id);
            log.error(e.getMessage());
            throw new OrderFormatException("Wrong time format for order id " + order_id);
        }
    }

    private void validateType(String type, String order_id) throws IllegalTradeTypeException {
        if (OrderType.valueOf(type.toUpperCase()) != OrderType.BUY
                && OrderType.valueOf(type.toUpperCase()) != OrderType.SELL) {
            log.error("Wrong order type - should be either buy of sell for order id " + order_id);
            throw new IllegalTradeTypeException("invalid order type : " + type + " for order id " + order_id);
        }
    }


}
