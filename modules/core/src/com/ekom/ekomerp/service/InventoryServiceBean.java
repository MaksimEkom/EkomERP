package com.ekom.ekomerp.service;

import com.ekom.ekomerp.entity.Inventory;
import com.ekom.ekomerp.entity.Location;
import com.ekom.ekomerp.entity.Product;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;


@Service(InventoryService.NAME)
public class InventoryServiceBean implements InventoryService {
    @Inject
    protected InventoryWorker inventoryWorker;


    public void insertInventoryLine(Product product, Location location, double quantity){
        inventoryWorker.insertInventoryLine(product,location,quantity);
    }

    public Inventory findInventoryLine(UUID productId, UUID locationId){

        return inventoryWorker.findInventoryLine(productId,locationId);
    }
    public void increaseInventoryLine(Inventory inventoryLine, double quantity){
        inventoryWorker.increaseInventoryLine(inventoryLine,quantity);
    }
    public void reduceInventoryLine(Inventory inventoryLine, double quantity){
        inventoryWorker.reduceInventoryLine(inventoryLine,quantity);
    }
    public void reorderInventoryLine(Inventory inventoryLine, double oldQuantity, double newQuantity){
        inventoryWorker.reorderInventoryLine(inventoryLine,oldQuantity,newQuantity);
    }
}
