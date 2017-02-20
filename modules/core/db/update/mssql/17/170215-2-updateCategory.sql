alter table EKOMERP_CATEGORY drop column PARENT_CATEGORY_ID ;
alter table EKOMERP_CATEGORY add PARENT_CATEGORY_ID uniqueidentifier ;
