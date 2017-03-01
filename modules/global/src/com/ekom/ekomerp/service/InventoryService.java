package com.ekom.ekomerp.service;


import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.Location;
import com.ekom.ekomerp.entity.Product;

import java.util.UUID;

public interface InventoryService {
    String NAME = "ekomerp_InventoryService";

    public void insertInventoryLine(Product product, Location location, double quantity);
    public Inventory findInventoryLine(Product product, Location location);
    public void increaseInventoryLine(Inventory inventoryLine, double quantity);
    public void reduceInventoryLine(Inventory inventoryLine, double quantity);
    public void reorderInventoryLine(Inventory inventoryLine, double oldQuantity, double newQuantity);

}