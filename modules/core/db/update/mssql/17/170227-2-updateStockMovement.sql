-- update EKOMERP_STOCK_MOVEMENT set LOCATION_ID = <default_value> where LOCATION_ID is null ;
alter table EKOMERP_STOCK_MOVEMENT alter column LOCATION_ID uniqueidentifier not null ;
update EKOMERP_STOCK_MOVEMENT set STOCK_MOVEMENT_TYPE = 1 where STOCK_MOVEMENT_TYPE is null ;
alter table EKOMERP_STOCK_MOVEMENT alter column STOCK_MOVEMENT_TYPE integer not null ;
