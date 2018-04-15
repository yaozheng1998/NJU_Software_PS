package com.nju.bff_service.restController.info;

public class PetItem {
    String pet_name;
    int pet_store;
    double pet_price;
    int category_id;
    int id;

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public void setPet_store(int pet_store) {
        this.pet_store = pet_store;
    }

    public void setPet_price(double pet_price) {
        this.pet_price = pet_price;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPet_name() {

        return pet_name;
    }

    public int getPet_store() {
        return pet_store;
    }

    public double getPet_price() {
        return pet_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getId() {
        return id;
    }
}
