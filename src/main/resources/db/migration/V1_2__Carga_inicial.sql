-- SELECT sig_classe, dsc_classe nom_classe FROM judiciario.classe WHERE flg_ativo = 'S' AND flg_admite_meio_eletronico = 'S' AND flg_admite_peticionador_web = 'S';
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('ADI','AÇÃO DIRETA DE INCONSTITUCIONALIDADE');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('ACO','AÇÃO CÍVEL ORIGINÁRIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('HD','HABEAS DATA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('MS','MANDADO DE SEGURANÇA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AO','AÇÃO ORIGINÁRIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('IF','INTERVENÇÃO FEDERAL');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AR','AÇÃO RESCISÓRIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('SS','SUSPENSÃO DE SEGURANÇA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('CC','CONFLITO DE COMPETÊNCIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('ADC','AÇÃO DECLARATÓRIA DE CONSTITUCIONALIDADE');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AOE','AÇÃO ORIGINÁRIA ESPECIAL');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AS','ARGÜIÇÃO DE SUSPEIÇÃO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('ADPF','ARGÜIÇÃO DE DESCUMPRIMENTO DE PRECEITO FUNDAMENTAL');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('SL','SUSPENSÃO DE LIMINAR');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('Pet','PETIÇÃO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('STA','SUSPENSÃO DE TUTELA ANTECIPADA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('ADO','AÇÃO DIRETA DE INCONSTITUCIONALIDADE POR OMISSÃO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('PSV','PROPOSTA DE SÚMULA VINCULANTE');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AImp','ARGÜIÇÃO DE IMPEDIMENTO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('EL','EXCEÇÃO DE LITISPENDÊNCIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('EI','EXCEÇÃO DE INCOMPETÊNCIA');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('HC','HABEAS CORPUS');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('Rcl','RECLAMAÇÃO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('MI','MANDADO DE INJUNÇÃO');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('AC','AÇÃO CAUTELAR');
Insert into PETICIONAMENTO.CLASSE_PETICIONAVEL (SIG_CLASSE,NOM_CLASSE) values ('RvC','REVISÃO CRIMINAL');

-- SELECT seq_tipo_preferencia seq_preferencia, dsc_preferencia nom_preferencia FROM judiciario.tipo_preferencia WHERE seq_tipo_preferencia IN (SELECT seq_tipo_preferencia FROM judiciario.classe_tipo_preferencia WHERE flg_ativo = 'S' AND sig_classe IN (SELECT sig_classe FROM judiciario.classe WHERE flg_ativo = 'S' AND flg_admite_meio_eletronico = 'S' AND flg_admite_peticionador_web = 'S'));
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (3,'Eleitoral');
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (8,'Medida Liminar');
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (2,'Criminal');
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (12,'Réu Preso');
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (1,'Maior de 60 anos ou portador de doença grave');
Insert into PETICIONAMENTO.PREFERENCIA (SEQ_PREFERENCIA,NOM_PREFERENCIA) values (15,'Assistência Judiciária Gratuita');

-- SELECT sig_classe, seq_tipo_preferencia seq_preferencia FROM judiciario.classe_tipo_preferencia WHERE flg_ativo = 'S' AND sig_classe IN (SELECT sig_classe FROM judiciario.classe WHERE flg_ativo = 'S' AND flg_admite_meio_eletronico = 'S' AND flg_admite_peticionador_web = 'S');
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADI',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADI',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ACO',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HD',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HD',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HD',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MS',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AO',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('IF',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('IF',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AR',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AR',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AR',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AR',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SS',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SS',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SS',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SS',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SS',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('CC',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('CC',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADC',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADC',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AOE',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AOE',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AOE',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AS',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AS',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AS',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AS',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADPF',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADPF',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('SL',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Pet',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('STA',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('STA',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('STA',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('STA',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('STA',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADO',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('ADO',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('PSV',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AImp',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AImp',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AImp',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AImp',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EL',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EL',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EL',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EL',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EI',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EI',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EI',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('EI',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HC',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HC',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HC',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('HC',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('Rcl',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MI',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('MI',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('AC',15);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',2);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',3);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',12);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',1);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',8);
Insert into PETICIONAMENTO.CLASSE_PREFERENCIA (SIG_CLASSE,SEQ_PREFERENCIA) values ('RvC',15);

-- SELECT cod_tipo_peca_processual seq_tipo_documento, dsc_tipo_peca_processual nom_tipo_documento FROM judiciario.tipo_peca_processual WHERE flg_ativo = 'S' AND EXISTS (SELECT 1 FROM judiciario.tipo_peca_processual_classe WHERE flg_admite_peticionador_web = 'S' AND sig_classe IN (SELECT sig_classe FROM judiciario.classe WHERE flg_ativo = 'S' AND flg_admite_meio_eletronico = 'S' AND flg_admite_peticionador_web = 'S'));
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (98,'Movimento Processual');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (99,'Manifestação da AGU');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (100,'Informação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (101,'Petição inicial');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1325,'Queixa / Denúncia');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1408,'Resposta à acusação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1409,'Resposta do réu');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (102,'Manifestação da Comissão de Jurisprudência');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (103,'Manifestação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (107,'Edital');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (109,'Alvará');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1060,'Despacho');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1068,'Manifestação sobre proposta de súmula vinculante');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1221,'Inteiro teor do acórdão');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1270,'Manifestação sobre a repercussão geral');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1272,'Relatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1273,'Extrato de ata');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1274,'Documento comprobatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1327,'Custas');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1333,'Contestação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1337,'Cópia do ato normativo ou lei impugnada');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1341,'Decisão rescindenda');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1344,'Alegações finais');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1346,'Ato coator');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1349,'Decisão ou ato reclamado');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1354,'Decisão impugnada');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1277,'Recibo de petição eletrônica');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1280,'Comunicação assinada');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1324,'Sentença');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1411,'Recursos para segunda instância');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1326,'Contrarrazões do recurso');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1415,'Recursos para Tribunais Superiores');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1416,'Certidão de não interposição de recurso a Tribunal Superior');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1417,'Decisões Tribunais Superiores');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1418,'Decisão monocrática Tribunal Superior');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1419,'Acórdão Tribunal Superior');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1430,'Decisão de admissibilidade do recurso extraordinário');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1422,'Outras peças');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1423,'Decisão interlocutória');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1424,'Gratuidade de justiça');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1425,'Mandado');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1426,'Carta');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1427,'Termo de Audiência');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1428,'Fax');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1429,'Íntegra da Movimentação Processual');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1468,'Ato ordinatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1469,'Carta de ordem');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1470,'Precatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1488,'Carta de Sentença');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1489,'Acórdão Condenatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1508,'Denúncia');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1509,'Interrogatório');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1,'Procurações e substabelecimentos');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (8,'Certidão de intimação/publicação do acórdão ou da decisão recorrida');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1431,'Recurso Extraordinário');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (12,'Petição de recurso extraordinário');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (13,'Contrarrazões do recurso extraordinário');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (14,'Certidão de não apresentação de contrarrazões do recurso extraordinário');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (50,'Petição de agravo de instrumento');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1420,'Petição de agravo (Lei 12.322/2010)');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1421,'Comprovante de recolhimento do preparo');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (26,'Petição');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (31,'Voto');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (33,'RECURSO');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (46,'Certidão de julgamento');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (48,'Decisões primeiro grau');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1410,'Pronúncia / impronúncia');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (49,'Decisões segundo grau');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (29,'Decisão de admissibilidade');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1412,'Decisão monocrática segundo grau');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1413,'Acórdão segundo grau');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (1414,'Decisão de admissibilidade do recurso para Tribunal Superior');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (60,'VOLUME');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (70,'Certidão de publicação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (80,'Intimação');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (81,'Ofício');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (84,'Certidão de trânsito em julgado');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (86,'Termo de baixa');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (89,'Apenso');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (90,'Juntada por linha');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (91,'Manifestação da PGR');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (93,'Termo de remessa');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (95,'Certidão');
Insert into PETICIONAMENTO.TIPO_ANEXO (SEQ_TIPO_DOCUMENTO,NOM_TIPO_DOCUMENTO) values (97,'Aviso de recebimento');

-- SELECT seq_pessoa, nom_pessoa FROM corporativo.pessoa WHERE seq_pessoa IN (SELECT seq_pessoa FROM corporativo.usuario WHERE seq_usuario IN (SELECT seq_usuario_externo FROM estf.usuario_externo_integracao) AND flg_ativo = 'S') AND UPPER(nom_pessoa) LIKE '%TRIBUNAL%';
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452180,'Tribunal Superior do Trabalho');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452182,'Tribunal Regional Federal da 1ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452141,'TRIBUNAL DE JUSTIÇA DO ESTADO DO ESPÍRITO SANTO');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452219,'Tribunal de Justiça do Estado do Amazonas');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452220,'TRIBUNAL DE JUSTIÇA DO ESTADO DO ACRE');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12463563,'Tribunal de Justiça do Estado da Bahia');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12475536,'TRIBUNAL DE JUSTIÇA DO ESTADO DE SÃO PAULO');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12570227,'Tribunal Regional do Trabalho da 23ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12675529,'Tribunal de Justiça do Estado de Alagoas');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452140,'Tribunal de Justiça do Estado de Goiás');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452896,'Tribunal de Justiça do Estado de Minas Gerais');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452299,'Tribunal de Justiça do Estado do Tocantins');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12473673,'TRF4 - TRIBUNAL REGIONAL FEDERAL DA 4ª REGIAO');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12487766,'Tribunal Regional Federal da 5ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12930871,'Tribunal de Justiça de Minas Gerais');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452221,'Tribunal de Justiça do Estado do Pernambuco');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12471851,'TRIBUNAL DE JUSTIÇA DO ESTADO DO MATO GROSSO DO SUL');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12486976,'Tribunal de Justiça Militar do Estado de Minas Gerais');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12494482,'TRF2 - Tribunal Regional Federal da 2ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452298,'Tribunal de Justiça do Estado do Rio de Janeiro');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452222,'Tribunal de Justiça do Estado do Ceará');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12468280,'Tribunal de Justiça do Estado do Pará');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452179,'Tribunal Regional Federal da 4ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452261,'Superior Tribunal de Justiça');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452262,'Tribunal de Justiça do Estado do Maranhão');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12551363,'Tribunal Regional do Trabalho da 24ª Região');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12701773,'Tribunal de Justiça do Estado de Mato Grosso do Sul');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452662,'Tribunal de Justiça do Estado de Santa Catarina');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12473966,'TRF1 - TRIBUNAL REGIONAL FEDERAL DA 1ª REGIAO');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452260,'Tribunal de Justiça do Estado de Sergipe');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452183,'Tribunal de Justiça do Estado do Paraná');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452898,'Tribunal de Justiça do Estado do Rio Grande do Sul');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12452825,'Tribunal de Justiça do Distrito Federal e Territórios');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12464809,'Tribunal Superior Eleitoral');
Insert into PETICIONAMENTO.ORGAO_PETICIONADOR (SEQ_PESSOA,NOM_PESSOA) values (12596313,'Tribunal de Justiça do Estado do Amapá');

-- Carga de usuário representante do órgão para testes
Insert into PETICIONAMENTO.ASSOCIADO (SEQ_ASSOCIADO, SEQ_PESSOA, NOM_PESSOA, TIP_ASSOCIADO, DSC_CARGO_FUNCAO, SEQ_PESSOA_ORGAO) values (PETICIONAMENTO.SEQ_ASSOCIADO.nextval, 1, 'USUARIO_FALSO', 'REPRESENTANTE', 'PETICIONADOR', 12452261);