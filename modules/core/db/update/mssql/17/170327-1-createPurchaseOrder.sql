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
    CODE varchar(255),
    VENDOR_ID uniqueidentifier not null,
    STATE varchar(50),
    NOTES varchar(max),
    AMOUNT_UNTAXED double precision,
    AMOUNT_TAX double precision,
    AMOUNT_WITH_TAX double precision,
    --
    primary key nonclustered (ID)
);
