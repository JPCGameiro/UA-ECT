/*	
** script that contains the project UDFs
**
** Pedro Abreu & João Gameiro
 */

 --Dado um id devolve um evento
GO 
CREATE FUNCTION getEventosById (@id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.EVENTO
		   WHERE id = @id)
GO
 --Teste
SELECT * FROM getEventosById(10);



--Dado um nome devolve um evento
GO 
CREATE FUNCTION getEventosByNome (@nome VARCHAR(250)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.EVENTO
		   WHERE nome LIKE '%' + @nome + '%')
GO
 --Teste
SELECT * FROM getEventosByNome('Foo');
SELECT * FROM EM.EVENTO;



--Dado um ccPromotor devolve um evento
GO 
CREATE FUNCTION getEventosByPromotor (@promotor_cc VARCHAR(12)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.EVENTO
		   WHERE cc_promotor = @promotor_cc)
GO
 --Teste
SELECT * FROM getEventosByPromotor(114444);



--Dado um ccStageManager devolve um evento
GO 
CREATE FUNCTION getEventosByStageManager (@manager_cc VARCHAR(12)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.EVENTO
		   WHERE cc_stageManager = @manager_cc)
GO
 --Teste
SELECT * FROM getEventosByStageManager(32032541);



--Dado o id de um Evento devolver os seus concertos
GO
CREATE FUNCTION getConcertosByIDEvento (@evento_id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.CONCERTO
		   WHERE id_evento = @evento_id)
GO
--Teste
SELECT * FROM getConcertosByIDEvento(14);



--Dado o id de um Evento devolver os seus concertos e respectivos soundchecks
GO
CREATE FUNCTION getConcertosSounchecksByIDEvento (@evento_id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT EM.CONCERTO.id, EM.CONCERTO.datatimeini as init_concerto, EM.CONCERTO.duracao as duracao_concerto, id_banda, id_evento, id_soundcheck, EM.SOUNDCHECK.duracao as duracao_soundcheck, EM.SOUNDCHECK.datatimeini as ini_soundcheck 
		   FROM EM.CONCERTO, EM.SOUNDCHECK
		   WHERE id_evento = 14 AND id_soundcheck = EM.SOUNDCHECK.id)
GO
--Teste
SELECT * FROM getConcertosSounchecksByIDEvento(14);



--Dado o id de um Evento devolver as bandas que vão tocar
GO
CREATE FUNCTION getBandasByIDEvento (@evento_id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT EM.BANDA.id, nome, telefone, email, numElem, genero FROM EM.CONCERTO, EM.BANDA
		   WHERE id_evento = @evento_id AND id_banda = EM.BANDA.id)
GO
--Teste
SELECT * FROM getBandasByIDEvento(14);






--Dado o id de uma Banda devolver os seus concertos
GO
CREATE FUNCTION getConcertosByIDBanda (@banda_id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.CONCERTO
		   WHERE id_banda = @banda_id)
GO
--Teste
SELECT * FROM geTConcertosByIDBanda('gd');

--Dado o nome de uma Banda devolver os seus concertos
GO
CREATE FUNCTION getConcertosByNomeBanda( @nome VARCHAR(150)) RETURNS TABLE AS
	RETURN(SELECT EM.EVENTO.id AS IdEvento, EM.EVENTO.nome AS NomeEvento, EM.CONCERTO.id AS IdConcerto, EM.BANDA.nome, id_banda,  datatimeini, duracao 
		   FROM EM.BANDA, EM.CONCERTO, EM.EVENTO
		   WHERE EM.BANDA.nome=@nome AND EM.BANDA.id=id_banda AND id_evento=EM.EVENTO.id);
GO
--Teste
SELECT * FROM getConcertosByNomeBanda('Green Day')







-- Pesquisar músico por músico por CC
GO
CREATE FUNCTION getMusicoByCC (@cc VARCHAR(12)) RETURNS TABLE AS
	RETURN(SELECT *
		   FROM EM.V_MUSICOS
		   WHERE numCC LIKE '%' + @name + '%');
GO
--Teste
SELECT * FROM getMusicoByCC(10111732);

-- Pesquisar músico por músico por Nome
GO
CREATE FUNCTION getMusicoByName (@name VARCHAR(12)) RETURNS TABLE AS
	RETURN(SELECT *
		   FROM EM.V_MUSICOS
		   WHERE nome LIKE '%' + @name + '%');
GO
--Teste
SELECT * FROM getMusicoByName('Sara Ramos');

-- Pesquisar músico por músico por Nome Artistico
GO
CREATE FUNCTION getMusicoByArstName (@ar_name VARCHAR(12)) RETURNS TABLE AS
	RETURN(SELECT *
		   FROM EM.V_MUSICOS
		   WHERE nomeArst LIKE '%' + @ar_name + '%');
GO
--Teste
SELECT * FROM getMusicoByArstName('Sara Ramos');







--Dado um id devolver a banda
GO
CREATE FUNCTION getBandaById (@id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.BANDA
		   WHERE id=@id)
GO
--Teste
SELECT * FROM getBandaById('ff');

--Dado um nome devolver a banda
GO
CREATE FUNCTION getBandaByNome (@nome VARCHAR(250)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.BANDA
		   WHERE nome LIKE '%' + @nome + '%')
GO
--Teste
SELECT * FROM getBandaByNome('Foo Fighters');

--Dado um género devolver bandas
GO
CREATE FUNCTION getBandaByGenre (@genre VARCHAR(200)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.BANDA
		   WHERE genero=@genre)
GO
--Teste
SELECT * FROM getBandaByGenre('Punk');





--Dado o id de uma Banda devolver os músicos constituintes que vão tocar
GO
CREATE FUNCTION getMusicosByIDBanda (@banda_id VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.MUSICO
		   WHERE id_banda = @banda_id)
GO
--Teste
SELECT * FROM getMusicosByIDBanda('gd');





-- Dado uma data de inicio e de fim dar os eventos aí dentro
GO
CREATE FUNCTION getEventosInBetween (@data_inicio DATE, @data_fim DATE) RETURNS TABLE AS
	RETURN(SELECT * 
		   FROM EM.EVENTO
		   WHERE (dataini >= @data_inicio AND dataini <= @data_fim) OR (datafim >= @data_inicio AND datafim <= @data_fim)) 
GO
--Teste
SELECT * FROM EM.EVENTO;
SELECT * FROM getEventosInBetween('2018-05-28', '2018-06-03');


-- Dada uma duração minima e uma duração máxima devolver todos os concertos cuja duração se encontra entre esses valores
GO
CREATE FUNCTION getConcertosDuracaoInBetween (@min TIME, @max TIME) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.CONCERTO
		   WHERE (duracao >= @min AND duracao <= @max)) 
GO
--Teste
SELECT * FROM EM.CONCERTO;
SELECT * FROM getConcertosDuracaoInBetween('02:00:00', '05:00:00');
go

---------------------------


CREATE FUNCTION getOverviewByNome (@nome VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.V_OVERVIEW
		   WHERE nome LIKE  '%' + @nome + '%')
GO
select * from getOverviewByNome('Festa')
go

CREATE FUNCTION getOverviewByNumdias (@numdias VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.V_OVERVIEW
		   WHERE numdias LIKE  '%' + @numdias + '%')
GO
select * from getOverviewByNumdias(2)
go

CREATE FUNCTION getOverviewByBanda (@banda VARCHAR(20)) RETURNS TABLE AS
	RETURN(SELECT * FROM EM.V_OVERVIEW
		   WHERE banda LIKE  '%' + @banda + '%')
GO
select * from getOverviewByBanda('F')