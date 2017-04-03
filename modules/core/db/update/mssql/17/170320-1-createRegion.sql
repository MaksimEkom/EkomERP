create table EKOMERP_REGION (
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
    NAME varchar(255) not null,
    COUNTRY_ID uniqueidentifier not null,
    --
    primary key nonclustered (ID)
);
