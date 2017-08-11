package com.ekom.ekomerp.web.doororder;

import com.ekom.ekomerp.entity.*;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.service.GaugingService;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.core.global.PersistenceHelper;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Persistence;
import javax.transaction.Status;
import java.util.List;

public class DoorOrderEdit extends AbstractEditor<DoorOrder> {

 @Inject
    private Persistence persistence;

    @Inject
    private GaugingService gaugingService;
    @Inject
    private DataManager dataManager;

    public List<Gauging> findOrderNumber(DoorOrder doorOrder){
        LoadContext loadContext = LoadContext.create(Gauging.class).setQuery(LoadContext
                .createQuery("select g from ekomerp$Gauging g where g.doorOrderNumber = :number ")
                .setParameter("number",doorOrder.getNumber()));

        return dataManager.loadList(loadContext);
    }

    public void onButtonClick() {

        if (!PersistenceHelper.isNew(getItem())) {
            if (findOrderNumber(getItem()).isEmpty()) {
                gaugingService.createGaugingFromDoorOrder(getItem());
                showNotification("Бланк заказа создан!", NotificationType.HUMANIZED);
                getItem().setStatus(DoorOrderStatus.measurement);
                commit();
            } else {
                showNotification("Бланк заказа создан!", NotificationType.HUMANIZED);
            }
        }else {
            showNotification("Бланк заказа создан!", NotificationType.HUMANIZED);
        }


    }



}