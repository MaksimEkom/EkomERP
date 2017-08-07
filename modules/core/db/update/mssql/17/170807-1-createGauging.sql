create table EKOMERP_GAUGING (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    DOOR_ORDER_NUMBER varchar(255),
    WIDTH decimal(19, 2) not null,
    HEIGHT decimal(19, 2) not null,
    DOOR_ORDER_ID uniqueidentifier not null,
    --
    primary key nonclustered (ID)
);
