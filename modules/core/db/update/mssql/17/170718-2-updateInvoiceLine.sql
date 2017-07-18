alter table EKOMERP_INVOICE_LINE drop column QUANTITY ;
alter table EKOMERP_INVOICE_LINE add QUANTITY decimal(19, 4) ^
update EKOMERP_INVOICE_LINE set QUANTITY = 0 where QUANTITY is null ;
alter table EKOMERP_INVOICE_LINE alter column QUANTITY decimal(19, 4) not null ;
alter table EKOMERP_INVOICE_LINE drop column PRICE ;
alter table EKOMERP_INVOICE_LINE add PRICE decimal(19, 2) ^
update EKOMERP_INVOICE_LINE set PRICE = 0 where PRICE is null ;
alter table EKOMERP_INVOICE_LINE alter column PRICE decimal(19, 2) not null ;
alter table EKOMERP_INVOICE_LINE drop column SUBTOTAL ;
alter table EKOMERP_INVOICE_LINE add SUBTOTAL decimal(19, 2) ;
alter table EKOMERP_INVOICE_LINE drop column TAX ;
alter table EKOMERP_INVOICE_LINE add TAX decimal(19, 2) ;
alter table EKOMERP_INVOICE_LINE drop column TOTAL ;
alter table EKOMERP_INVOICE_LINE add TOTAL decimal(19, 2) ;
