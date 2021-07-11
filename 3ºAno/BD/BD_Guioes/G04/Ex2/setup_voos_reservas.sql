--CREATE SCHEMA G4_RESERVAS_VOOS;

CREATE TABLE G4_RESERVAS_VOOS.Airport(
airport_code		CHAR(12)			NOT NULL,
city				VARCHAR(30),
statee				VARCHAR(30),
namee				VARCHAR(30)			not null,
Primary Key (airport_code)
); 

CREATE TABLE G4_RESERVAS_VOOS.Airplane_type(
type_namee			VARCHAR(30)			NOT NULL,
max_seats			INT					default 0,
company				VARCHAR(30),
PRIMARY KEY (type_namee)
); 


CREATE TABLE G4_RESERVAS_VOOS.Can_land(
airport_code		CHAR(12)			NOT NULL,
type_namee			VARCHAR(30)			NOT NULL,
PRIMARY KEY (airport_code, type_namee),
FOREIGN KEY (airport_code) REFERENCES G4_RESERVAS_VOOS.Airport(airport_code),
FOREIGN KEY (type_namee) REFERENCES G4_RESERVAS_VOOS.Airplane_type(type_namee)
);

create table G4_RESERVAS_VOOS.Airplane(
airplane_id			varchar(30)			not null,
total_num_seats		int					default 0			check (total_num_seats < 1000),
airplane_type		varchar(30)			not null,
primary key (airplane_id),
foreign key (airplane_type) references G4_RESERVAS_VOOS.Airplane_type(type_namee),
);

create table G4_RESERVAS_VOOS.Flight(
number_flight		int					not null,
airline				varchar(30)			not null,
weekdays			int,
primary key (number_flight),
);

create table G4_RESERVAS_VOOS.Flight_leg(
number_flight			int					not null,
leg_number				int					not null,
airport_code_departure	CHAR(12)			NOT NULL,
airport_code_arrival	CHAR(12)			NOT NULL,
sched_depart_time		datetime			not null,
sched_arrival_time		datetime			not null,
primary key(number_flight, leg_number),
foreign key(number_flight) references G4_RESERVAS_VOOS.Flight(number_flight), 
foreign key(airport_code_departure) references G4_RESERVAS_VOOS.Airport(airport_code),
foreign key(airport_code_arrival) references G4_RESERVAS_VOOS.Airport(airport_code),
check (sched_depart_time < sched_arrival_time),
);

create table G4_RESERVAS_VOOS.Leg_instance(
number_flight			int					not null,
leg_number				int					not null,
datee					date				not null,
num_avail_seats			int					default 0,
depart_airport			CHAR(12)			NOT NULL,
arrive_airport			CHAR(12)			NOT NULL,
depart_time				datetime			not null,
arrive_time				datetime			not null,
plane_id				varchar(30)			not null,
primary key(number_flight, leg_number, datee),
foreign key(number_flight, leg_number) references G4_RESERVAS_VOOS.Flight_leg(number_flight, leg_number),
foreign key(depart_airport) references G4_RESERVAS_VOOS.Airport(airport_code),
foreign key(arrive_airport) references G4_RESERVAS_VOOS.Airport(airport_code),
foreign key(plane_id) references G4_RESERVAS_VOOS.Airplane(airplane_id),
check (depart_time < arrive_time)
);


create table G4_RESERVAS_VOOS.Seat(
number_flight			int					not null,
leg_number				int					not null,
datee					date				not null,
seat_number				int					not null,
custumer_name			char(150)			not null,
cphone					int,
primary key(number_flight, leg_number, datee, seat_number),
foreign key(number_flight, leg_number, datee) references G4_RESERVAS_VOOS.Leg_instance(number_flight, leg_number, datee)
);

create table G4_RESERVAS_VOOS.Fare(
number_flight			int					not null,
code					char(30)			not null,
restricions				varchar(1000),
amount					int					default 0
primary key(number_flight, code),
foreign key(number_flight) references G4_RESERVAS_VOOS.Flight(number_flight)
);