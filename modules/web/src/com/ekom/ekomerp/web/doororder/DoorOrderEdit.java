package com.ekom.ekomerp.web.doororder;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.DoorOrder;
import com.ekom.ekomerp.entity.Gauging;
import com.ekom.ekomerp.service.GaugingService;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.PickerField;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class DoorOrderEdit extends AbstractEditor<DoorOrder> {

 @Inject
    private Persistence persistence;

    @Inject
    private GaugingService gaugingService;

    public void onButtonClick() {
     gaugingService.createGaugingFromDoorOrder(getItem());
    }
}