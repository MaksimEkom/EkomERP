alter table EKOMERP_INVOICE drop column AMOUNT_UNTAXED ;
alter table EKOMERP_INVOICE add AMOUNT_UNTAXED decimal(19, 2) ;
alter table EKOMERP_INVOICE drop column AMOUNT_TAX ;
alter table EKOMERP_INVOICE add AMOUNT_TAX decimal(19, 2) ;
alter table EKOMERP_INVOICE drop column AMOUNT_TOTAL ;
alter table EKOMERP_INVOICE add AMOUNT_TOTAL decimal(19, 2) ;
