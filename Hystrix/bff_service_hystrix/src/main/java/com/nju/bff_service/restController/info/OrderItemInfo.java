package com.nju.bff_service.restController.info;

public class OrderItemInfo {
    String pet_name;
    int pet_num;
    Double price;

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public void setPet_num(int pet_num) {
        this.pet_num = pet_num;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPet_name() {

        return pet_name;
    }

    public int getPet_num() {
        return pet_num;
    }

    public Double getPrice() {
        return price;
    }
}
