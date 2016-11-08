create index PETICIONAMENTO.FK_PETICAO_ANEX on PETICIONAMENTO.ANEXO (
   SEQ_PROTOCOLO ASC
);

create index PETICIONAMENTO.FK_TIPO_ANEXO_ANEX on PETICIONAMENTO.ANEXO (
   SEQ_TIPO_ANEXO ASC
);

create index PETICIONAMENTO.FK_ORGAO_PETICIONADOR_ASSO on PETICIONAMENTO.ASSOCIADO (
   SEQ_PESSOA_ORGAO ASC
);

create index PETICIONAMENTO.FK_PETICAO_ENVO on PETICIONAMENTO.ENVOLVIDO (
   SEQ_PROTOCOLO ASC
);

create index PETICIONAMENTO.FK_CLASSE_PETICIONAVEL_PETI on PETICIONAMENTO.PETICAO (
   SIG_CLASSE ASC
);

create index PETICIONAMENTO.FK_ORGAO_PETICIONADOR_PETI on PETICIONAMENTO.PETICAO (
   SEQ_ORGAO ASC
);