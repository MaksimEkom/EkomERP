update EKOMERP_INVOICE_LINE set PRICE = 0 where PRICE is null ;
alter table EKOMERP_INVOICE_LINE alter column PRICE decimal(19, 2) not null ;
