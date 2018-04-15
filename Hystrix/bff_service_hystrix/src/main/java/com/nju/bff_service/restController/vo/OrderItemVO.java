package com.nju.bff_service.restController.vo;

public class OrderItemVO {
    String pet_name;
    int pet_num;
    double single_price;

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public void setPet_num(int pet_num) {
        this.pet_num = pet_num;
    }

    public void setSingle_price(double single_price) {
        this.single_price = single_price;
    }

    public String getPet_name() {

        return pet_name;
    }

    public int getPet_num() {
        return pet_num;
    }

    public double getSingle_price() {
        return single_price;
    }
}
