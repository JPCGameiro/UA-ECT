
--create database EXP3;
--use EXP3;

CREATE TABLE EMPLOYEE (
	Fname VARCHAR(15) NOT NULL,
	Minit CHAR,
	Lname VARCHAR(15) NOT NULL,
	Ssn CHAR(9) NOT NULL,
	Bdate DATE,
	Address VARCHAR(30),
	Sex CHAR,
	Salary DECIMAL(10,2),
	Super_ssn CHAR(9),
	Dno INT NOT NULL,
	PRIMARY KEY (Ssn),
	FOREIGN KEY (Super_ssn) REFERENCES EMPLOYEE(Ssn),
);

CREATE TABLE DEPARTMENT(
	Dname VARCHAR(15) NOT NULL,
	Dnumber INT NOT NULL,
	Mgr_ssn CHAR(9) NOT NULL,
	Mgr_start_date DATE,
	PRIMARY KEY (Dnumber),
	UNIQUE (Dname),
	FOREIGN KEY (Mgr_ssn) REFERENCES EMPLOYEE(Ssn) 
);

CREATE TABLE DEPT_LOCATIONS(
	Dnumber INT NOT NULL,
	Dlocation VARCHAR(15) NOT NULL,
	PRIMARY KEY (Dnumber, Dlocation),
	FOREIGN KEY (Dnumber) REFERENCES DEPARTMENT(Dnumber) 
);

CREATE TABLE Project(
	Pname VARCHAR(15) NOT NULL,
	Pnumber INT NOT NULL,
	Plocation VARCHAR(15),
	Dnum INT NOT NULL,
	PRIMARY KEY (Pnumber),
	UNIQUE (Pname),
	FOREIGN KEY (Dnum) REFERENCES DEPARTMENT(Dnumber)
);

CREATE TABLE Works_on(
	Essn CHAR(9) NOT NULL,
	Pno INT NOT NULL,
	Hours DECIMAL(3,1) NOT NULL,
	PRIMARY KEY (Essn, Pno),
	FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn),
	FOREIGN KEY (Pno) REFERENCES PROJECT(Pnumber)
);

CREATE TABLE Dependent(
	Essn CHAR(9) NOT NULL,
	Dependent_name VARCHAR(15) NOT NULL,
	Sex CHAR,
	Bdate DATE,
	Relationship VARCHAR(8),
	PRIMARY KEY (Essn, Dependent_name),
	FOREIGN KEY (Essn) REFERENCES EMPLOYEE(Ssn)
);

INSERT INTO employee VALUES ('Paula','A','Sousa',183623612,'2001-08-11','Rua da FRENTE','F',1450.00,NULL,3);
INSERT INTO employee VALUES('Carlos','D','Gomes',21312332 ,'2000-01-01','Rua XPTO','M',1200.00,NULL,1);
INSERT INTO employee VALUES('Juliana','A','Amaral',321233765,'1980-08-11','Rua BZZZZ','F',1350.00,NULL,3);
INSERT INTO employee VALUES('Maria','I','Pereira',342343434,'2001-05-01','Rua JANOTA','F',1250.00,21312332,2)
INSERT INTO employee VALUES('Joao','G','Costa',41124234 ,'2001-01-01','Rua YGZ','M',1300.00,21312332,2);
INSERT INTO employee VALUES('Ana','L','Silva',12652121 ,'1990-03-03','Rua ZIG ZAG','F',1400.00,21312332,2);

INSERT INTO department VALUES('Investigacao',1,21312332 ,'2010-08-02');
INSERT INTO department VALUES('Comercial',2,321233765,'2013-05-16');
INSERT INTO department VALUES('Logistica',3,41124234 ,'2013-05-16');
INSERT INTO department VALUES('Recursos Humanos', 4,12652121,'2014-04-02');

INSERT INTO dependent VALUES (21312332 ,'Maria Costa','F','1990-10-05','Neto');
INSERT INTO dependent VALUES (21312332 ,'Joana Costa','F','2008-04-01', 'Filho');
INSERT INTO dependent VALUES (21312332 ,'Rui Costa','M','2000-08-04','Neto');
INSERT INTO dependent VALUES (321233765,'Filho Lindo','M','2001-02-22','Filho');
INSERT INTO dependent VALUES (342343434,'Rosa Lima','F','2006-03-11','Filho');
INSERT INTO dependent VALUES (41124234 ,'Ana Sousa','F','2007-04-13','Neto');
INSERT INTO dependent VALUES (41124234 ,'Gaspar Pinto','M','2006-02-08','Sobrinho');

INSERT INTO dept_locations VALUES (2, 'Aveiro');
INSERT INTO dept_locations VALUES (3, 'Coimbra');

INSERT INTO project VALUES ('Aveiro Digital',1,'Aveiro',3);
INSERT INTO project VALUES ('BD Open Day',2,'Espinho',2);
INSERT INTO project VALUES ('Dicoogle',3,'Aveiro',3);
INSERT INTO project VALUES ('GOPACS',4,'Aveiro',3);


INSERT INTO works_on VALUES (183623612,1,20.0);
INSERT INTO works_on VALUES (183623612,3,10.0);
INSERT INTO works_on VALUES (21312332 ,1,20.0);
INSERT INTO works_on VALUES (321233765,1,25.0);
INSERT INTO works_on VALUES (342343434,1,20.0);
INSERT INTO works_on VALUES (342343434,4,25.0);
INSERT INTO works_on VALUES (41124234 ,2,20.0);
INSERT INTO works_on VALUES (41124234 ,3,30.0);

ALTER TABLE EMPLOYEE ADD FOREIGN KEY (Dno) REFERENCES DEPARTMENT(Dnumber);

/*
go
create proc procedure1 @ssn as CHAR(9)
as
	delete from dependent where Essn = @snn
	delete from works_on where Essn = @snn
	update department set Mgr_ssn = null where Mgr_ssn = @snn
	update employee set super_ssn = null where employee.ssn = @snn
	delete from employee where employee.ssn = @snn
go



go
create proc p2 @ as 
as 
	*/