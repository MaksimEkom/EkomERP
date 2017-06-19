-- update EKOMERP_CALCULATION set PRODUCT_ID = <default_value> where PRODUCT_ID is null ;
alter table EKOMERP_CALCULATION alter column PRODUCT_ID uniqueidentifier not null ;
