alter table EKOMERP_MANUFACTURING_ORDER add STATE integer ^
update EKOMERP_MANUFACTURING_ORDER set STATE = 1 where STATE is null ;
alter table EKOMERP_MANUFACTURING_ORDER alter column STATE integer not null ;
