update EKOMERP_PURCHASE_ORDER set NUMBER_ = '' where NUMBER_ is null ;
alter table EKOMERP_PURCHASE_ORDER alter column NUMBER_ varchar(255) not null ;
