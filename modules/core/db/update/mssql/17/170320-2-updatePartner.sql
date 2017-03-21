alter table EKOMERP_PARTNER add REGION_ID uniqueidentifier ;
alter table EKOMERP_PARTNER add COUNTRY_ID uniqueidentifier ;
alter table EKOMERP_PARTNER drop column REGION ;
alter table EKOMERP_PARTNER drop column COUNTRY ;
