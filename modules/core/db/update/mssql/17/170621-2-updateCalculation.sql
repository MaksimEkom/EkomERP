alter table EKOMERP_CALCULATION drop column PRODUCTION_EXPENSES_RATE ;
alter table EKOMERP_CALCULATION add PRODUCTION_EXPENSES_RATE decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column COMMERCIAL_EXPENSES_RATE ;
alter table EKOMERP_CALCULATION add COMMERCIAL_EXPENSES_RATE decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column PROFIT_RATE ;
alter table EKOMERP_CALCULATION add PROFIT_RATE decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column FSZN ;
alter table EKOMERP_CALCULATION add FSZN decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column BGS ;
alter table EKOMERP_CALCULATION add BGS decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column PRODUCTION_EXPENSES ;
alter table EKOMERP_CALCULATION add PRODUCTION_EXPENSES decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column COMMERCIAL_EXPENSES ;
alter table EKOMERP_CALCULATION add COMMERCIAL_EXPENSES decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column MATERIAL_SUM ;
alter table EKOMERP_CALCULATION add MATERIAL_SUM decimal(19, 2) ;
alter table EKOMERP_CALCULATION drop column LABOR_SUM ;
alter table EKOMERP_CALCULATION add LABOR_SUM decimal(19, 2) ;