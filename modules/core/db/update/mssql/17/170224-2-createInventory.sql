alter table EKOMERP_INVENTORY add constraint FK_EKOMERP_INVENTORY_LOCATION foreign key (LOCATION_ID) references EKOMERP_LOCATION(ID);
alter table EKOMERP_INVENTORY add constraint FK_EKOMERP_INVENTORY_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID);
create index IDX_EKOMERP_INVENTORY_LOCATION on EKOMERP_INVENTORY (LOCATION_ID);
create index IDX_EKOMERP_INVENTORY_PRODUCT on EKOMERP_INVENTORY (PRODUCT_ID);
