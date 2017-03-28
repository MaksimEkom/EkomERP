alter table EKOMERP_PURCHASE_ORDER add DATE_ datetime2 ^
update EKOMERP_PURCHASE_ORDER set DATE_ = convert (date, current_timestamp) where DATE_ is null ;
alter table EKOMERP_PURCHASE_ORDER alter column DATE_ datetime2 not null ;
