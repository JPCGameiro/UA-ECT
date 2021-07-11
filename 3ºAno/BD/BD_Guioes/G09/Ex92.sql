CREATE TABLE mytemp (
	rid BIGINT IDENTITY (1,1) NOT NULL,
	at1 INT NULL,
	at2 INT NULL,
	at3 INT NULL,
	lixo varchar(100) NULL,
	PRIMARY KEY CLUSTERED (RID) with (fillfactor = 80)
);

create index index1 on mytemp(at1);
create index index2 on mytemp(at2);
create index index3 on mytemp(at3);
create index indexlixo on mytemp(lixo);

DROP TABLE mytemp;

-- Record the Start Time
DECLARE @start_time DATETIME, @end_time DATETIME;
SET @start_time = GETDATE();
PRINT @start_time; 

-- Generate random records
DECLARE @val as int = 1;
DECLARE @nelem as int = 50000;

SET nocount ON

WHILE @val <= @nelem
BEGIN
 DBCC DROPCLEANBUFFERS; -- need to be sysadmin
 INSERT mytemp (/*rid,*/ at1, at2, at3, lixo)
 SELECT /*cast((RAND()*@nelem*40000) as int),*/ cast((RAND()*@nelem) as int),
		cast((RAND()*@nelem) as int), cast((RAND()*@nelem) as int),
		'lixo...lixo...lixo...lixo...lixo...lixo...lixo...lixo...lixo';
 SET @val = @val + 1;
 PRINT str(@val)
END
PRINT 'Inserted ' + str(@nelem) + ' total records'

-- Duration of Insertion Process
SET @end_time = GETDATE();
PRINT 'Milliseconds used: ' + CONVERT(VARCHAR(20), DATEDIFF(MILLISECOND, @start_time, @end_time));

-- b)
-- Miliseconds used: 576480
-- Page Fullness: 68,47%
-- Total Fragmentation: 99,07%

-- c)
-- fillfactor = 65
-- Miliseconds used: 544820

-- fillfactor = 80
-- Miliseconds used: 540363

-- fillfactor = 90
-- Miliseconds used: 503600

-- d)
-- Miliseconds used: 97180

-- e)
-- Miliseconds used:1146670

´--Tempo de inserção com todos os índices é muito maior do que apenas com um