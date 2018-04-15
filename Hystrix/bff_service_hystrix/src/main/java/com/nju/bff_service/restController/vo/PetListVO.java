package com.nju.bff_service.restController.vo;

import com.nju.bff_service.restController.info.PetItem;

import java.util.List;

public class PetListVO {
    List<PetItem> petItems;

    public void setPetItems(List<PetItem> petItems) {
        this.petItems = petItems;
    }

    public List<PetItem> getPetItems() {

        return petItems;
    }
}
