package com.stockmarket.transaction;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Transaction {
    private String sellerId;
    private String buyerId;

    private BigDecimal price;
    private int qty;
    private LocalTime requestTime;

    public Transaction(String sellerId, String buyerId, BigDecimal price, int qty, LocalTime requestTime) {
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.price = price;
        this.qty = qty;
        this.requestTime = requestTime;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "sellerId='" + sellerId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", requestTime=" + requestTime +
                '}';
    }
}
