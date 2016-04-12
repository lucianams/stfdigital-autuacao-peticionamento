create schema peticionamento;

create sequence peticionamento.seq_envolvido increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create sequence peticionamento.seq_evento increment by 1 start with 1 nomaxvalue minvalue 1 nocycle nocache;

create table peticionamento.peticao (seq_protocolo number not null, sig_classe varchar2(6) not null, seq_orgao number, constraint pk_peticao primary key (seq_protocolo));

create table peticionamento.envolvido (seq_envolvido number not null, nom_envolvido varchar2(50) not null, constraint pk_envolvido primary key (seq_envolvido));

create table peticionamento.peticao_envolvido (seq_protocolo number not null, seq_envolvido number not null, constraint pk_peticao_envolvido primary key (seq_protocolo, seq_envolvido));
alter table peticionamento.peticao_envolvido add constraint fk_peticao_peen foreign key (seq_protocolo) references peticionamento.peticao(seq_protocolo);
alter table peticionamento.peticao_envolvido add constraint fk_envolvido_peen foreign key (seq_envolvido) references peticionamento.envolvido(seq_envolvido);

create table peticionamento.evento (seq_evento number not null, nom_evento varchar2(50) not null, dat_criacao date not null, bin_detalhe clob not null, tip_status tinyint not null, constraint pk_evento primary key (seq_evento));

create table peticionamento.peticao_evento (seq_protocolo number not null, seq_evento number not null, constraint pk_peticao_evento primary key (seq_protocolo, seq_evento));
alter table peticionamento.peticao_evento add constraint fk_peticao_peev foreign key (seq_protocolo) references peticionamento.peticao(seq_protocolo);
alter table peticionamento.peticao_evento add constraint fk_evento_peev foreign key (seq_evento) references peticionamento.evento(seq_evento);