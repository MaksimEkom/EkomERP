package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.StockMovement;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class StockMovementAdjustmentBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<StockMovement, UUID> stockMovementsDs;
    @Inject
    private CollectionDatasource<StockMovement, UUID> stockMovementsFilteredDs;

    @Inject
    private UserSession userSession;
    @Inject
    private DataGrid<StockMovement> stockMovementsFilteredDataGrid;
    @Named("stockMovementsFilteredDataGrid.create")
    private Action stockMovementsFilteredDataGridCreate;
    @Named("stockMovementsFilteredDataGrid.edit")
    private Action stockMovementsFilteredDataGridEdit;
    @Inject
    private Filter stockMovementsFilter;

    @Override
  public void init(Map<String, Object> params) {
       if(isUserAdmin()){
           stockMovementsFilteredDataGrid.setDatasource(stockMovementsDs);
           stockMovementsFilter.setDatasource(stockMovementsDs);
       }else {
           stockMovementsFilteredDataGrid.setDatasource(stockMovementsFilteredDs);
           stockMovementsFilter.setDatasource(stockMovementsFilteredDs);
       }

        ((CreateAction) stockMovementsFilteredDataGridCreate).setWindowId("ekomerp$StockMovementAdjustment.edit");
        ((EditAction) stockMovementsFilteredDataGridEdit).setWindowId("ekomerp$StockMovementAdjustment.edit");


        super.init(params);
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