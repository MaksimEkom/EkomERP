-- begin EKOMERP_PRODUCT
create table EKOMERP_PRODUCT (
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
-- begin EKOMERP_STOCK_MOVEMENT
create table EKOMERP_STOCK_MOVEMENT (
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
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_STOCK_MOVEMENT
-- begin EKOMERP_STOCK_MOVEMENT_LINE
create table EKOMERP_STOCK_MOVEMENT_LINE (
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
    QUANTITY_BEFORE double precision,
    QUANTITY_AFTER double precision,
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
-- begin EKOMERP_PARTNER
create table EKOMERP_PARTNER (
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
    REGION varchar(255),
    COUNTRY varchar(255),
    ZIP varchar(255),
    ADDRESSES varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_PARTNER
-- begin EKOMERP_ADDRESS
create table EKOMERP_ADDRESS (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    TYPE_ integer not null,
    POSITION_ varchar(255),
    EMAIL varchar(255),
    PHONE varchar(255),
    MOBILE varchar(255),
    NOTES varchar(max),
    STREET varchar(255),
    CITY varchar(255),
    REGION varchar(255),
    ZIP varchar(255),
    COUNTRY varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end EKOMERP_ADDRESS
