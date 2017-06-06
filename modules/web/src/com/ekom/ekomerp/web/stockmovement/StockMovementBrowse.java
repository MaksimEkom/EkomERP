package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.StockMovement;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.Filter;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class StockMovementBrowse extends AbstractLookup {

    @Inject
    private CollectionDatasource<StockMovement, UUID> stockMovementsDs;
    @Inject
    private CollectionDatasource<StockMovement, UUID> stockMovementsFilteredDs;
    @Inject
    private UserSession userSession;
    @Inject
    private DataGrid<StockMovement> stockMovementsFilteredDataGrid;
    @Inject
    private Filter stockMovementsFilteredFilter;

    @Override
    public void init(Map<String, Object> params) {
        if(isUserAdmin()){
            stockMovementsFilteredDataGrid.setDatasource(stockMovementsDs);
            stockMovementsFilteredFilter.setDatasource(stockMovementsDs);
        }else {
            stockMovementsFilteredDataGrid.setDatasource(stockMovementsFilteredDs);
            stockMovementsFilteredFilter.setDatasource(stockMovementsFilteredDs);
        }
        super.init(params);
    }

    public boolean isUserAdmin(){
        Collection <UserRole> userRoles = userSession.getCurrentOrSubstitutedUser().getUserRoles();
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