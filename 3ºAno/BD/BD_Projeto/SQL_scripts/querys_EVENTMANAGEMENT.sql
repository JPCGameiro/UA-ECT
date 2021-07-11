/*	
** script that includes querys to the database
**
** Pedro Abreu & João Gameiro
 */

/*	Promotores com mais do que um evento */
SELECT EM.PROMOTOR.nome, numCC, email, telefone, COUNT(id) as numEventos
FROM EM.PROMOTOR, EM.EVENTO
WHERE numCC = cc_promotor
GROUP BY EM.PROMOTOR.nome, numCC, email, telefone
HAVING  COUNT(id) > 1;

/* Promotores dos eventos com mais de um dia */
SELECT EM.PROMOTOR.nome, numCC, email, telefone
FROM EM.EVENTO, EM.PROMOTOR
WHERE numdias > 1 AND cc_promotor = numCC;

/* Promotores dos eventos que a acontecer a partir de 2020 */
SELECT EM.PROMOTOR.nome, numCC, email, telefone
FROM EM.EVENTO, EM.PROMOTOR
WHERE dataini > '2019-12-31' AND cc_promotor = numCC;

/* Promotores de eventos com mais de 3 concertos */
SELECT numCC, EM.PROMOTOR.nome, EM.EVENTO.id, EM.EVENTO.nome, cc_promotor, COUNT(EM.EVENTO.nome) as numConcertos
FROM EM.EVENTO, EM.CONCERTO, EM.PROMOTOR
WHERE id_evento = EM.EVENTO.id AND EM.EVENTO.cc_promotor = numCC
GROUP BY numCC, EM.PROMOTOR.nome, EM.EVENTO.id, EM.EVENTO.nome, cc_promotor
HAVING COUNT(EM.EVENTO.nome) > 3;


/* Eventos cujo número de bilhetes seja maior que a média de todos os bilhetes vendidos */
SELECT id, nome, numdias, numbilhetes 
FROM EM.EVENTO
WHERE numbilhetes > ANY( SELECT AVG(numbilhetes) as media_bilhetes
						 FROM EM.EVENTO);

/* Eventos e o número de dias, concertos e de artistas nos mesmos */
SELECT EM.EVENTO.id, EM.EVENTO.numdias, EM.EVENTO.nome, COUNT(DISTINCT EM.BANDA.nome) as num_bandas, COUNT(EM.CONCERTO.id) as num_concertos
FROM EM.EVENTO, EM.CONCERTO, EM.BANDA
WHERE id_evento=EM.EVENTO.id AND id_banda=EM.BANDA.id
GROUP BY EM.EVENTO.id, EM.EVENTO.numdias, EM.EVENTO.nome;

/* Número de elementos de Cada comitiva */
SELECT id, id_banda, EM.COMITIVA.email, COUNT(*) as numElementos
FROM EM.COMITIVA, EM.PESSOA
WHERE id=id_comitiva
GROUP BY id, id_banda, EM.COMITIVA.email
ORDER BY COUNT(*);

/* Número de acompanhantes de cada comitiva */
SELECT id, id_banda, EM.COMITIVA.email, COUNT(*) as numAcompanhantes
FROM EM.COMITIVA, EM.PESSOA, EM.ACOMPANHANTE
WHERE id=id_comitiva AND EM.PESSOA.numCC = EM.ACOMPANHANTE.numCC
GROUP BY id, id_banda, EM.COMITIVA.email
ORDER BY COUNT(*);


/* Número de Técnicos de cada comitiva */
SELECT id, id_banda, EM.COMITIVA.email, COUNT(*) as numTecnicos
FROM EM.COMITIVA, EM.PESSOA, EM.TECNICO
WHERE id=id_comitiva AND EM.PESSOA.numCC = EM.TECNICO.numCC
GROUP BY id, id_banda, EM.COMITIVA.email
ORDER BY COUNT(*);

/* Número de Musicos de apoio de cada comitiva */
SELECT id, EM.COMITIVA.id_banda, EM.COMITIVA.email, COUNT(*) as numMusicos
FROM EM.COMITIVA, EM.PESSOA, EM.MUSICO
WHERE id=id_comitiva AND EM.PESSOA.numCC = EM.MUSICO.numCC
GROUP BY id, EM.COMITIVA.id_banda, EM.COMITIVA.email
ORDER BY COUNT(*);

/* Número de Instrumentos que cada banda traz */
SELECT EM.BANDA.id, EM.BANDA.nome, COUNT(modelo) as numInstumentos
FROM EM.INSTRUMENTO, EM.MUSICO, EM.BANDA
WHERE id_banda=EM.BANDA.id AND numCC = musicoCC
GROUP BY EM.BANDA.id, EM.BANDA.nome;

/* Empresas de catering e o número de eventos aos quais fornecem serviços */
SELECT nif, EM.EMPRESACATERING.nome, COUNT(EM.EVENTO.nome) as numEventos 
FROM EM.REFEICAO, EM.EMPRESACATERING, EM.EVENTO
WHERE nif_empresa = nif AND id_evento = EM.EVENTO.id
GROUP BY nif, EM.EMPRESACATERING.nome;

