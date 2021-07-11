/*	
** script that contains the project Procedures
**
** Pedro Abreu & João Gameiro
 */

/* Criar um evento */
GO
CREATE PROCEDURE create_evento( @id VARCHAR(20), @nome VARCHAR(150), @numdias INT, @dataini DATE, @datafim DATE, @numbilhetes INT, @cc_promotor VARCHAR(12), @dataproposta DATE, @cc_stageManager VARCHAR(12))
AS
BEGIN
	BEGIN TRY
		INSERT INTO EM.EVENTO VALUES(@id, @nome, @numdias, @dataini, @datafim, @numbilhetes, @cc_promotor, @dataproposta, @cc_stageManager);
		PRINT 'Sucess'
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
	END CATCH
END

SELECT * FROM EM.EVENTO;

/* Teste */
EXEC create_evento 12, 'Evento Teste', 1,'2021-06-01', '2021-06-01',1400, 2134022,'2020-01-30',12003011;




/* Criar um concerto e um soundcheck para o concerto com banda já existente*/
GO
CREATE PROCEDURE create_concerto( @id VARCHAR(20), @datatimeini DATETIME, @duracao TIME, @id_banda VARCHAR(20), @id_evento INT, @id_soundcheck INT, @duracao_s TIME, @data_s DATETIME)
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION
			INSERT INTO EM.SOUNDCHECK VALUES(@id_soundcheck, @duracao_s, @data_s); 
			INSERT INTO EM.CONCERTO VALUES(@id, @datatimeini, @duracao, @id_banda, @id_evento, @id_soundcheck);
			PRINT 'Sucess'
		COMMIT
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK
	END CATCH
END

/* Teste */
SELECT * FROM EM.CONCERTO;
SELECT * FROM EM.SOUNDCHECK;
SELECT * FROM EM.EVENTO;
EXEC create_concerto 80, '2022-06-05 21:00:00', '04:00:00', 'ff', 40, 80, '00:30:00', '2022-06-05 13:00:00';





/*Apagar um evento dado o id do mesmo*/
GO
CREATE PROCEDURE delete_evento_byId( @id VARCHAR(20))
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION
			DELETE FROM EM.EVENTO WHERE id=@id; 
			PRINT 'Sucess'
		COMMIT
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK
	END CATCH
END

/* Teste */
SELECT * FROM EM.EVENTO;
EXEC delete_evento_byId 40;




/*Apagar um concerto dado o id do mesmo*/
GO
CREATE PROCEDURE delete_concerto_byId( @id VARCHAR(20))
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION
			DELETE FROM EM.CONCERTO WHERE id=@id; 
			PRINT 'Sucess'
		COMMIT
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK
	END CATCH
END

/* Teste */
SELECT * FROM EM.CONCERTO;
EXEC delete_concerto_byId 49;




/*Editar um evento */
GO
CREATE PROCEDURE alter_evento( @id VARCHAR(20), @nome VARCHAR(150), @numdias INT, @dataini DATE, @datafim DATE, @numBilhetes INT, @cc_promotor VARCHAR(12), @dataproposta DATE, @cc_stagemanager VARCHAR(12) )
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION
			DECLARE @id_old AS VARCHAR(20);
			DECLARE @nome_old AS VARCHAR(150);
			DECLARE @numdias_old AS INT;
			DECLARE @dataini_old AS DATE;
			DECLARE @datafim_old AS DATE;
			DECLARE @numBilhetes_old INT;
			DECLARE @cc_promotor_old VARCHAR(12);
			DECLARE @dataproposta_old DATE;
			DECLARE @cc_stagemanager_old VARCHAR(12);

			SELECT @id_old = id, @nome_old = nome, @numdias_old = numdias, @dataini_old = dataini, @datafim_old = datafim, @numBilhetes_old = numbilhetes, @cc_promotor_old = cc_promotor, @dataproposta_old = dataproposta, @cc_stagemanager_old = cc_stageManager
			FROM EM.EVENTO
			WHERE EM.EVENTO.id = @id;

			IF @id_old != @id
			BEGIN
				UPDATE EM.EVENTO SET id = @id WHERE id=@id_old;
				PRINT 'Event id updated with success'
			END

			IF @nome_old != @nome
			BEGIN
				UPDATE EM.EVENTO SET nome = @nome WHERE @id_old = id;
				PRINT 'Event name updated with success'
			END

			IF @numdias_old != @numdias
			BEGIN
				UPDATE EM.EVENTO SET numdias = @numdias WHERE @id_old = id;
				PRINT 'Event days updated with success'
			END

			IF @dataini_old != @dataini
			BEGIN
				UPDATE EM.EVENTO SET dataini = @dataini WHERE @id_old = id;
				PRINT 'Event start date updated with success'
			END

			IF @datafim_old != @datafim
			BEGIN
				UPDATE EM.EVENTO SET datafim = @datafim WHERE @id_old = id;
				PRINT 'Event end date updated with success'
			END

			IF @numbilhetes_old != @numbilhetes
			BEGIN
				UPDATE EM.EVENTO SET numbilhetes = @numbilhetes WHERE @id_old = id;
				PRINT 'Event tickets updated with success'
			END

			IF @cc_promotor_old != @cc_promotor
			BEGIN
				UPDATE EM.EVENTO SET cc_promotor = @cc_promotor WHERE @id_old = id;
				PRINT 'Event end promotor updated with success'
			END

			IF @dataproposta_old != @dataproposta
			BEGIN
				UPDATE EM.EVENTO SET dataproposta = @dataproposta WHERE @id_old = id;
				PRINT 'Event proposal date updated with success'
			END

			IF @cc_stagemanager != @cc_stagemanager_old
			BEGIN
				UPDATE EM.EVENTO SET @cc_stagemanager =  @cc_stagemanager WHERE @id_old = id;
				PRINT 'Event stage manager updated with success'
			END

		COMMIT
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK
	END CATCH
END

/* Teste */
SELECT * FROM EM.EVENTO;
EXEC alter_evento 1, 'RockFest', 2, '2018-06-02', '2018-06-03', 1450, 1134000, '2017-12-01',22032242;









/*Editar um concerto */
GO
CREATE PROCEDURE alter_concerto( @id VARCHAR(20), @datatimeini DATETIME, @duracao TIME, @id_banda VARCHAR(20), @id_evento VARCHAR(20), @id_soundcheck VARCHAR(20), @soundcheck_duracao TIME, @soundcheck_datetime DATETIME)
AS
BEGIN
	BEGIN TRY
		BEGIN TRANSACTION
			DECLARE @id_old AS VARCHAR(20);
			DECLARE @datatimeini_old AS DATETIME;
			DECLARE @duracao_old AS TIME;
			DECLARE @idbanda_old AS VARCHAR(20);
			DECLARE @idevento_old AS VARCHAR(20);
			DECLARE @idsoundcheck_old AS VARCHAR(20);
			DECLARE @soundcheckduracao_old AS TIME;
			DECLARE @soundcheckdataime_old AS DATETIME;

			SELECT @id_old = id, @datatimeini_old = datatimeini, @duracao_old = duracao, @idbanda_old = id_banda, @idevento_old = id_evento, @idsoundcheck_old = id_soundcheck
			FROM EM.CONCERTO
			WHERE EM.CONCERTO.id = @id;

			SELECT @soundcheckduracao_old = duracao, @soundcheckdataime_old = datatimeini
			FROM EM.SOUNDCHECK
			WHERE id = @idsoundcheck_old

			IF @id_old != @id
			BEGIN
				UPDATE EM.CONCERTO SET id = @id WHERE id=@id_old;
				PRINT 'Concert id updated with success'
			END

			IF @datatimeini_old != @datatimeini
			BEGIN
				UPDATE EM.CONCERTO SET datatimeini = @datatimeini WHERE id=@id_old;
				PRINT 'Concert Start date updated with success'
			END

			IF @duracao_old != @duracao
			BEGIN
				UPDATE EM.CONCERTO SET duracao = @duracao WHERE id=@id_old;
				PRINT 'Concert duration updated with success'
			END

			IF @id_banda != @idbanda_old
			BEGIN
				UPDATE EM.CONCERTO SET id_banda = @id_banda WHERE id=@id_old;
				PRINT 'Concert band updated with success'
			END

			IF @id_evento != @idevento_old
			BEGIN
				UPDATE EM.CONCERTO SET id_evento = @id_evento WHERE id=@id_old;
				PRINT 'Concert event updated with success'
			END

			IF @id_soundcheck != @idsoundcheck_old
			BEGIN
				UPDATE EM.CONCERTO SET id_soundcheck = @id_soundcheck WHERE id=@id_old;
				PRINT 'Concert soundcheck updated with success'
			END

			IF @soundcheckduracao_old != @soundcheck_duracao
			BEGIN
				UPDATE EM.SOUNDCHECK SET duracao = @soundcheck_duracao WHERE id=@idsoundcheck_old;
				PRINT 'Soundcheck duration updated with success'
			END

			IF  @soundcheckdataime_old != @soundcheck_datetime
			BEGIN
				UPDATE EM.SOUNDCHECK SET datatimeini = @soundcheck_datetime WHERE id=@idsoundcheck_old;
				PRINT 'Soundcheck start date updated with success'
			END
		COMMIT
	END TRY
	BEGIN CATCH
		PRINT ERROR_MESSAGE()
		ROLLBACK
	END CATCH
END

/* Teste */
SELECT * FROM EM.CONCERTO;
SELECT * FROM EM.SOUNDCHECK;
EXEC alter_concerto 300, '2021-10-14 20:00:00', '02:00:00', 'ff', 13, 300, '00:35:00', '2021-09-14 10:00:00';
