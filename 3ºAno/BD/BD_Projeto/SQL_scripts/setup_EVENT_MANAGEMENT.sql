/*	
** script to instaciate Database for bd project
**
** Pedro Abreu & João Gameiro
 */
 use p2g5

 go

-- schema
if not exists (select * from sys.schemas where name='EM') -- EM -> EVENT_MANAGEMENT
begin
  exec('create schema EM;')
end

go

-- types
if not exists( select * from sys.types where name = 'id')
begin
	create type id from varchar(20)
end

if not exists( select * from sys.types where name = 'str150')
begin
	create type str150 from varchar(150)
end

if not exists( select * from sys.types where name = 'str250')
begin
	create type str250 from varchar(250)
end

if not exists( select * from sys.types where name = 'cc')
begin
	create type cc from varchar(12)
end

if not exists( select * from sys.types where name = 'email')
begin
	create type email from varchar(100)
end

if not exists( select * from sys.types where name = 'telefone')
begin
	create type telefone from varchar(12)
end

go 
--tables
create table EM.PROMOTOR(
numCC				cc				not null,
nome				str150,
email				email,			--unique?
telefone			telefone,
primary key (numCC)
)


create table EM.STAGEMANAGER(
numCC				cc				not null,
nome				str150,
email				email,			--unique?
telefone			telefone,
primary key(numCC)
)


create table EM.EVENTO(
id					id				not null,
nome				str150			not null,
numdias				int									check (numdias > 0),
dataini				date			not null,
datafim				date,
numbilhetes			int				DEFAULT 0,
cc_promotor			cc,
dataproposta		date,
cc_stageManager		cc,
primary key (id),
foreign key (cc_promotor) references EM.PROMOTOR(numCC) on delete set null on update cascade,
foreign key (cc_stageManager) references EM.STAGEMANAGER(numCC) on delete set null on update cascade,
check (dataproposta < dataini), -- se ao introduzir uma destas for null, a check rebenta?
check (dataini <= datafim)
)


create table EM.BANDA(
id					id				not null,
nome				str150			not null,
telefone			telefone,
email				email,
numElem				int,
genero				varchar(200)
primary key(id)
)

create table EM.SOUNDCHECK(
id					id				not null,
duracao				time,
datatimeini			datetime,
primary key(id)
)

create table EM.CONCERTO(
id					id				not null,
datatimeini			datetime		not null,
duracao				time,
id_banda			id,
id_evento			id,
id_soundcheck		id,
primary key(id),
foreign key (id_banda) references EM.BANDA(id) on delete set null on update cascade,
foreign key(id_evento) references EM.EVENTO(id) on delete set null on update cascade,
foreign key(id_soundcheck) references EM.SOUNDCHECK(id) on delete set null on update cascade,
) -- como check/ver que datatime inicio concerto é dps datetime inicio do evento?

create table EM.COMITIVA(
id					id				not null,
email				email,
telefone			telefone,
id_banda			id,
primary key(id),
foreign key(id_banda) references EM.BANDA(id) on delete set null on update cascade
)

create table EM.PESSOA(
numCC				cc				not null,
email				email,
nome				str150,
sexo				char(1),
id_comitiva			id
primary key (numCC),
foreign key (id_comitiva) references EM.COMITIVA(id) on delete set null on update cascade
)

create table EM.ACOMPANHANTE(
numCC				cc				not null,
tipoAcomp			str150,
primary key (numCC),
foreign key (numCC) references EM.PESSOA(numCC) on delete cascade on update cascade
)

create table EM.TECNICO(
numCC				cc				not null,
tipoTecn			str150,
primary key (numCC),
foreign key (numCC) references EM.PESSOA(numCC) on delete cascade on update cascade
)

create table EM.MUSICO(
numCC				cc				not null,
nomeArst			str150,
id_banda			id,
primary key(numCC),
foreign key(id_banda) references EM.BANDA(id),
foreign key(numCC) references EM.PESSOA(numCC) on delete cascade on update cascade

)

create table EM.INSTRUMENTO(
id					id				not null,
marca				str150,
modelo				str150,
musicoCC			cc,
famInstrumento		str150,
primary key(id),
foreign key (musicoCC) references EM.MUSICO(numCC) on delete set null on update cascade
)

create table EM.EMPRESACATERING(
nif					char(9)			not null			check (nif like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
nome				str150,
email				email,			--unique?
telefone			telefone,
endereço			str250,
primary key (nif)
)

create table EM.REFEICAO(
id					id				not null,
id_evento			id,
nif_empresa			char(9)			check (nif_empresa like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]') default '000000000',
prato				str250,
sobremesa			str250,	
bebida				str250,
primary key (id),
foreign key (id_evento) references EM.EVENTO(id) on delete set null on update cascade,
foreign key (nif_empresa) references EM.EMPRESACATERING(nif) on delete set default on update cascade
);





