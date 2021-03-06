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