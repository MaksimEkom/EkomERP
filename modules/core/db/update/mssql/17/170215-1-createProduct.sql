create table EKOMERP_PRODUCT (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    UNIT_ID uniqueidentifier,
    FULL_NAME varchar(255),
    DOC_NAME varchar(255),
    NOTES varchar(max),
    HEIGHT integer,
    WIDTH integer,
    DEPTH integer,
    CATEGORY_ID uniqueidentifier,
    ACTIVE tinyint,
    ANALOG varchar(255),
    WEIGHT double precision,
    --
    primary key nonclustered (ID)
);
