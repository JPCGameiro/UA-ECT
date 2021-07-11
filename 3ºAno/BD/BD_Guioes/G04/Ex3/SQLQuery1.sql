CREATE SCHEMA G4_GESTAO_STOCKS;
GO

CREATE TABLE G4_GESTAO_STOCKS.Armazem(
	codigo			INT CHECK (codigo>0)	NOT NULL,
	nome			VARCHAR(90)				NOT NULL,
	localizacao		VARCHAR(100)			NOT NULL,

	PRIMARY KEY (codigo)
);

CREATE TABLE G4_GESTAO_STOCKS.Produto(
	codigo				INT CHECK (codigo>0)				NOT NULL,
	nome				VARCHAR(90)							NOT NULL,
	preco				DECIMAL(10, 2),
	preco_quantidade	INT CHECK(preco_quantidade >= 0),
	quantidade			INT CHECK(quantidade >= 0 )			NOT NULL,
	codigo_armazem		INT									NOT NULL,

	PRIMARY KEY (codigo),
	FOREIGN KEY (codigo_armazem) REFERENCES G4_GESTAO_STOCKS.Armazem(codigo)
);

CREATE TABLE G4_GESTAO_STOCKS.TipoFornecedor(
	codigo_interno		INT				NOT NULL,
	designacao			VARCHAR(90)		NOT NULL,

	PRIMARY KEY (codigo_interno)
);

CREATE TABLE G4_GESTAO_STOCKS.Fornecedor(
	NIF					INT				NOT NULL,
	nome				VARCHAR(90)		NOT NULL,
	FAX					INT,
	endereco			VARCHAR(100),
	cod_TipoFornecedor	INT				NOT NULL,

	PRIMARY KEY (NIF),
	FOREIGN KEY (cod_TipoFornecedor) REFERENCES G4_GESTAO_STOCKS.TipoFornecedor(codigo_interno)
);

CREATE TABLE G4_GESTAO_STOCKS.Encomenda(
	numEncomenda		INT		NOT NULL,
	encData				DATE	NOT NULL,
	NIFfornecedor		INT		NOT NULL,

	PRIMARY KEY (numEncomenda),
	FOREIGN KEY (NIFfornecedor) REFERENCES G4_GESTAO_STOCKS.Fornecedor(NIF)
);

CREATE TABLE G4_GESTAO_STOCKS.Contem(
	cod_produto			INT					NOT NULL,
	num_encomenda		INT					NOT NULL,
	quantidade			INT					NOT NULL,
	preco				DECIMAL(10, 2)		NOT NULL,

	PRIMARY KEY (cod_produto, num_encomenda),
	FOREIGN KEY (cod_produto) REFERENCES G4_GESTAO_STOCKS.Produto(codigo),
	FOREIGN KEY (num_encomenda) REFERENCES G4_GESTAO_STOCKS.Encomenda(numEncomenda)
);

CREATE TABLE G4_GESTAO_STOCKS.TaxaIva(
	valoriva		INT CHECK (valoriva in (23,13,6))	NOT NULL,
	cod_produto		INT CHECK (cod_produto>0)			NOT NULL,

	PRIMARY KEY (valoriva, cod_produto),
	FOREIGN KEY (cod_produto) REFERENCES G4_GESTAO_STOCKS.Produto(codigo),
);

CREATE TABLE G4_GESTAO_STOCKS.CondicoesPagamento(
	condicao_pagamento		VARCHAR(7) CHECK (condicao_pagamento in ('pronto', '30 dias', '60 dias')) NOT NULL,
	NIF_fornecedor			INT		NOT NULL,

	PRIMARY KEY	(condicao_pagamento, NIF_fornecedor),
	FOREIGN KEY (NIF_fornecedor) REFERENCES G4_GESTAO_STOCKS.Fornecedor(NIF)
);
