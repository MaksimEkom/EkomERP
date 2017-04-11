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
    --
    primary key nonclustered (ID)
);
