package com.ekom.ekomerp.web.partner;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Partner;
import com.haulmont.cuba.gui.components.GroupBoxLayout;
import com.haulmont.cuba.gui.components.OptionsGroup;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class PartnercontactEdit extends AbstractEditor<Partner> {
    @Inject
    private GroupBoxLayout addressGroupBox;
    @Inject
    private OptionsGroup partnerOptionsGroup;
    @Named("partnerFieldGroup.position")
    private TextField positionField;


    @Override
    public void init(Map<String, Object> params) {
        partnerOptionsGroup.addValueChangeListener(e -> {
            if(e.getValue().toString() == "contact"){
                addressGroupBox.setVisible(false);
                positionField.setVisible(true);
            }else {
                addressGroupBox.setVisible(true);
                positionField.setVisible(false);
            }
        });
        super.init(params);
    }
}