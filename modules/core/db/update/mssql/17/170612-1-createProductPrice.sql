create table EKOMERP_PRODUCT_PRICE (
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
    PRICING_TYPE_ID uniqueidentifier,
    PRICE double precision,
    --
    primary key nonclustered (ID)
);
