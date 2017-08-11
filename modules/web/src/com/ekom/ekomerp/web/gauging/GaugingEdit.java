package com.ekom.ekomerp.web.gauging;

import com.ekom.ekomerp.service.DoorOrderService;
import javax.inject.Inject;
import com.ekom.ekomerp.service.GaugingService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Gauging;

import javax.inject.Inject;

public class GaugingEdit extends AbstractEditor<Gauging> {

    @Inject
    private DoorOrderService doorOrderService;
    public void onButtonClick() {

        doorOrderService.createDoorOrderFromGauging(getItem());
         showNotification("Данные успешно сохранены!", NotificationType.HUMANIZED);
    }
}