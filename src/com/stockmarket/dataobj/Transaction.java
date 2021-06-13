package com.stockmarket.dataobj;

import java.math.BigDecimal;

public class Transaction {
    String sellerId;
    String buyerId;

    public Transaction(String sellerId, String buyerId, BigDecimal price, int qty) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.qty = qty;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public BigDecimal getPrice() {
        return price;
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

    BigDecimal price;
    int qty;

}
