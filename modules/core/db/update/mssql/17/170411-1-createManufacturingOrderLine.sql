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
    MANUFACTURING_ORDER_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);
