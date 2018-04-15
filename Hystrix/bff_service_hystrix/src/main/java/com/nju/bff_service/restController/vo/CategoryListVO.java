package com.nju.bff_service.restController.vo;

import com.nju.bff_service.restController.info.CategoryItem;

import java.util.List;

public class CategoryListVO {
    List<CategoryItem> categoryInfos;

    public void setCategoryInfos(List<CategoryItem> categoryInfos) {
        this.categoryInfos = categoryInfos;
    }

    public List<CategoryItem> getCategoryInfos() {

        return categoryInfos;
    }
}
