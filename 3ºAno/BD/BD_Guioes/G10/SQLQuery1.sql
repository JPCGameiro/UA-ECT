
USE Company;

-- A)
GO
CREATE PROCEDURE remove_employee 
	@ssn as INT	
AS
BEGIN	
	DELETE FROM dependent WHERE essn = @ssn;
	DELETE FROM works_on WHERE essn = @ssn; 
	UPDATE department set Mgr_ssn = NULL where Mgr_ssn = @ssn;
	UPDATE employee set Super_ssn = NULL where Super_ssn = @ssn;
	DELETE FROM employee WHERE ssn = @ssn;
END

-- Teste
EXEC remove_employee 21312332; 


-- B)
GO
CREATE PROCEDURE get_emplyees_oldestmgr 
	@Oldssn INT OUTPUT,
	@Oldyear INT OUTPUT
AS
BEGIN
	SELECT Fname, Minit, Lname, Ssn, Salary, Dno, Mgr_start_date
	FROM employee, department
	WHERE Mgr_ssn = Ssn;

	SELECT TOP(1) @Oldssn = Ssn, @Oldyear = YEAR(Mgr_start_date)
	FROM employee, department
	WHERE Mgr_ssn = Ssn
	ORDER BY Mgr_start_date;
END
GO

--Teste
DECLARE @Oldssn INT, @Oldyear INT;
EXEC get_emplyees_oldestmgr @Oldssn OUTPUT, @Oldyear OUTPUT;
PRINT @Oldssn;
PRINT @Oldyear;




-- C)
GO
CREATE TRIGGER dpt_manager ON department
INSTEAD OF INSERT, UPDATE
AS
BEGIN
	DECLARE @Mgr_ssn AS INT;
	SELECT @Mgr_ssn=Mgr_ssn FROM inserted;

	IF @Mgr_ssn IN (SELECT Mgr_ssn FROM department)
	BEGIN
		PRINT 'Employee already manages one department'
	END
	ELSE
	BEGIN
		INSERT INTO department SELECT * FROM inserted;
		PRINT 'Success'
	END
END
GO

--Teste
SELECT * FROM DEPARTMENT;
SELECT * FROM EMPLOYEE;
INSERT INTO DEPARTMENT VALUES ('Teste', 5, 21312332,'2013-05-16');
INSERT INTO DEPARTMENT VALUES ('Teste', 5, 345355435 ,'2013-05-16');




-- D)
GO
CREATE TRIGGER check_salary ON employee
AFTER INSERT, UPDATE
AS
BEGIN
	DECLARE @Salary AS INT;
	DECLARE @MgrSalary AS INT;
	DECLARE @Ssn AS INT;
	DECLARE @Dno AS INT;
	DECLARE @Mgr_ssn AS INT;

	SELECT @Ssn=inserted.Ssn, @Salary=inserted.Salary, @Dno=inserted.Dno FROM inserted;

	SELECT @Mgr_Ssn = Mgr_ssn, @Salary = Salary
	FROM employee, department
	WHERE @Dno = Dnumber AND @Ssn = Ssn;

	SELECT @MgrSalary = Salary
	FROM employee
	WHERE Ssn = @Mgr_Ssn;
	
	IF @Salary > @MgrSalary
	BEGIN
		UPDATE employee SET Salary=@MgrSalary-1 WHERE Ssn=@Ssn;
	END
END
GO


-- Teste
INSERT INTO employee VALUES('Marco', 'A', 'Ramos', 345355435, '2001-05-01', 'Rua etc', 'M', 2200, NULL, 2);




-- E)
GO
CREATE FUNCTION locations_name (@ssn CHAR(9)) RETURNS @table TABLE
	(Pname		VARCHAR(15),
	 PLocation  VARCHAR(15))
AS
BEGIN
	INSERT @table
		SELECT Pname, Plocation
		FROM works_on, project
		WHERE Essn = @ssn AND Pno = Pnumber;
	RETURN;
END
GO

-- Test
SELECT * FROM locations_name(21312332);




-- F)
GO
CREATE FUNCTION above_average (@dno INT) RETURNS @table TABLE
	(Fname  VARCHAR(15),
	 Lname  VARCHAR(15),
	 Ssn    CHAR(9),
	 Salary DECIMAL(10,2))
AS
BEGIN
	INSERT @table
		SELECT Fname, Lname, Ssn, Salary
		FROM employee JOIN(SELECT Dno, AVG(Salary) as average_salary
						   FROM department, employee
						   WHERE Dno = Dnumber
						   GROUP BY Dno) AS DepSal
		ON DepSal.Dno = employee.Dno AND Salary>average_salary AND employee.Dno = @dno;
	RETURN;
END
GO

-- Test
SELECT * FROM above_average(2);
SELECT * FROM employee;



-- G)
GO
CREATE FUNCTION employeeDeptHighAverage(@departId  INT)
RETURNS @table TABLE (
	pname  VARCHAR(15), 
	pnumber INT, 
	plocation VARCHAR(15), 
	dnum INT, 
	budget FLOAT, 
	totalbudget FLOAT )
AS
BEGIN
	DECLARE C CURSOR
        FOR
            SELECT Pname, Pnumber, Plocation, Dnum, SUM((Salary*1.0*Hours)/40) AS Budget 
            FROM project JOIN works_on
            ON Pnumber=Pno
            JOIN employee
            ON Essn=Ssn
            WHERE Dnum=@departId
            GROUP BY Pnumber, Pname, Plocation, Dnum;
 
	DECLARE @pname AS VARCHAR(15), @pnumber AS INT, @plocation as VARCHAR(15), @dnum AS INT, @budget AS FLOAT, @totalbudget AS FLOAT;
	SET @totalbudget = 0;
    OPEN C;
	FETCH C INTO @pname, @pnumber, @plocation, @dnum, @budget;

	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @totalbudget += @budget;
		INSERT INTO @table VALUES (@pname, @pnumber, @plocation, @dnum, @budget, @totalbudget);
		FETCH C INTO @pname, @pnumber, @plocation, @dnum, @budget;
	END
	CLOSE C;
	DEALLOCATE C;
	RETURN;
END
GO

--Teste 
SELECT * FROM employeeDeptHighAverage(3);




-- H)
--After
GO
CREATE TRIGGER delete_department_after ON department
AFTER DELETE
AS
BEGIN
	IF NOT (EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'Department_Deleted'))
	BEGIN
		CREATE TABLE Department_Deleted(
			Dname VARCHAR(15) NOT NULL,
			Dnumber INT NOT NULL,
			Mgr_ssn CHAR(9) NOT NULL,
			Mgr_start_date DATE,
			PRIMARY KEY (Dnumber),
		);
		INSERT INTO Department_Deleted SELECT * FROM deleted;
	END
	ELSE
	BEGIN
		INSERT INTO Department_Deleted SELECT * FROM deleted;
	END
END
GO

--Teste
SELECT * FROM DEPARTMENT;
INSERT INTO DEPARTMENT VALUES('Teste', 5, 21312332 , '2015-08-02');
INSERT INTO DEPARTMENT VALUES('Teste2', 6,  21312332, '2015-08-02');
DELETE FROM DEPARTMENT WHERE Dnumber = 6;
SELECT * FROM Department_Deleted;



--Instead
GO
CREATE TRIGGER delete_department_instead ON department
INSTEAD OF DELETE
AS
BEGIN
	DECLARE @Dnumber AS INT;
	SELECT @Dnumber = Dnumber FROM deleted;

	IF NOT (EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'Department_Deleted'))
	BEGIN
		CREATE TABLE Department_Deleted(
			Dname VARCHAR(15) NOT NULL,
			Dnumber INT NOT NULL,
			Mgr_ssn CHAR(9) NOT NULL,
			Mgr_start_date DATE,
			PRIMARY KEY (Dnumber),
		);
		INSERT INTO Department_Deleted SELECT * FROM deleted;
		DELETE FROM DEPARTMENT WHERE Dnumber = @Dnumber;
	END
	ELSE
	BEGIN
		INSERT INTO Department_Deleted SELECT * FROM deleted;
		DELETE FROM DEPARTMENT WHERE Dnumber = @Dnumber;
	END
END
GO

--Teste
SELECT * FROM DEPARTMENT;
INSERT INTO DEPARTMENT VALUES('Teste', 7, 21312332 , '2015-08-02');
INSERT INTO DEPARTMENT VALUES('Teste2', 8,  21312332, '2015-08-02');
DELETE FROM DEPARTMENT WHERE Dnumber = 7;
DELETE FROM DEPARTMENT WHERE Dnumber = 8;
SELECT * FROM Department_Deleted;

/*
	A diferença na implementação vem na medida em que o trigger AFTER não necessita da query delete enquanto que o instead precisa
	
	O trigger instead of faz "override" à operação na medida em que adiciona funcionalidades extra à mesma (operação de delete tem a funcionaliade
	extra de inserir na tabela department_deleted
	
	O trigger after é disparado após a operação e faz o que foi especificado no final da mesma ter sido concluida	
*/




-- I)
/*
	Stored Procedures vs UDFs
		Ambos apresentam os mesmos benefícios na medida em que não precisasm de ser recompilados 
		cada vez que são executados e são ambos guardados na memória cache na primeira vez que são executados (execução mais rápida).

		SPs podem ou não retornar mais que um valor, contrariamente às UDFs que apenas retornam um valor.
		SPs não  suportam cláusulas SELECT, WHERE ou HAVING enquanto que as UDFs sim.
		SPs suportam excepções enquanto que UDFs não.
		SPs podem receber parâmetros de entrada e de saída, enquanto que as UDFs apenas podem receber de entrada.
		UDFs não permitem tabelas temporárias, mas SPs sim.

	- Quando é necessário passar  parâmetro
*/