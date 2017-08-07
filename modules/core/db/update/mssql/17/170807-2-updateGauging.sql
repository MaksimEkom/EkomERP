alter table EKOMERP_GAUGING drop column WIDTH ;
alter table EKOMERP_GAUGING add WIDTH integer ^
update EKOMERP_GAUGING set WIDTH = 0 where WIDTH is null ;
alter table EKOMERP_GAUGING alter column WIDTH integer not null ;
alter table EKOMERP_GAUGING drop column HEIGHT ;
alter table EKOMERP_GAUGING add HEIGHT integer ^
update EKOMERP_GAUGING set HEIGHT = 0 where HEIGHT is null ;
alter table EKOMERP_GAUGING alter column HEIGHT integer not null ;
