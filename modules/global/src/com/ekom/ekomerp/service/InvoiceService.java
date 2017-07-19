package com.ekom.ekomerp.service;


import com.ekom.ekomerp.entity.PurchaseOrder;

public interface InvoiceService {
    String NAME = "ekomerp_InvoiceService";

    public void createInvoiceFromPurcheseOrder(PurchaseOrder purchaseOrder);
}