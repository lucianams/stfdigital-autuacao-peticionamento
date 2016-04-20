create table peticionamento.classe_peticionavel (sig_classe varchar2(6) not null, nom_classe varchar2(100) not null, constraint pk_classe_peticionavel primary key (sig_classe));

create table peticionamento.preferencia (seq_preferencia number not null, nom_preferencia varchar2(100) not null, constraint pk_preferencia primary key (seq_preferencia));

create table peticionamento.classe_preferencia (sig_classe varchar2(6) not null, seq_preferencia number not null, constraint pk_classe_preferencia primary key (sig_classe, seq_preferencia));
alter table peticionamento.classe_preferencia add constraint fk_classe_peticionavel_clpr foreign key (sig_classe) references peticionamento.classe_peticionavel(sig_classe);
alter table peticionamento.classe_preferencia add constraint fk_preferencia_clpr foreign key (seq_preferencia) references peticionamento.preferencia(seq_preferencia);

drop table peticionamento.peticao_envolvido;
alter table peticionamento.envolvido add column tip_polo varchar2(11) not null;
alter table peticionamento.envolvido add constraint ck_envo_tip_polo check (tip_polo in ('ATIVO', 'PASSIVO', 'INTERESSADO'));
alter table peticionamento.envolvido alter column nom_envolvido rename to nom_apresentacao;
alter table peticionamento.envolvido alter column nom_apresentacao set not null;
alter table peticionamento.envolvido add column seq_pessoa number;
alter table peticionamento.envolvido add column seq_protocolo number not null;
alter table peticionamento.envolvido add constraint fk_peticao_envo foreign key (seq_protocolo) references peticionamento.peticao(seq_protocolo);

create sequence peticionamento.seq_anexo increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table peticionamento.tipo_anexo (seq_tipo_documento number not null, nom_tipo_documento varchar2(100) not null, constraint pk_tipo_anexo primary key (seq_tipo_documento));

create table peticionamento.anexo (seq_anexo number not null, dsc_anexo varchar2(50) not null, seq_tipo_anexo number not null, seq_documento number not null, seq_protocolo number not null, constraint pk_anexo primary key (seq_anexo));
alter table peticionamento.anexo add constraint fk_peticao_anex foreign key (seq_protocolo) references peticionamento.peticao(seq_protocolo);

create table peticionamento.orgao_peticionador (seq_pessoa number not null, nom_pessoa varchar2(100) not null, constraint pk_orgao_peticionador primary key (seq_pessoa));

create sequence peticionamento.seq_associado increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table peticionamento.associado (seq_associado number not null, seq_pessoa number not null, nom_pessoa varchar2(100) not null, tip_associado varchar2(13) not null, dsc_cargo_funcao varchar2(50), seq_pessoa_orgao number not null, constraint pk_associado primary key (seq_associado));
alter table peticionamento.associado add constraint fk_orgao_peticionador_asso foreign key (seq_pessoa_orgao) references peticionamento.orgao_peticionador(seq_pessoa);
alter table peticionamento.associado add constraint uk_orpe_seq_pessoa unique key (seq_pessoa, seq_pessoa_orgao);
alter table peticionamento.associado add constraint ck_orpe_tip_associado check (tip_associado in ('ASSOCIADO', 'GESTOR', 'REPRESENTANTE'));

alter table peticionamento.peticao add column sig_peticionador varchar2(30) not null;
alter table peticionamento.peticao add column dat_peticionamento date not null;