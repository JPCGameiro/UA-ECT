CREATE SCHEMA G4_RENT_CAR;
GO

CREATE TABLE G4_RENT_CAR.Cliente(
	nome		VARCHAR(60)					NOT NULL,
	NIF			INT	CHECK(NIF>0)			NOT NULL,
	endereco	VARCHAR(150)				NOT NULL,
	numCarta	INT	IDENTITY(1,1) UNIQUE	NOT NULL,

	PRIMARY KEY (NIF)
);


CREATE TABLE G4_RENT_CAR.Balcao(
	numero		INT	CHECK(numero>0)	NOT NULL,
	nome		VARCHAR(60)			NOT NULL,
	endereco	VARCHAR(150)		NOT NULL,

	PRIMARY KEY (numero)
);


CREATE TABLE G4_RENT_CAR.TipoVeiculo(
	codigo			INT	CHECK(codigo>0) 	NOT NULL,
	arcondicionado	BIT						NOT NULL,
	designacao		VARCHAR(50),

	PRIMARY KEY (codigo)
);


CREATE TABLE G4_RENT_CAR.Veiculo(
	matricula			VARCHAR(8)			NOT NULL,	
	marca				VARCHAR(60)			NOT NULL,
	ano					INT	CHECK(ano>0)	NOT NULL,
	cod_tipo_veiculo	INT					NOT NULL,

	PRIMARY KEY (matricula),
	FOREIGN KEY (cod_tipo_veiculo) REFERENCES G4_RENT_CAR.TipoVeiculo(codigo)
);


CREATE TABLE G4_RENT_CAR.Aluguer(
	numero				INT	IDENTITY(1,1)	NOT NULL,
	duracao				INT					NOT NULL,
	a_data				DATE				NOT NULL,
	client_nif			INT					NOT NULL,
	num_balcao			INT					NOT NULL,
	matricula_veiculo	VARCHAR(8)			NOT NULL,

	PRIMARY KEY (numero),
	FOREIGN KEY (client_nif) REFERENCES G4_RENT_CAR.Cliente(NIF),
	FOREIGN KEY (num_balcao) REFERENCES G4_RENT_CAR.Balcao(numero),
	FOREIGN KEY (matricula_veiculo) REFERENCES G4_RENT_CAR.Veiculo(matricula)
);


CREATE TABLE G4_RENT_CAR.Similaridade(
	codigo_tipo1	INT				NOT NULL,
	codigo_tipo2	INT				NOT NULL,

	PRIMARY KEY (codigo_tipo1, codigo_tipo2),
	FOREIGN KEY (codigo_tipo1) REFERENCES G4_RENT_CAR.TipoVeiculo(codigo),
	FOREIGN KEY (codigo_tipo2) REFERENCES G4_RENT_CAR.TipoVeiculo(codigo)
);


CREATE TABLE G4_RENT_CAR.Ligeiro(
	cod_tipo		INT							NOT NULL,
	numLugares		INT	CHECK(numLugares>0)		NOT NULL,
	portas			INT	CHECK(portas>0)			NOT NULL,
	combustivel		VARCHAR(50)	,

	PRIMARY KEY (cod_tipo),
	FOREIGN KEY (cod_tipo) REFERENCES G4_RENT_CAR.TipoVeiculo(codigo)
);


CREATE TABLE G4_RENT_CAR.Pesado(
	cod_tipo		INT					NOT NULL,
	peso			INT	CHECK(peso>0)	NOT NULL,
	passageiros		INT					NOT NULL,

	PRIMARY KEY (cod_tipo),
	FOREIGN KEY (cod_tipo) REFERENCES G4_RENT_CAR.TipoVeiculo(codigo)
);