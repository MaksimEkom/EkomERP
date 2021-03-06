alter table EKOMERP_CONSUMPTION add constraint FK_EKOMERP_CONSUMPTION_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID);
alter table EKOMERP_CONSUMPTION add constraint FK_EKOMERP_CONSUMPTION_CONSUMABLE_PRODUCT foreign key (CONSUMABLE_PRODUCT_ID) references EKOMERP_PRODUCT(ID);
create index IDX_EKOMERP_CONSUMPTION_CONSUMABLE_PRODUCT on EKOMERP_CONSUMPTION (CONSUMABLE_PRODUCT_ID);
create index IDX_EKOMERP_CONSUMPTION_PRODUCT on EKOMERP_CONSUMPTION (PRODUCT_ID);
