alter table EKOMERP_PURCHASE_ORDER drop column AMOUNT_UNTAXED ;
alter table EKOMERP_PURCHASE_ORDER add AMOUNT_UNTAXED decimal(19, 2) ;
alter table EKOMERP_PURCHASE_ORDER drop column AMOUNT_TAX ;
alter table EKOMERP_PURCHASE_ORDER add AMOUNT_TAX decimal(19, 2) ;
alter table EKOMERP_PURCHASE_ORDER drop column AMOUNT_WITH_TAX ;
alter table EKOMERP_PURCHASE_ORDER add AMOUNT_WITH_TAX decimal(19, 2) ;
