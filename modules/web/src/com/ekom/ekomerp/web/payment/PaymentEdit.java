package com.ekom.ekomerp.web.payment;

import com.ekom.ekomerp.entity.InvoiceTypeEnum;
import com.ekom.ekomerp.entity.PaymentTypeEnum;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.ekom.ekomerp.entity.Payment;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.data.Datasource;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

public class PaymentEdit extends AbstractEditor<Payment> {

    @Inject
    private FieldGroup paymentFieldGroup;

    @Override
    protected boolean preCommit() {
        if (getItem().getInvoice().getType().equals(InvoiceTypeEnum.in)){
            getItem().setPaymentType(PaymentTypeEnum.Inbound);
        }else if(getItem().getInvoice().getType().equals(InvoiceTypeEnum.out)){
            getItem().setPaymentType(PaymentTypeEnum.Outbound);
        }
        return super.preCommit();
    }

    @Inject
    private Datasource<Payment> paymentDs;

    public void select(Component source) {
        close(Window.COMMIT_ACTION_ID);
    }

    public void cancel(Component source) {
        close(Window.CLOSE_ACTION_ID);
    }

    public Payment getPayment(){return paymentDs.getItem();}

}