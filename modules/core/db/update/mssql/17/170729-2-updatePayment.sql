alter table EKOMERP_PAYMENT add AMOUNT decimal(19, 2) ^
update EKOMERP_PAYMENT set AMOUNT = 0 where AMOUNT is null ;
alter table EKOMERP_PAYMENT alter column AMOUNT decimal(19, 2) not null ;
