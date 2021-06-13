package com.stockmarket.order;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Order {
    final private String orderId;
    private String stock;
    private LocalTime requestTime;
    private BigDecimal price;
    private int qty;
    private OrderType orderType;

    public Order(OrderBuilder builder) {
        orderId = builder.orderId;
        stock = builder.stock;
        requestTime = builder.requestTime;
        price = builder.price;
        qty = builder.qty;
        orderType = builder.orderType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", stock='" + stock + '\'' +
                ", requestTime=" + requestTime +
                ", price=" + price +
                ", qty=" + qty +
                ", orderType=" + orderType +
                '}';
    }



    public String getOrderId() {
        return orderId;
    }

    public LocalTime getRequestTime() {
        return requestTime;
    }

    public String getStock() {
        return stock;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }

    public OrderType getType() {
        return orderType;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public static class OrderBuilder{
        final private String orderId;
        private String stock;
        private LocalTime requestTime;
        private BigDecimal price;
        private int qty;
        private OrderType orderType;

        private OrderBuilder(String orderId)
        {
            this.orderId=orderId;
        }
        public static OrderBuilder getBuilder(String orderId)
        {
            return new OrderBuilder(orderId);
        }

        public OrderBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderBuilder setStock(String stock) {
            this.stock = stock;
            return this;
        }

        public OrderBuilder setType(OrderType orderType) {
            this.orderType = orderType;
            return this;
        }
        public OrderBuilder setQty(int qty) {
            this.qty = qty;
            return this;
        }
        public OrderBuilder setRequestTime(LocalTime time) {
            requestTime = time;
            return this;
        }

        public Order build() {
            return new Order(this);

        }
    }


}
