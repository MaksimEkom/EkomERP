alter table EKOMERP_PARTNER add constraint FK_EKOMERP_PARTNER_COUNTRY foreign key (COUNTRY_ID) references EKOMERP_COUNTRY(ID);
create index IDX_EKOMERP_PARTNER_COUNTRY on EKOMERP_PARTNER (COUNTRY_ID);
