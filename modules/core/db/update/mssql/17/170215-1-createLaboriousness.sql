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
    OPERATION_ID uniqueidentifier not null,
    VALUE_ double precision not null,
    --
    primary key nonclustered (ID)
);
