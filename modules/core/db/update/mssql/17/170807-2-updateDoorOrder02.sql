update EKOMERP_DOOR_ORDER set NUMBER_ = '' where NUMBER_ is null ;
alter table EKOMERP_DOOR_ORDER alter column NUMBER_ varchar(20) not null ;
