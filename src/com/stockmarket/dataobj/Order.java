package com.stockmarket.dataobj;

import com.stockmarket.utils.Type;

import java.math.BigDecimal;
import java.time.LocalTime;

public  class Order {
    private LocalTime tradetime;
    private String stock;
    private BigDecimal price;
    private int qty;
    private Type type;
    private String orderId;

    public void setType(Type type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalTime getTradetime() {
        return tradetime;
    }

    public void setTradetime(LocalTime tradetime) {
        this.tradetime = tradetime;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }



    public Type getType() {
        return type;
    }





    public BigDecimal getPrice() {
        return price;
    }


}
