package com.ekom.ekomerp.service;


import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.Location;
import com.ekom.ekomerp.entity.Product;

import java.util.UUID;

public interface InventoryService {
    String NAME = "ekomerp_InventoryService";

    public void addInventoryLine(Product product, Location location, double quantity);
    public Inventory findInventoryLine(UUID productId, UUID locationId);
    public void incomeInventoryLine(Inventory inventoryLine, double quantity);
    public void expenceInventoryLine(Inventory inventoryLine, double quantity);
}