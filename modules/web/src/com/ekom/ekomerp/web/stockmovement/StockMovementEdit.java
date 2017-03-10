package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.Notification;
import java.util.*;

public class StockMovementEdit extends AbstractEditor<StockMovement> {
    @Inject
    private CollectionDatasource<StockMovementLine, UUID> stockMovementLineDs;
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;
    @Named("fieldGroup.location")
    private LookupPickerField locationField;
    @Inject
    private CollectionDatasource<Location, UUID> locationsDs;
    @Inject
    private CollectionDatasource<Location, UUID> locationsFilteredDs;

    @Override
    public void init(Map<String, Object> params) {
        if(isUserAdmin()){
            locationField.setOptionsDatasource(locationsDs);
        }else locationField.setOptionsDatasource(locationsFilteredDs);

        stockMovementLineDs.addItemPropertyChangeListener(e
                -> checkNegativeInventoryAfterUpdate(e.getItem(),e.getProperty(),e.getValue(),e.getPrevValue()));
        stockMovementLineDs.addCollectionChangeListener(e -> {
            if(e.getOperation()== CollectionDatasource.Operation.ADD){
                showNotification("Недостаточно 1");
            }else if(e.getOperation()== CollectionDatasource.Operation.REMOVE){
                showNotification("Недостаточно 2");
            }
        });

        super.init(params);
    }

   private void checkNegativeInventoryAfterUpdate(StockMovementLine stockMovementLine, String property, Object value, Object oldValue) {
       showNotification(property);
       List<Inventory> inventory = findInventoryByProductAndLocation(stockMovementLine.getProduct(),stockMovementLine.getStockMovement().getLocation());
       if(property.equals("quantity")){
           showNotification("dfdghjhgfds");
           switch(stockMovementLine.getStockMovement().getStockMovementType()){
                case in:
                    if ((inventory.get(0).getQuantity()-((double)oldValue-(double)value))<0){
                        showNotification("Недостаточно "+stockMovementLine.getProduct().toString()
                                +" на складе "+stockMovementLine.getStockMovement().getLocation());
                    }
                    break;
                case out:
                    if ((inventory.get(0).getQuantity()+((double)oldValue-(double)value))<0){
                        showNotification("Недостаточно "+stockMovementLine.getProduct().toString()
                                +" на складе "+stockMovementLine.getStockMovement().getLocation());
                    }
                    break;
            }
       }
//        StockMovement stockMovement = stockMovementLineDs.getItem().getStockMovement();
//       Inventory inventory = new Inventory();
//           Set<StockMovementLine> stockMovementLines = stockMovement.getStockMovementLine();
//        for (StockMovementLine line : stockMovementLines) {
//            inventory=findInventoryByProductAndLocation(line.getProduct(),stockMovement.getLocation()).get(0);
//            switch(stockMovement.getStockMovementType()){
//                case in:
//                    showNotification("Недостаточно "+line.getProduct().toString()+" на складе "+line.getStockMovement().getLocation());
//                    break;
//                case out:
//                    showNotification("Недостаточно "+line.getProduct().toString()+" на складе "+line.getStockMovement().getLocation());
//                    break;
//            }
//   }
//
//

   }

    private List<Inventory> findInventoryByProductAndLocation(Product product, Location location){
        LoadContext loadContext = LoadContext.create(Inventory.class).setQuery(LoadContext
                .createQuery("select i from ekomerp$Inventory i where i.product.id = :productId and i.location.id = :locationId")
                    .setParameter("productId",product.getId())
                    .setParameter("locationId",location.getId()))
                .setView("inventory-view");
        return dataManager.loadList(loadContext);

    }

    public boolean isUserAdmin(){
        Collection <UserRole> userRoles = userSession.getUser().getUserRoles();
        boolean isAdmin = false;
        for (UserRole userRole:userRoles){
            if(userRole.getRole().getType().getId()==10){
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }
}