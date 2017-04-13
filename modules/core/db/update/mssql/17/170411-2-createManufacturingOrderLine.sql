alter table EKOMERP_MANUFACTURING_ORDER_LINE add constraint FK_EKOMERP_MANUFACTURING_ORDER_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID);
alter table EKOMERP_MANUFACTURING_ORDER_LINE add constraint FK_EKOMERP_MANUFACTURING_ORDER_LINE_MANUFACTURING_ORDER foreign key (MANUFACTURING_ORDER_ID) references EKOMERP_MANUFACTURING_ORDER(ID);
create index IDX_EKOMERP_MANUFACTURING_ORDER_LINE_PRODUCT on EKOMERP_MANUFACTURING_ORDER_LINE (PRODUCT_ID);
create index IDX_EKOMERP_MANUFACTURING_ORDER_LINE_MANUFACTURING_ORDER on EKOMERP_MANUFACTURING_ORDER_LINE (MANUFACTURING_ORDER_ID);