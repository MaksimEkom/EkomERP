create table EKOMERP_CALCULATION (
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
    NAME varchar(255) not null,
    PRODUCT_ID uniqueidentifier not null,
    SELLING_PRICE_TOTAL double precision,
    AMOUNT_TAX double precision,
    SELLING_PRICE_UNTAXED double precision,
    PROFIT double precision,
    COST_PRICE double precision,
    DATE_ datetime2,
    --
    primary key nonclustered (ID)
);
