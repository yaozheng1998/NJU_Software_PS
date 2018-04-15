package com.nju.bff_service.restController.info;

public class CategoryItem {
    String name;
    int id;
    String dept;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {

        return name;
    }

    public int getId() {
        return id;
    }

    public String getDept() {
        return dept;
    }
}
