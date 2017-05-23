package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.Notification;
import java.util.*;

public class StockMovementAdjustmentEdit extends AbstractEditor<StockMovement> {
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
    @Inject
    private OptionsGroup stockMovementTypeOptionsGroup;
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Override
    public void init(Map<String, Object> params) {
        if(isUserAdmin()){
            locationField.setOptionsDatasource(locationsDs);
        }else locationField.setOptionsDatasource(locationsFilteredDs);
        super.init(params);
    }

    @Override
    protected boolean preCommit() {
        getItem().setStockMovementType(StockmovementTypeEnum.adjustment);
        setNumberField();
 //       setBeforeAndAfterQuantity();
        return super.preCommit();
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

    public void setBeforeAndAfterQuantity(){
        Collection<StockMovementLine> lines = getItem().getStockMovementLine();
        for (StockMovementLine line:lines) {
            if(findInventoryByProductAndLocation(line.getProduct(),line.getStockMovement().getLocation())==null){
                line.setQuantityBefore(0.0);
            }else{
                line.setQuantityBefore(findInventoryByProductAndLocation(line.getProduct(),line.getStockMovement().getLocation()).get(0).getQuantity());
            }

            if(line.getStockMovement().getStockMovementType().getId()==1){
                line.setQuantityAfter(line.getQuantityBefore()+line.getQuantity());
            }else if (line.getStockMovement().getStockMovementType().getId()==2){
                line.setQuantityAfter(line.getQuantityBefore()-line.getQuantity());
            }
        }
    }

    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("StockAdjustmentNumber");
    }

    private void setNumberField(){
        if (getItem().getNumber().equals("Новый")){
            String number = "КС-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
            getItem().setNumber(number);
        }
    }
}