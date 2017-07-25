package com.ekom.ekomerp.service;

import com.ekom.ekomerp.entity.*;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Sergey on 19.07.2017.
 */
@Component(InvoiceWorker.NAME)
public class InvoiceWorker {
    public static final String NAME = "ekomerp_InvoiceWorker";

    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    protected Persistence persistence;

    /**
     * Создание Счета из Заказа на закупку
     *@param purchaseOrder Заказ на закупку
     */
    public void createInvoiceFromPurcheseOrder(PurchaseOrder purchaseOrder){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            Invoice invoice = new Invoice();
            invoice.setNumber(getInvoiceNumber());
            invoice.setDate(new Date(System.currentTimeMillis()));
            invoice.setPartner(purchaseOrder.getVendor());
            invoice.setState(InvoiceStateEnum.open);
            invoice.setType(InvoiceTypeEnum.in);
            invoice.setOrigin(purchaseOrder.getNumber());
            invoice.setAmountUntaxed(purchaseOrder.getAmountUntaxed());
            invoice.setAmountTax(purchaseOrder.getAmountTax());
            invoice.setAmountTotal(purchaseOrder.getAmountWithTax());
            invoice.setInvoiceFile(purchaseOrder.getInvoiceFile());
            em.persist(invoice);
            tx.commit();
            Set<PurchaseOrderLine> purchaseOrderLines = purchaseOrder.getPurchaseOrderLine();
            for (PurchaseOrderLine purchaseOrderLine : purchaseOrderLines) {
                createInvoiceLineFromPurcheseOrderLine(purchaseOrderLine,invoice);
            }

        }
    }
    /**
     * Создание Строки Счета из Строки Заказа на закупку
     * @param purchaseOrderLine Строка Заказа на закупку
     * @param invoice Счет
     */
    public void createInvoiceLineFromPurcheseOrderLine(PurchaseOrderLine purchaseOrderLine, Invoice invoice){
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            InvoiceLine invoiceLine = new InvoiceLine();
            invoiceLine.setProduct(purchaseOrderLine.getProduct());
            invoiceLine.setQuantity(purchaseOrderLine.getQuantity());
            invoiceLine.setPrice(purchaseOrderLine.getPrice());
            invoiceLine.setSubtotal(purchaseOrderLine.getSubtotal());
            invoiceLine.setTax(purchaseOrderLine.getTax());
            invoiceLine.setTotal(purchaseOrderLine.getTotal());
            invoiceLine.setInvoice(invoice);
            em.persist(invoiceLine);
            tx.commit();
        }
    }
    /**
     * Генерация номера счета
     * @return номер счета
     */
    private String getInvoiceNumber(){
            String number = "СЧ-";
            long longNumber=getNextValue();
            for (int i = (6-(int)(Math.log10(longNumber)+1)); i>0;i--){
                number+="0";
            }
            number+=longNumber;
        return number;
    }
    /**
     * Получение значения последовательности "PurchaseOrderNumber"
     * @return следующее значение последовательности "PurchaseOrderNumber"
     */
    private long getNextValue() {
        return uniqueNumbersService.getNextNumber("PurchaseOrderNumber");
    }
}
