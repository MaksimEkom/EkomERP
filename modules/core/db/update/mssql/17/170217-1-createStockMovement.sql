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
    DATE_ datetime2,
    LOCATION_ID uniqueidentifier,
    STOCK_MOVEMENT_TYPE integer,
    --
    primary key nonclustered (ID)
);
