alter table EKOMERP_INVOICE drop column PAID ;
alter table EKOMERP_INVOICE add PAID decimal(19, 2) ;
alter table EKOMERP_INVOICE drop column DEBT ;
alter table EKOMERP_INVOICE add DEBT decimal(19, 2) ;
