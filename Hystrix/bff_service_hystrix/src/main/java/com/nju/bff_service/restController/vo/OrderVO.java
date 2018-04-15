package com.nju.bff_service.restController.vo;

import java.util.List;

public class OrderVO {
    int status;
    double price;
    String description;
    String bill;
    List<OrderItemVO> orderItems;

    public void setOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItemVO> getOrderItems() {

        return orderItems;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public int getStatus() {

        return status;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getBill() {
        return bill;
    }
}
