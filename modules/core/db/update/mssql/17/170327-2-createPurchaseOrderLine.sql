alter table EKOMERP_PURCHASE_ORDER_LINE add constraint FK_EKOMERP_PURCHASE_ORDER_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID);
alter table EKOMERP_PURCHASE_ORDER_LINE add constraint FK_EKOMERP_PURCHASE_ORDER_LINE_PURCHASE_ORDER foreign key (PURCHASE_ORDER_ID) references EKOMERP_PURCHASE_ORDER(ID);
create index IDX_EKOMERP_PURCHASE_ORDER_LINE_PURCHASE_ORDER on EKOMERP_PURCHASE_ORDER_LINE (PURCHASE_ORDER_ID);
create index IDX_EKOMERP_PURCHASE_ORDER_LINE_PRODUCT on EKOMERP_PURCHASE_ORDER_LINE (PRODUCT_ID);
