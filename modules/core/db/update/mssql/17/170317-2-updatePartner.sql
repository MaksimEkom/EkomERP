alter table EKOMERP_PARTNER add TYPE_ varchar(50) ;
alter table EKOMERP_PARTNER drop column ADDRESSES ;
alter table EKOMERP_PARTNER drop column PARTNER_TYPE ;
alter table EKOMERP_PARTNER add PARTNER_TYPE varchar(50) ;
