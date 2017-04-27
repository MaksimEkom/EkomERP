package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.StockMovement;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.security.entity.UserRole;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class StockMovementBrowse extends AbstractLookup {

    @Inject
    private GroupDatasource<StockMovement, UUID> stockMovementsDs;
    @Inject
    private GroupDatasource<StockMovement, UUID> stockMovementsFilteredDs;
    @Inject
    private GroupTable<StockMovement> stockMovementsTable;
    @Inject
    private UserSession userSession;

//    @Override
//    public void init(Map<String, Object> params) {
//        if(isUserAdmin()){
//            stockMovementsTable.setDatasource(stockMovementsDs);
//        }else stockMovementsTable.setDatasource(stockMovementsFilteredDs);
//        super.init(params);
//    }
//
//    public boolean isUserAdmin(){
//        Collection <UserRole> userRoles = userSession.getUser().getUserRoles();
//        boolean isAdmin = false;
//        for (UserRole userRole:userRoles){
//            if(userRole.getRole().getType().getId()==10){
//                isAdmin = true;
//                break;
//            }
//        }
//        return isAdmin;
//    }
}