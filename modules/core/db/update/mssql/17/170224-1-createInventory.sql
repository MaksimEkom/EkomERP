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
);
