alter table peticionamento.peticao add tip_sigilo varchar2(15) not null;
alter table peticionamento.peticao add constraint ck_peti_tip_sigilo check (tip_sigilo in ('PUBLICO', 'SEGREDO_JUSTICA'));

alter table peticionamento.peticao add tip_processo varchar2(10) not null;
alter table peticionamento.peticao add constraint ck_peti_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));

alter table peticionamento.classe_peticionavel add tip_processo varchar2(10) not null;
alter table peticionamento.classe_peticionavel add constraint ck_clpe_tip_processo check (tip_processo in ('ORIGINARIO', 'RECURSAL'));