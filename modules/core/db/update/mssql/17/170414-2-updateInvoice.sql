-- alter table EKOMERP_INVOICE add PARTNER_ID uniqueidentifier ^
-- update EKOMERP_INVOICE set PARTNER_ID = <default_value> ;
-- alter table EKOMERP_INVOICE alter column PARTNER_ID uniqueidentifier not null ;
alter table EKOMERP_INVOICE add PARTNER_ID uniqueidentifier not null ;
alter table EKOMERP_INVOICE drop column PARTNER ;
