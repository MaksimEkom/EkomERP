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
    PARTNER varchar(255) not null,
    STATE varchar(50),
    TYPE_ varchar(50),
    ORIGIN varchar(255),
    AMOUNT_UNTAXED double precision,
    AMOUNT_TAX double precision,
    AMOUNT_TOTAL double precision,
    NOTES varchar(max),
    --
    primary key nonclustered (ID)
);
