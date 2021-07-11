--CREATE SCHEMA G4_Medicamentos;

CREATE TABLE G4_Medicamentos.Medico(
num_id				char(12)			not null,
nome				varchar(150)		not null,
especialidade		varchar(100	),
Primary Key (num_id)
);

CREATE TABLE G4_Medicamentos.Paciente(
num_utente			char(12)			not null,
nome				varchar(150)		not null,
datee				date,
endereço			varchar(150),
Primary Key (num_utente)
);

create table G4_Medicamentos.Farmacia(
nif					char(9)				not null,
nome				varchar(150)		not null,
telefone			varchar(20),
endereço			varchar(150)		not null,
primary key(nif)
);

CREATE TABLE G4_Medicamentos.Prescricao(
num_unico			char(12)			not null,
medico				char(12)			not null,
farmacia			char(9)				not null,
num_utente			char(12)			not null,
datee				date,			
datee_prescricao	date				not null,
primary key (num_unico, medico, farmacia, num_utente),
unique (num_unico),
foreign key (medico) references G4_Medicamentos.Medico(num_id),
foreign key (farmacia) references G4_Medicamentos.Farmacia(nif),
foreign key (num_utente) references G4_Medicamentos.Paciente(num_utente),
check(datee_prescricao < datee)
);

create table G4_Medicamentos.Farmaceutica(
num_reg_nacional	char(12)			not null,
nome				varchar(150),
telefone			varchar(20),
endereço			varchar(150),
primary key(num_reg_nacional)
);

create table G4_Medicamentos.Farmaco(
num_unico			char(12)			not null,
num_reg_nacional	char(12)			not null,
formula				varchar(1000),
nome_comercial		varchar(150),		
endereço			varchar(150),
primary key(num_unico, num_reg_nacional),
unique (num_unico),
foreign key(num_reg_nacional) references  G4_Medicamentos.Farmaceutica(num_reg_nacional),
);

--tem(prescricao tem farmacos)
create table G4_Medicamentos.Tem(
nome_farmaco		char(12)		not null,
num_unico_pres		char(12)			not null,
primary key(nome_farmaco, num_unico_pres),
foreign key(nome_farmaco) references G4_Medicamentos.Farmaco(num_unico),
foreign key(num_unico_pres) references G4_Medicamentos.Prescricao(num_unico)
);