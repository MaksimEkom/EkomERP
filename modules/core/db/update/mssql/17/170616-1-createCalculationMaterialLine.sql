create table EKOMERP_CALCULATION_MATERIAL_LINE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    CALCULATION_ID uniqueidentifier,
    MATERIAL_ID uniqueidentifier,
    QUANTITY double precision,
    PRICE double precision,
    --
    primary key nonclustered (ID)
);
