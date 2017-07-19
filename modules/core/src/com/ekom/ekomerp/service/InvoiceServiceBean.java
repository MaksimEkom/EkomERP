package com.ekom.ekomerp.service;

import com.ekom.ekomerp.entity.PurchaseOrder;
import com.haulmont.cuba.core.Persistence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service(InvoiceService.NAME)
public class InvoiceServiceBean implements InvoiceService {
    @Inject
    protected InvoiceWorker invoiceWorker;
    @Inject
    protected Persistence persistence;

    @Transactional
    @Override
    public void createInvoiceFromPurcheseOrder(PurchaseOrder purchaseOrder){
        invoiceWorker.createInvoiceFromPurcheseOrder(purchaseOrder);
    }

}