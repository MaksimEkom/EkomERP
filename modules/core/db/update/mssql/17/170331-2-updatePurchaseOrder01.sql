alter table EKOMERP_PURCHASE_ORDER add constraint FK_EKOMERP_PURCHASE_ORDER_PAYMENT_CONDITION foreign key (PAYMENT_CONDITION_ID) references EKOMERP_PAYMENT_CONDITION(ID);
create index IDX_EKOMERP_PURCHASE_ORDER_PAYMENT_CONDITION on EKOMERP_PURCHASE_ORDER (PAYMENT_CONDITION_ID);
