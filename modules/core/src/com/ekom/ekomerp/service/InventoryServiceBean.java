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


    public void addInventoryLine(Product product, Location location, double quantity){
        inventoryWorker.addInventoryLine(product,location,quantity);
    }

    public Inventory findInventoryLine(UUID productId, UUID locationId){

        return inventoryWorker.findInventoryLine(productId,locationId);
    }
    public void incomeInventoryLine(Inventory inventoryLine, double quantity){
        inventoryWorker.incomeInventoryLine(inventoryLine,quantity);
    }
    public void expenceInventoryLine(Inventory inventoryLine, double quantity){
        inventoryWorker.expenceInventoryLine(inventoryLine,quantity);
    }

}
