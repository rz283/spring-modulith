package com.example.springmodulith.inventory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface InventoryRepository extends CrudRepository<Inventory,Long> {

    Optional<Inventory> getInventoryByName(String name);

    List<Inventory> getInventoryByNameIn(List<String> names);
}
