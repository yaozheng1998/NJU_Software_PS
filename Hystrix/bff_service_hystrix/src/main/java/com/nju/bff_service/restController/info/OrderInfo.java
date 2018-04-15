package com.nju.bff_service.restController.info;

import java.util.List;

public class OrderInfo {
    int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    Double price;
    String description;
    int user_id;
    String billInfo;
    List<OrderItemInfo> orderItems;

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setBillInfo(String billInfo) {
        this.billInfo = billInfo;
    }

    public void setOrderItems(List<OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }

    public Double getPrice() {

        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getBillInfo() {
        return billInfo;
    }

    public List<OrderItemInfo> getOrderItems() {
        return orderItems;
    }
}
