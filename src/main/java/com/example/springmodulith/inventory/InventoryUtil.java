package com.example.springmodulith.inventory;

import com.example.springmodulith.inventory.exposed.InventoryDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryUtil {
    public InventoryDto mapInventoryDto(Inventory inventory) {
        return new InventoryDto(inventory.getId(), inventory.getName(), inventory.getDescription(), inventory.getPrice());
    }
}
