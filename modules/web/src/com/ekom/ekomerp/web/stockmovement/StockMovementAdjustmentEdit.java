package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.List;

public class StockMovementAdjustmentEdit extends AbstractEditor<StockMovement> {
    @Inject
    private CollectionDatasource<StockMovementLine, UUID> stockMovementLineDs;
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionDatasource<Location, UUID> locationsDs;
    @Inject
    private CollectionDatasource<Location, UUID> locationsFilteredDs;
    @Inject
    private OptionsGroup stockMovementTypeOptionsGroup;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private Metadata metadata;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private CollectionDatasource<Product, UUID> productsDs;
    @Inject
    private Datasource<StockMovement> stockMovementDs;
    @Inject
    private LookupField locationLookupField;
    @Inject
    private Table<StockMovementLine> stockMovementLineTable;

    @Override
    public void init(Map<String, Object> params) {

        if (isUserAdmin()) {
            locationLookupField.setOptionsDatasource(locationsDs);
        } else locationLookupField.setOptionsDatasource(locationsFilteredDs);

        stockMovementLineTable.addGeneratedColumn("product", entity -> {
            LookupPickerField productLookUpPickerField = componentsFactory.createComponent(LookupPickerField.class);
            productLookUpPickerField.setOptionsDatasource(productsDs);
            productLookUpPickerField.setDatasource(stockMovementLineTable.getItemDatasource(entity), "product");
            productLookUpPickerField.addLookupAction();
            productLookUpPickerField.setWidth("100%");
            return productLookUpPickerField;
        });


        stockMovementLineDs.addItemPropertyChangeListener(e -> {
            StockMovementLine line = stockMovementLineDs.getItem();
            if (line!=null) {
                List<Inventory> inventory = findInventoryByProductAndLocation(line.getProduct(), line.getStockMovement().getLocation());
                if (e.getProperty() == "product") {
                    if (inventory.isEmpty()) {
                        line.setQuantityBefore(0.0);
                    } else
                        line.setQuantityBefore(inventory.get(0).getQuantity());

                    line.setQuantityAfter(line.getQuantity() + line.getQuantityBefore());
                } else if (e.getProperty() == "quantity") {
                    line.setQuantityAfter(line.getQuantity() + line.getQuantityBefore());
                } else if (e.getProperty() == "quantityAfter") {
                    line.setQuantity(line.getQuantityAfter() - line.getQuantityBefore());
                }
                stockMovementLineTable.repaint();
            }
        });

        locationLookupField.addValueChangeListener(e -> {
            Collection<StockMovementLine> lines = stockMovementLineDs.getItems();
            for (StockMovementLine line : lines) {
                List<Inventory> inventory = findInventoryByProductAndLocation(line.getProduct(), line.getStockMovement().getLocation());
                if (inventory.isEmpty()) {
                    line.setQuantityBefore(0.0);
                } else
                    line.setQuantityBefore(inventory.get(0).getQuantity());
                line.setQuantityAfter(line.getQuantity() + line.getQuantityBefore());
            }

            stockMovementLineTable.repaint();
        });

    }



    @Override
    protected boolean preCommit() {
        getItem().setStockMovementType(StockmovementTypeEnum.adjustment);
        setNumberField();
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



    

    public void onCreateButtonClick() {
        if(getItem().getLocation()!=null) {
            Collection<StockMovementLine> lines = stockMovementLineDs.getItems();
            boolean hasEmptyLine = false;
            if (lines != null) {
                for (StockMovementLine line : lines) {
                    if (line.getProduct() == null) {
                        hasEmptyLine = true;
                        break;
                    }
                }
                if (hasEmptyLine == false) {
                    StockMovementLine line = metadata.create(StockMovementLine.class);
                    line.setStockMovement(getItem());
                    line.setQuantity(-1.0);
                    stockMovementLineDs.addItem(line);

                }
            } else {
                StockMovementLine line = metadata.create(StockMovementLine.class);
                line.setStockMovement(getItem());
                line.setQuantity(-1.0);
                stockMovementLineDs.addItem(line);

            }
        }else{
            showMessageDialog("Внимание", "Заполните поле Место хранения!", MessageType.WARNING);
        }

    }
}