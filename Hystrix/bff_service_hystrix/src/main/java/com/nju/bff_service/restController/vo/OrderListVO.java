package com.nju.bff_service.restController.vo;

import java.util.List;

public class OrderListVO {
    List<OrderVO> orders;

    public void setOrders(List<OrderVO> orders) {
        this.orders = orders;
    }

    public List<OrderVO> getOrders() {

        return orders;
    }
}
