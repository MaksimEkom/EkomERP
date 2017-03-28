package com.ekom.ekomerp.web.purchaseorder;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.PurchaseOrder;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

public class PurchaseOrderEdit extends AbstractEditor<PurchaseOrder> {

    @Named("fieldGroup.number")
    private TextField numberField;
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private LookupPickerField vendorField;

    @Override
    public void init(Map<String, Object> params) {
        vendorField.removeAction(vendorField.getOpenAction());
        super.init(params);
    }

}