package com.ekom.ekomerp.web.stockmovement;

import com.ekom.ekomerp.entity.StockMovement;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
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
    private GroupDatasource<StockMovement, UUID> stockMovementsDs;
    @Inject
    private GroupDatasource<StockMovement, UUID> stockMovementsFilteredDs;
    @Inject
    private GroupTable<StockMovement> stockMovementsTable;
    @Inject
    private UserSession userSession;
    @Inject
    private Button createBtn;
    @Named("stockMovementsTable.create")
    private CreateAction stockMovementsTableCreate;
    @Named("stockMovementsTable.edit")
    private EditAction stockMovementsTableEdit;

    @Override
  public void init(Map<String, Object> params) {
       //if(isUserAdmin()){
       //     stockMovementsTable.setDatasource(stockMovementsDs);
       // }else stockMovementsTable.setDatasource(stockMovementsFilteredDs);

       stockMovementsTableCreate.setWindowId("ekomerp$StockMovementAdjustment.edit");
       stockMovementsTableCreate.setOpenType(WindowManager.OpenType.THIS_TAB);
       stockMovementsTableEdit.setWindowId("ekomerp$StockMovementAdjustment.edit");
       stockMovementsTableEdit.setOpenType(WindowManager.OpenType.THIS_TAB);
       super.init(params);
   }
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