CREATE SCHEMA G4_CONFERENCIAS;
GO

CREATE TABLE G4_CONFERENCIAS.Instituicao(
	endereco	VARCHAR(100)	NOT NULL,
	nome		VARCHAR(80)		NOT NULL,

	PRIMARY KEY (endereco)
);

CREATE TABLE G4_CONFERENCIAS.Pessoa(
	email					VARCHAR(50)		NOT NULL,
	endereco_instituicao	VARCHAR(100)	NOT NULL,
	nome					VARCHAR(80)		NOT NULL,

	PRIMARY KEY(email),
	FOREIGN KEY (endereco_instituicao) REFERENCES G4_CONFERENCIAS.Instituicao(endereco)
);

CREATE TABLE G4_CONFERENCIAS.Artigo(
	num_registo		INT CHECK (num_registo>0)	NOT NULL,
	titulo			VARCHAR(90)					NOT NULL,

	PRIMARY KEY (num_registo)
);

CREATE TABLE G4_CONFERENCIAS.Autor(
	email		VARCHAR(50)		NOT NULL,
	nome		VARCHAR(80)		NOT NULL,

	PRIMARY KEY (email),
	FOREIGN KEY (email) REFERENCES G4_CONFERENCIAS.Pessoa(email)
);

CREATE TABLE G4_CONFERENCIAS.Escreve(
	email_autor		VARCHAR(50)					NOT NULL,
	num_artigo		INT	CHECK (num_artigo>0)	NOT NULL,

	PRIMARY KEY (email_autor, num_artigo),
	FOREIGN KEY (email_autor) REFERENCES G4_CONFERENCIAS.Autor(email),
	FOREIGN KEY (num_artigo) REFERENCES G4_CONFERENCIAS.Artigo(num_registo)
);

CREATE TABLE G4_CONFERENCIAS.Estudante(
	email				VARCHAR(50)			NOT NULL,
	loc_comprovativo	VARCHAR(100)		NOT NULL,
	morada				VARCHAR(100)		NOT NULL,	
	data_incricacao		DATE				NOT NULL,	

	PRIMARY KEY (email),
	FOREIGN KEY (email) REFERENCES G4_CONFERENCIAS.Pessoa(email)
);

CREATE TABLE G4_CONFERENCIAS.NaoEstudante(
	email				VARCHAR(50)						NOT NULL,
	ref_tranf_bancaria	INT	CHECK(ref_tranf_bancaria>0)	NOT NULL,
	morada				VARCHAR(100)					NOT NULL,	
	data_incricacao		DATE							NOT NULL,

	PRIMARY KEY (email),
	FOREIGN KEY (email) REFERENCES G4_CONFERENCIAS.Pessoa(email)
);
