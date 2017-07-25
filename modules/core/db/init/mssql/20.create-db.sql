-- begin EKOMERP_PRODUCTalter table EKOMERP_PRODUCT add constraint FK_EKOMERP_PRODUCT_UNIT foreign key (UNIT_ID) references EKOMERP_UNIT(ID)^
alter table EKOMERP_PRODUCT add constraint FK_EKOMERP_PRODUCT_CATEGORY foreign key (CATEGORY_ID) references EKOMERP_CATEGORY(ID)^
alter table EKOMERP_PRODUCT add constraint FK_EKOMERP_PRODUCT_IMAGE foreign key (IMAGE_ID) references SYS_FILE(ID)^
create index IDX_EKOMERP_PRODUCT_UNIT on EKOMERP_PRODUCT (UNIT_ID)^
create index IDX_EKOMERP_PRODUCT_CATEGORY on EKOMERP_PRODUCT (CATEGORY_ID)^
create index IDX_EKOMERP_PRODUCT_IMAGE on EKOMERP_PRODUCT (IMAGE_ID)^
-- end EKOMERP_PRODUCT
-- begin EKOMERP_CATEGORY
alter table EKOMERP_CATEGORY add constraint FK_EKOMERP_CATEGORY_PARENT_CATEGORY foreign key (PARENT_CATEGORY_ID) references EKOMERP_CATEGORY(ID)^
create index IDX_EKOMERP_CATEGORY_PARENT_CATEGORY on EKOMERP_CATEGORY (PARENT_CATEGORY_ID)^
-- end EKOMERP_CATEGORY
-- begin EKOMERP_LABORIOUSNESS
alter table EKOMERP_LABORIOUSNESS add constraint FK_EKOMERP_LABORIOUSNESS_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_LABORIOUSNESS add constraint FK_EKOMERP_LABORIOUSNESS_OPERATION foreign key (OPERATION_ID) references EKOMERP_OPERATION(ID)^
create index IDX_EKOMERP_LABORIOUSNESS_OPERATION on EKOMERP_LABORIOUSNESS (OPERATION_ID)^
create index IDX_EKOMERP_LABORIOUSNESS_PRODUCT on EKOMERP_LABORIOUSNESS (PRODUCT_ID)^
-- end EKOMERP_LABORIOUSNESS
-- begin EKOMERP_CONSUMPTION
alter table EKOMERP_CONSUMPTION add constraint FK_EKOMERP_CONSUMPTION_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_CONSUMPTION add constraint FK_EKOMERP_CONSUMPTION_CONSUMABLE_PRODUCT foreign key (CONSUMABLE_PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
create index IDX_EKOMERP_CONSUMPTION_CONSUMABLE_PRODUCT on EKOMERP_CONSUMPTION (CONSUMABLE_PRODUCT_ID)^
create index IDX_EKOMERP_CONSUMPTION_PRODUCT on EKOMERP_CONSUMPTION (PRODUCT_ID)^
-- end EKOMERP_CONSUMPTION
-- begin EKOMERP_LOCATION
alter table EKOMERP_LOCATION add constraint FK_EKOMERP_LOCATION_STOCKMAN foreign key (STOCKMAN_ID) references SEC_USER(ID)^
create index IDX_EKOMERP_LOCATION_STOCKMAN on EKOMERP_LOCATION (STOCKMAN_ID)^
-- end EKOMERP_LOCATION
-- begin EKOMERP_STOCK_MOVEMENTalter table EKOMERP_STOCK_MOVEMENT add constraint FK_EKOMERP_STOCK_MOVEMENT_LOCATION foreign key (LOCATION_ID) references EKOMERP_LOCATION(ID)^
create index IDX_EKOMERP_STOCK_MOVEMENT_LOCATION on EKOMERP_STOCK_MOVEMENT (LOCATION_ID)^
-- end EKOMERP_STOCK_MOVEMENT
-- begin EKOMERP_STOCK_MOVEMENT_LINEalter table EKOMERP_STOCK_MOVEMENT_LINE add constraint FK_EKOMERP_STOCK_MOVEMENT_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_STOCK_MOVEMENT_LINE add constraint FK_EKOMERP_STOCK_MOVEMENT_LINE_STOCK_MOVEMENT foreign key (STOCK_MOVEMENT_ID) references EKOMERP_STOCK_MOVEMENT(ID)^
create index IDX_EKOMERP_STOCK_MOVEMENT_LINE_PRODUCT on EKOMERP_STOCK_MOVEMENT_LINE (PRODUCT_ID)^
create index IDX_EKOMERP_STOCK_MOVEMENT_LINE_STOCK_MOVEMENT on EKOMERP_STOCK_MOVEMENT_LINE (STOCK_MOVEMENT_ID)^
-- end EKOMERP_STOCK_MOVEMENT_LINE

-- begin EKOMERP_INVENTORY
alter table EKOMERP_INVENTORY add constraint FK_EKOMERP_INVENTORY_LOCATION foreign key (LOCATION_ID) references EKOMERP_LOCATION(ID)^
alter table EKOMERP_INVENTORY add constraint FK_EKOMERP_INVENTORY_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
create index IDX_EKOMERP_INVENTORY_LOCATION on EKOMERP_INVENTORY (LOCATION_ID)^
create index IDX_EKOMERP_INVENTORY_PRODUCT on EKOMERP_INVENTORY (PRODUCT_ID)^
-- end EKOMERP_INVENTORY
-- begin EKOMERP_PARTNERalter table EKOMERP_PARTNER add constraint FK_EKOMERP_PARTNER_IMAGE foreign key (IMAGE_ID) references SYS_FILE(ID)^
alter table EKOMERP_PARTNER add constraint FK_EKOMERP_PARTNER_PARENT foreign key (PARENT_ID) references EKOMERP_PARTNER(ID)^
alter table EKOMERP_PARTNER add constraint FK_EKOMERP_PARTNER_REGION foreign key (REGION_ID) references EKOMERP_REGION(ID)^
alter table EKOMERP_PARTNER add constraint FK_EKOMERP_PARTNER_COUNTRY foreign key (COUNTRY_ID) references EKOMERP_COUNTRY(ID)^
create unique index IDX_EKOMERP_PARTNER_UNIQ_NAME on EKOMERP_PARTNER (NAME, DELETE_TS) ^
create index IDX_EKOMERP_PARTNER_IMAGE on EKOMERP_PARTNER (IMAGE_ID)^
create index IDX_EKOMERP_PARTNER_PARENT on EKOMERP_PARTNER (PARENT_ID)^
create index IDX_EKOMERP_PARTNER_REGION on EKOMERP_PARTNER (REGION_ID)^
create index IDX_EKOMERP_PARTNER_COUNTRY on EKOMERP_PARTNER (COUNTRY_ID)^
-- end EKOMERP_PARTNER
-- begin EKOMERP_REGION
alter table EKOMERP_REGION add constraint FK_EKOMERP_REGION_COUNTRY foreign key (COUNTRY_ID) references EKOMERP_COUNTRY(ID)^
create index IDX_EKOMERP_REGION_COUNTRY on EKOMERP_REGION (COUNTRY_ID)^
-- end EKOMERP_REGION
-- begin EKOMERP_PURCHASE_ORDER
alter table EKOMERP_PURCHASE_ORDER add constraint FK_EKOMERP_PURCHASE_ORDER_VENDOR foreign key (VENDOR_ID) references EKOMERP_PARTNER(ID)^
alter table EKOMERP_PURCHASE_ORDER add constraint FK_EKOMERP_PURCHASE_ORDER_PAYMENT_CONDITION foreign key (PAYMENT_CONDITION_ID) references EKOMERP_PAYMENT_CONDITION(ID)^
alter table EKOMERP_PURCHASE_ORDER add constraint FK_EKOMERP_PURCHASE_ORDER_INVOICE_FILE foreign key (INVOICE_FILE_ID) references SYS_FILE(ID)^
create index IDX_EKOMERP_PURCHASE_ORDER_VENDOR on EKOMERP_PURCHASE_ORDER (VENDOR_ID)^
create index IDX_EKOMERP_PURCHASE_ORDER_PAYMENT_CONDITION on EKOMERP_PURCHASE_ORDER (PAYMENT_CONDITION_ID)^
create index IDX_EKOMERP_PURCHASE_ORDER_INVOICE_FILE on EKOMERP_PURCHASE_ORDER (INVOICE_FILE_ID)^
-- end EKOMERP_PURCHASE_ORDER
-- begin EKOMERP_PURCHASE_ORDER_LINE
alter table EKOMERP_PURCHASE_ORDER_LINE add constraint FK_EKOMERP_PURCHASE_ORDER_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_PURCHASE_ORDER_LINE add constraint FK_EKOMERP_PURCHASE_ORDER_LINE_PURCHASE_ORDER foreign key (PURCHASE_ORDER_ID) references EKOMERP_PURCHASE_ORDER(ID)^
create index IDX_EKOMERP_PURCHASE_ORDER_LINE_PRODUCT on EKOMERP_PURCHASE_ORDER_LINE (PRODUCT_ID)^
create index IDX_EKOMERP_PURCHASE_ORDER_LINE_PURCHASE_ORDER on EKOMERP_PURCHASE_ORDER_LINE (PURCHASE_ORDER_ID)^
-- end EKOMERP_PURCHASE_ORDER_LINE
-- begin EKOMERP_MANUFACTURING_ORDER
alter table EKOMERP_MANUFACTURING_ORDER add constraint FK_EKOMERP_MANUFACTURING_ORDER_CUSTOMER foreign key (CUSTOMER_ID) references EKOMERP_PARTNER(ID)^
create index IDX_EKOMERP_MANUFACTURING_ORDER_CUSTOMER on EKOMERP_MANUFACTURING_ORDER (CUSTOMER_ID)^
-- end EKOMERP_MANUFACTURING_ORDER
-- begin EKOMERP_MANUFACTURING_ORDER_LINE
alter table EKOMERP_MANUFACTURING_ORDER_LINE add constraint FK_EKOMERP_MANUFACTURING_ORDER_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_MANUFACTURING_ORDER_LINE add constraint FK_EKOMERP_MANUFACTURING_ORDER_LINE_MANUFACTURING_ORDER foreign key (MANUFACTURING_ORDER_ID) references EKOMERP_MANUFACTURING_ORDER(ID)^
create index IDX_EKOMERP_MANUFACTURING_ORDER_LINE_PRODUCT on EKOMERP_MANUFACTURING_ORDER_LINE (PRODUCT_ID)^
create index IDX_EKOMERP_MANUFACTURING_ORDER_LINE_MANUFACTURING_ORDER on EKOMERP_MANUFACTURING_ORDER_LINE (MANUFACTURING_ORDER_ID)^
-- end EKOMERP_MANUFACTURING_ORDER_LINE
-- begin EKOMERP_INVOICE_LINE
alter table EKOMERP_INVOICE_LINE add constraint FK_EKOMERP_INVOICE_LINE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_INVOICE_LINE add constraint FK_EKOMERP_INVOICE_LINE_INVOICE foreign key (INVOICE_ID) references EKOMERP_INVOICE(ID)^
create index IDX_EKOMERP_INVOICE_LINE_PRODUCT on EKOMERP_INVOICE_LINE (PRODUCT_ID)^
create index IDX_EKOMERP_INVOICE_LINE_INVOICE on EKOMERP_INVOICE_LINE (INVOICE_ID)^
-- end EKOMERP_INVOICE_LINE
-- begin EKOMERP_INVOICE
alter table EKOMERP_INVOICE add constraint FK_EKOMERP_INVOICE_PARTNER foreign key (PARTNER_ID) references EKOMERP_PARTNER(ID)^
alter table EKOMERP_INVOICE add constraint FK_EKOMERP_INVOICE_INVOICE_FILE foreign key (INVOICE_FILE_ID) references SYS_FILE(ID)^
create index IDX_EKOMERP_INVOICE_PARTNER on EKOMERP_INVOICE (PARTNER_ID)^
create index IDX_EKOMERP_INVOICE_INVOICE_FILE on EKOMERP_INVOICE (INVOICE_FILE_ID)^
-- end EKOMERP_INVOICE
-- begin EKOMERP_PRODUCT_PRICE
alter table EKOMERP_PRODUCT_PRICE add constraint FK_EKOMERP_PRODUCT_PRICE_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
alter table EKOMERP_PRODUCT_PRICE add constraint FK_EKOMERP_PRODUCT_PRICE_PRICING_TYPE foreign key (PRICING_TYPE_ID) references EKOMERP_PRICING_TYPE(ID)^
create index IDX_EKOMERP_PRODUCT_PRICE_PRODUCT on EKOMERP_PRODUCT_PRICE (PRODUCT_ID)^
create index IDX_EKOMERP_PRODUCT_PRICE_PRICING_TYPE on EKOMERP_PRODUCT_PRICE (PRICING_TYPE_ID)^
-- end EKOMERP_PRODUCT_PRICE
-- begin EKOMERP_CALCULATION
alter table EKOMERP_CALCULATION add constraint FK_EKOMERP_CALCULATION_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID)^
create index IDX_EKOMERP_CALCULATION_PRODUCT on EKOMERP_CALCULATION (PRODUCT_ID)^
-- end EKOMERP_CALCULATION
-- begin EKOMERP_CALCULATION_MATERIAL_LINE
alter table EKOMERP_CALCULATION_MATERIAL_LINE add constraint FK_EKOMERP_CALCULATION_MATERIAL_LINE_CALCULATION foreign key (CALCULATION_ID) references EKOMERP_CALCULATION(ID)^
alter table EKOMERP_CALCULATION_MATERIAL_LINE add constraint FK_EKOMERP_CALCULATION_MATERIAL_LINE_MATERIAL foreign key (MATERIAL_ID) references EKOMERP_PRODUCT(ID)^
create index IDX_EKOMERP_CALCULATION_MATERIAL_LINE_CALCULATION on EKOMERP_CALCULATION_MATERIAL_LINE (CALCULATION_ID)^
create index IDX_EKOMERP_CALCULATION_MATERIAL_LINE_MATERIAL on EKOMERP_CALCULATION_MATERIAL_LINE (MATERIAL_ID)^
-- end EKOMERP_CALCULATION_MATERIAL_LINE
-- begin EKOMERP_CALCULATION_LABORIOUSNESS_LINE
alter table EKOMERP_CALCULATION_LABORIOUSNESS_LINE add constraint FK_EKOMERP_CALCULATION_LABORIOUSNESS_LINE_CALCULATION foreign key (CALCULATION_ID) references EKOMERP_CALCULATION(ID)^
alter table EKOMERP_CALCULATION_LABORIOUSNESS_LINE add constraint FK_EKOMERP_CALCULATION_LABORIOUSNESS_LINE_OPERATION foreign key (OPERATION_ID) references EKOMERP_OPERATION(ID)^
create index IDX_EKOMERP_CALCULATION_LABORIOUSNESS_LINE_CALCULATION on EKOMERP_CALCULATION_LABORIOUSNESS_LINE (CALCULATION_ID)^
create index IDX_EKOMERP_CALCULATION_LABORIOUSNESS_LINE_OPERATION on EKOMERP_CALCULATION_LABORIOUSNESS_LINE (OPERATION_ID)^
-- end EKOMERP_CALCULATION_LABORIOUSNESS_LINE
