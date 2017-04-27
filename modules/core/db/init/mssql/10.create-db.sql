-- begin EKOMERP_PRODUCTcreate table EKOMERP_PRODUCT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    UNIT_ID uniqueidentifier,
    FULL_NAME varchar(255),
    DOC_NAME varchar(255),
    DESCRIPTION varchar(max),
    HEIGHT integer,
    WIDTH integer,
    DEPTH integer,
    CATEGORY_ID uniqueidentifier,
    ACTIVE tinyint,
    ANALOG varchar(255),
    WEIGHT double precision,
    FOR_SALE tinyint,
    FOR_PURCHASE tinyint,
    IMAGE_ID uniqueidentifier,
    NOTES varchar(max),
    PURCHASE_PRICE double precision,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PRODUCT
-- begin EKOMERP_CATEGORY
create table EKOMERP_CATEGORY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255) not null,
    PARENT_CATEGORY_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_CATEGORY
-- begin EKOMERP_UNIT
create table EKOMERP_UNIT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    CODE integer,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_UNIT
-- begin EKOMERP_OPERATION
create table EKOMERP_OPERATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255) not null,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_OPERATION
-- begin EKOMERP_LABORIOUSNESS
create table EKOMERP_LABORIOUSNESS (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier,
    OPERATION_ID uniqueidentifier,
    VALUE_ double precision,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_LABORIOUSNESS
-- begin EKOMERP_CONSUMPTION
create table EKOMERP_CONSUMPTION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier,
    CONSUMABLE_PRODUCT_ID uniqueidentifier,
    QUANTITY double precision not null,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_CONSUMPTION
-- begin EKOMERP_LOCATION
create table EKOMERP_LOCATION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255),
    STOCKMAN_ID uniqueidentifier,
    ADDRESS varchar(max),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_LOCATION
-- begin EKOMERP_STOCK_MOVEMENTcreate table EKOMERP_STOCK_MOVEMENT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CONSIGNMENT varchar(255),
    DATE_ datetime2 not null,
    LOCATION_ID uniqueidentifier not null,
    STOCK_MOVEMENT_TYPE integer not null,
    NOTES varchar(max),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_STOCK_MOVEMENT
-- begin EKOMERP_STOCK_MOVEMENT_LINEcreate table EKOMERP_STOCK_MOVEMENT_LINE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier not null,
    QUANTITY double precision not null,
    STOCK_MOVEMENT_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_STOCK_MOVEMENT_LINE

-- begin EKOMERP_INVENTORY
create table EKOMERP_INVENTORY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    LOCATION_ID uniqueidentifier not null,
    PRODUCT_ID uniqueidentifier not null,
    QUANTITY double precision,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_INVENTORY
-- begin EKOMERP_PARTNERcreate table EKOMERP_PARTNER (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    PARTNER_TYPE integer,
    CUSTOMER tinyint,
    VENDOR tinyint,
    IMAGE_ID uniqueidentifier,
    EMAIL varchar(255),
    WEBSITE varchar(255),
    ACTIVE tinyint,
    POSITION_ varchar(255),
    PHONE varchar(255),
    MOBILE varchar(255),
    FAX varchar(255),
    PARENT_ID uniqueidentifier,
    STREET varchar(255),
    CITY varchar(255),
    REGION_ID uniqueidentifier,
    COUNTRY_ID uniqueidentifier,
    ZIP varchar(255),
    ADDRESS_TYPE integer,
    NOTES varchar(max),
    UNP varchar(255),
    FULL_NAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PARTNER
-- begin EKOMERP_COUNTRY
create table EKOMERP_COUNTRY (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_COUNTRY
-- begin EKOMERP_REGION
create table EKOMERP_REGION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255),
    NAME varchar(255) not null,
    COUNTRY_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_REGION
-- begin EKOMERP_PURCHASE_ORDER
create table EKOMERP_PURCHASE_ORDER (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    VENDOR_ID uniqueidentifier not null,
    STATE varchar(50),
    NOTES varchar(max),
    AMOUNT_UNTAXED double precision,
    AMOUNT_TAX double precision,
    AMOUNT_WITH_TAX double precision,
    DATE_ datetime2 not null,
    NUMBER_ varchar(255) not null,
    DELIVERY_DATE datetime2,
    PAYMENT_CONDITION_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PURCHASE_ORDER
-- begin EKOMERP_PURCHASE_ORDER_LINE
create table EKOMERP_PURCHASE_ORDER_LINE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier not null,
    QUANTITY double precision not null,
    PRICE double precision not null,
    SUBTOTAL double precision,
    TAX double precision,
    TOTAL double precision,
    PURCHASE_ORDER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PURCHASE_ORDER_LINE
-- begin EKOMERP_PAYMENT_CONDITION
create table EKOMERP_PAYMENT_CONDITION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CONDITION_ varchar(400),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PAYMENT_CONDITION
-- begin EKOMERP_MANUFACTURING_ORDER
create table EKOMERP_MANUFACTURING_ORDER (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    DATE_ datetime2 not null,
    CUSTOMER_ID uniqueidentifier not null,
    DATE_OF_MANUFACTURE datetime2,
    NOTES varchar(max),
    STATE integer not null,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_MANUFACTURING_ORDER
-- begin EKOMERP_MANUFACTURING_ORDER_LINE
create table EKOMERP_MANUFACTURING_ORDER_LINE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier not null,
    QUANTITY double precision not null,
    QUANTITY_PRODUCED double precision,
    MANUFACTURING_ORDER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_MANUFACTURING_ORDER_LINE
-- begin EKOMERP_INVOICE
create table EKOMERP_INVOICE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255) not null,
    DATE_ datetime2 not null,
    DUE_DATE datetime2,
    PARTNER_ID uniqueidentifier not null,
    STATE varchar(50),
    TYPE_ varchar(50),
    ORIGIN varchar(255),
    AMOUNT_UNTAXED double precision,
    AMOUNT_TAX double precision,
    AMOUNT_TOTAL double precision,
    NOTES varchar(max),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_INVOICE
-- begin EKOMERP_INVOICE_LINE
create table EKOMERP_INVOICE_LINE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PRODUCT_ID uniqueidentifier not null,
    QUANTITY double precision not null,
    PRICE double precision not null,
    SUBTOTAL double precision,
    TAX double precision,
    TOTAL double precision,
    INVOICE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_INVOICE_LINE
