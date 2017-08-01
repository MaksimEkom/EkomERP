update EKOMERP_PURCHASE_ORDER_LINE set PRICE = 0 where PRICE is null ;
alter table EKOMERP_PURCHASE_ORDER_LINE alter column PRICE decimal(19, 2) not null ;
