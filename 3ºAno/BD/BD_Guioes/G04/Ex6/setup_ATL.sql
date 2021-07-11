--create schema ATL;

create table ATL.Professor(
noCC				char(12)			not null,
nome				varchar(150),
morada				varchar(150),
email				varchar(100)		not null,
noFuncionario		char(12)			not null,
dataNasc			date				not null,
telefone			char(15),
primary key(noCC)
);

create table ATL.Turma(
id					char(12)			not null,
nuMaxAlunos			int,
designação			varchar(15),
cc_prof				char(12)			not null,
primary key(id),
foreign key(cc_prof) references ATL.Professor(noCC)
);

create table ATL.AnoLetivo(
ano					int					not null,
id_turma			char(12)			not null,
primary key(id_turma, ano),
foreign key(id_turma) references ATL.Turma(id)
);

create table ATL.EncEducacao(
noCC				char(12)			not null,
nome				varchar(150)		not null,
morada				varchar(150)		not null,
dataNasc			date,
telefone			varchar(15),
email				varchar(100)		not null,
primary key(noCC),
);

create table ATL.Aluno(
noCC				char(12)			not null,
nome				varchar(150)		not null,
morada				varchar(150)		not null,
dataNasc			date,
telefone			varchar(15),
email				varchar(100)		not null,
cc_EncEdu			char(12)			not null,
id_turma			char(12)			not null,
primary key(noCC),
foreign key(cc_EncEdu) references ATL.EncEducacao(noCC),
foreign key(id_turma) references ATL.Turma(id)
);

create table ATL.Pessoa(
noCC				char(12)			not null,
nome				varchar(150)		not null,
morada				varchar(150)		not null,
dataNasc			date,
telefone			varchar(15),
email				varchar(100)		not null,
primary key(noCC)
);

create table ATL.Entrega(
cc_pessoa			char(12)			not null,
cc_aluno			char(12)			not null,
primary key(cc_pessoa, cc_aluno),
foreign key(cc_pessoa) references ATL.Pessoa(noCC),
foreign key(cc_aluno) references ATL.Aluno(noCC)
);

create table ATL.Atividade(
id					char(12)			not null,
custo				int					not null,
designacao			varchar(150),
primary key(id)
);

-- turma tem atvidade
create table ATL.Tem(
id_turma			char(12)			not null,
id_atividade		char(12)			not null,
primary key(id_turma, id_atividade),
foreign key(id_turma) references ATL.Turma(id),
foreign key(id_atividade) references ATL.Atividade(id)
);

create table ATL.Frequenta(
id_atividade		char(12)			not null,
cc_aluno			char(12)			not null,
primary key(id_atividade, cc_aluno),
foreign key(id_atividade) references ATL.Atividade(id),
foreign key(cc_aluno) references ATL.Aluno(noCC)
);