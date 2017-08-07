create table EKOMERP_PAYMENT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255),
    DATE_ datetime2,
    PAYMENT_METHOD varchar(50),
    NOTES varchar(max),
    INVOICE_ID uniqueidentifier,
    PAYMENT_TYPE varchar(50),
    AMOUNT decimal(19, 2) not null,
    --
    primary key nonclustered (ID)
);
