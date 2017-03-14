create table EKOMERP_ADDRESS (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    TYPE_ integer not null,
    POSITION_ varchar(255),
    EMAIL varchar(255),
    PHONE varchar(255),
    MOBILE varchar(255),
    NOTES varchar(max),
    STREET varchar(255),
    CITY varchar(255),
    REGION varchar(255),
    ZIP varchar(255),
    COUNTRY varchar(255),
    --
    primary key nonclustered (ID)
);
