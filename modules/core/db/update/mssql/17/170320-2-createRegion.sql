alter table EKOMERP_REGION add constraint FK_EKOMERP_REGION_COUNTRY foreign key (COUNTRY_ID) references EKOMERP_COUNTRY(ID);
create index IDX_EKOMERP_REGION_COUNTRY on EKOMERP_REGION (COUNTRY_ID);
