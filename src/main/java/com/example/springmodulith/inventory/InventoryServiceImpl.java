package com.example.springmodulith.inventory;

import com.example.springmodulith.exception.ModulithException;
import com.example.springmodulith.inventory.exposed.InventoryDto;
import com.example.springmodulith.inventory.exposed.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryDto fetchInventoryByName(String name) {
        return inventoryRepository.getInventoryByName(name)
                .map(InventoryUtil::mapInventoryDto)
                .orElseThrow(() -> new ModulithException("Could not find inventory by name: " + name));
    }

    @Override
    public List<InventoryDto> fetchAllInName(List<String> inventoryNames) {
        return inventoryRepository.getInventoryByNameIn(inventoryNames)
                .stream()
                .map(InventoryUtil::mapInventoryDto)
                .toList();
    }
}
