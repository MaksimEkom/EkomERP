alter table EKOMERP_LABORIOUSNESS add constraint FK_EKOMERP_LABORIOUSNESS_PRODUCT foreign key (PRODUCT_ID) references EKOMERP_PRODUCT(ID);
alter table EKOMERP_LABORIOUSNESS add constraint FK_EKOMERP_LABORIOUSNESS_OPERATION foreign key (OPERATION_ID) references EKOMERP_OPERATION(ID);
create index IDX_EKOMERP_LABORIOUSNESS_OPERATION on EKOMERP_LABORIOUSNESS (OPERATION_ID);
create index IDX_EKOMERP_LABORIOUSNESS_PRODUCT on EKOMERP_LABORIOUSNESS (PRODUCT_ID);
