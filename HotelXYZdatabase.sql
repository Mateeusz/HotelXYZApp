DROP VIEW IF EXISTS displayAllEmployees ;
DROP VIEW IF EXISTS displayAllReservations;
DROP VIEW IF EXISTS displayAllClients;
DROP VIEW IF EXISTS displayClient;
DROP VIEW IF EXISTS displayEmployee;
DROP VIEW IF EXISTS displayReservation;

DROP TABLE IF EXISTS personalData;
DROP TABLE IF EXISTS addressData;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS manager;
DROP TABLE IF EXISTS receptionist;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS hotel;
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS roomReservation;
DROP TABLE IF EXISTS employee;

DROP SEQUENCE IF EXISTS personalDataSequence;
DROP SEQUENCE IF EXISTS addressDataSequence;
DROP SEQUENCE IF EXISTS clientSequence;
DROP SEQUENCE IF EXISTS employeeSequence;
DROP SEQUENCE IF EXISTS hotelSequence;
DROP SEQUENCE IF EXISTS managerSequence;
DROP SEQUENCE IF EXISTS receptionistSequence;
DROP SEQUENCE IF EXISTS reservationSequence;
DROP SEQUENCE IF EXISTS roomSequence;

DROP ROLE IF EXISTS manager;
DROP ROLE IF EXISTS dbAdmin;
DROP ROLE IF EXISTS receptionist;
DROP ROLE IF EXISTS client;

--DROP FUNCTION setReservationDate();
DROP FUNCTION IF EXISTS setReservationDate();





      CREATE SEQUENCE IF NOT EXISTS personalDataSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS addressDataSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS clientSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS employeeSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS managerSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS receptionistSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS reservationSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS hotelSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

       CREATE SEQUENCE IF NOT EXISTS roomSequence
         INCREMENT 1
         MINVALUE 1
         MAXVALUE 999999
         START 1
         CACHE 1;

 CREATE TABLE personalData (
   personalDataId  integer DEFAULT nextval('personalDataSequence'),
   firstName       VARCHAR (15) NOT NULL ,
   lastName        VARCHAR (40) NOT NULL ,
   birtDate        DATE NOT NULL ,
   phoneNumber     VARCHAR (15) NOT NULL ,
   pesel           VARCHAR (11) NOT NULL ,
   eMail           VARCHAR (40),
   password        VARCHAR (30) NOT NULL ,
   login           VARCHAR (30) NOT NULL
 ) ;

ALTER TABLE personalData ADD CONSTRAINT personalDataPK PRIMARY KEY (personalDataId) ;

CREATE TABLE addressData (
  addressDataId   integer DEFAULT nextval('addressDataSequence'),
  streetNumber    NUMERIC (6) NOT NULL ,
  streetName      VARCHAR (60) ,
  city            VARCHAR (255) NOT NULL ,
  zipCode         VARCHAR (6) NOT NULL ,
  apartmentNumber NUMERIC (5)
) ;

ALTER TABLE addressData ADD CONSTRAINT addressDataPK PRIMARY KEY (addressDataId);

CREATE TABLE client (
  clientId       integer DEFAULT nextval('clientSequence'),
  personalDataId NUMERIC (11) NOT NULL ,
  addressDataId  NUMERIC (11) NOT NULL
) ;
ALTER TABLE client ADD CONSTRAINT clientPK PRIMARY KEY (clientId);

CREATE TABLE employee (
  employeeId             integer DEFAULT nextval('employeeSequence'),
  hireDate               DATE NOT NULL ,
  salary                 NUMERIC (6) NOT NULL ,
  workTime               NUMERIC (3) NOT NULL ,
  job                    VARCHAR (30) ,
  personalDataId 		     NUMERIC (11) NOT NULL ,
  addressDataId  		     NUMERIC (11) NOT NULL
) ;

ALTER TABLE employee ADD CONSTRAINT employeePK PRIMARY KEY (employeeId);

CREATE TABLE manager (
  managerId              integer DEFAULT nextval('managerSequence'),
  employeeId      		   NUMERIC (11) NOT NULL
) ;

ALTER TABLE manager ADD CONSTRAINT managerPK PRIMARY KEY (managerId);


CREATE TABLE receptionist (
  receptionistId         integer DEFAULT nextval('receptionistSequence'),
  employeeId             NUMERIC (11) NOT NULL
) ;

ALTER TABLE receptionist ADD CONSTRAINT receptionistPK PRIMARY KEY (receptionistId);


CREATE TABLE reservation (
  reservationId             integer DEFAULT nextval('reservationSequence'),
  beginDate                 DATE NOT NULL ,
  endDate                   DATE NOT NULL ,
  reservationDate           DATE ,
  checkIn                   DATE ,
  checkOut                  DATE ,
  clientId       NUMERIC (11) NOT NULL ,
  recepcionistId NUMERIC (11) ,
  managerId      NUMERIC (11)
);
ALTER TABLE reservation ADD CONSTRAINT reservationPK PRIMARY KEY (reservationId);


CREATE TABLE hotel (
  hotelId           integer DEFAULT nextval('hotelSequence'),
  numberOfRooms     NUMERIC (3) NOT NULL
) ;
ALTER TABLE hotel ADD CONSTRAINT hotelPK PRIMARY KEY (hotelID);


CREATE TABLE room (
  roomId           integer DEFAULT nextval('roomSequence'),
  type             VARCHAR (25) NOT NULL ,
  roomNumber       NUMERIC (3) NOT NULL ,
  maxGuestNumber   NUMERIC (1) NOT NULL ,
  equipment        VARCHAR (255) ,
  status           CHAR (1) DEFAULT ('W') ,
  HotelId          NUMERIC (11) NOT NULL DEFAULT 1
);

ALTER TABLE room ADD CONSTRAINT roomPK PRIMARY KEY (roomId);

CREATE TABLE roomReservation (
  roomId        NUMERIC (11) NOT NULL ,
  reservationId NUMERIC (11) NOT NULL
) ;

ALTER TABLE roomReservation ADD CONSTRAINT roomReservationPK PRIMARY KEY (roomId, reservationId);


CREATE FUNCTION setReservationDate()
  RETURNS trigger
AS $setReservationDate$
    BEGIN
		new.reservationDate := current_date;

		return new;
	end;
$setReservationDate$ LANGUAGE plpgsql;

CREATE TRIGGER reservationInsertTrigger
BEFORE INSERT
ON reservation
FOR EACH ROW
EXECUTE PROCEDURE setReservationDate();

CREATE VIEW displayAllReservations AS
SELECT
res.reservationid,
per.firstname,
per.lastname,
res.begindate,
res.enddate,
res.checkin
FROM reservation res, personaldata per
where reservationid = personaldataid;

CREATE VIEW displayReservation AS
SELECT
res.reservationid,
per.firstname,
per.lastname,
per.pesel,
res.begindate,
res.enddate,
res.reservationdate,
res.checkin,
res.checkout,
rom.roomnumber
FROM reservation res, personaldata per, room rom
where res.reservationid = per.personaldataid
AND res.reservationid = rom.roomid;

CREATE VIEW displayAllClients AS
SELECT
per.firstname,
per.lastname,
per.pesel
FROM personaldata per, client
WHERE per.personaldataid = clientid;

CREATE VIEW displayClient AS
SELECT
per.firstname,
per.lastname,
per.pesel,
per.birtdate,
per.phonenumber,
per.email,
per.login
FROM personaldata per, client
WHERE per.personaldataid = clientid;

CREATE VIEW displayAllEmployees AS
SELECT
per.firstname,
per.lastname,
emp.salary,
emp.worktime,
emp.job
FROM personaldata per, employee emp
WHERE per.personaldataid = employeeid;

CREATE VIEW displayEmployee AS
SELECT
per.firstname,
per.lastname,
per.pesel,
per.birtdate,
per.phonenumber,
per.email,
per.login,
emp.salary,
emp.hiredate,
emp.worktime,
emp.job
FROM personaldata per, employee emp
WHERE per.personaldataid = employeeid;

CREATE ROLE dbAdmin;
CREATE ROLE manager;
CREATE ROLE receptionist;
CREATE ROLE client;

    GRANT ALL
    ON
    addressDataSequence,
    personalDataSequence,
    clientSequence,
    employeeSequence,
    hotelSequence,
    managerSequence,
    receptionistSequence,
    reservationSequence,
    roomSequence,
    personalData,
    addressData,
    client,
    employee,
    room,
    hotel,
    reservation,
    receptionist,
    roomReservation,
    manager,
    displayAllEmployees,
    displayClient,
    displayAllReservations,
    displayAllClients,
    displayEmployee,
    displayReservation

    TO dbAdmin;

    GRANT SELECT, INSERT
    ON
    personalData,
    addressData,
    client,
    reservation,
    displayReservation,
    reservationSequence,
    addressDataSequence,
    personalDataSequence,
    clientSequence
    TO client;

    GRANT SELECT, INSERT, UPDATE, DELETE
    ON
    reservation,
    addressData,
    personalData,
    client,
    displayClient,
    displayAllReservations,
    displayAllClients,
    displayReservation,
    addressDataSequence,
    personalDataSequence,
    clientSequence
    TO receptionist;

    GRANT  SELECT, INSERT, UPDATE, DELETE
    ON
    personalData,
    addressData,
    client,
    employee,
    room,
    hotel,
    reservation,
    receptionist,
    roomReservation,
    manager,
    displayAllEmployees,
    displayClient,
    displayAllReservations,
    displayAllClients,
    displayEmployee,
    displayReservation,
    addressDataSequence,
    personalDataSequence,
    clientSequence,
    employeeSequence,
    hotelSequence,
    managerSequence,
    receptionistSequence,
    reservationSequence,
    roomSequence
    TO manager;

Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Jakub', 'Gadzala', '1997-01-10', 532675987,97011067453, 'kuba123@gmail.com', 'kuba', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Andrzej', 'Duda', '1988-02-22', 546783982,88022254678, 'andrzej12@gmail.com', 'andrzej', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Rafał', 'Kmiecik', '1970-12-24', 987463478,70122476123, 'rafal22@gmail.com', 'rafal', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Patryk', 'Zwal', '1991-04-17', 364789745,91041784435, 'patrynio@gmail.com', 'patryk', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Kamil', 'Malina', '1988-01-10', 515237766,88011099887, 'kamil12@gmail.com', 'kamil', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Zbigniew', 'Polak', '1963-03-12', 987234574,63031223556, 'zbysio@gmail.com', 'zbigniew', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Leszek', 'Dąb', '1945-09-29', 518978654,45092923432, 'lenio@gmail.com', 'leszek', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Janusz', 'Wąs', '1986-08-27', 812354765,06082744667, 'waskik69@gmail.com', 'janusz', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Kacper', 'Linkoln', '1967-07-11', 694567733,67071161496, 'rybak@gmail.com', 'kaceper', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Michał', 'Boruch', '1981-06-15', 512334457,81061545673, 'michalkichal@gmail.com', 'michal', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Mateusz', 'Wrocnowski', '1982-01-01', 513454589,82010189007, 'recepcionist@gmail.com', 'mateusz', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Bartosz', 'Wawryniuk', '1983-02-12', 846375873,83021200045, 'bartek123@gmail.com', 'bartosz', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Andrzej', 'Glik', '1983-05-22', 555487666,83052254632, 'adrjej123@gmail.com', 'andrzej1', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Joanna', 'Lewandowska', '1984-06-09', 897645222,84060934589, 'prawnik@gmail.com', 'joanna', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Patrycja', 'Kowal', '1986-07-18', 123887774,86071878960, 'kub3@gmail.com', 'patrycja', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Michalina', 'Wach', '1990-12-13', 536745876,90121311110, 'michalinka69@gmail.com', 'michalina', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Anna', 'Szpilka', '1991-02-22', 678985465,91022209876, 'szpila12@gmail.com', 'anna', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Ilona', 'Materla', '1965-07-20', 576988544,65072020134, 'bocian@gmail.com', 'ilona', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Katarzyna', 'Jankik', '1969-05-29', 213456887,69052934576, 'katarzyna@gmail.com', 'kasia', '12345678');
Insert Into personalData( firstName, lastName, birtDate, phoneNumber, pesel, eMail, password, login)
Values ( 'Elżbieta', 'Janikowska', '1968-01-01', 990997997,68010111223, 'janik@gmail.com', 'manager', 'manager');


Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 23, 'Malinowa', 'Wrocław', '54-130',8);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 68, 'Legnicka', 'Wrocław', '54-130',34);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 1, 'Marszałkowska', 'Wrocław', '54-130',12);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 12, 'Latawcowa', 'Wrocław', '54-130',45);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 7, 'Drzewieckiego', 'Wrocław', '54-130',16);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 78, 'Szybowcowa', 'Wrocław', '54-130',6);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 123, 'Lotników', 'Poznań', '60-101',7);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 22, 'Orzechowa', 'Poznań', '60-101',27);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 56, 'Świdnicka', 'Poznań', '60-101',28);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 33, 'Górna', 'Poznań', '60-101',1);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 99, 'Pruszkowska', 'Warszawa', '00-001',2);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 123, 'Płocka', 'Warszawa', '00-001',3);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 15, 'Żwirki Wigury', 'Warszawa', '00-001',7);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 5, 'Wawelska', 'Warszawa', '00-001',55);
Insert Into addressData( streetNumber, streetName, city, zipCode, apartmentNumber )
Values ( 42, 'Nowy świat', 'Warszawa', '00-001',19);

Insert Into addressData( streetNumber,  city, zipCode ) Values ( 176,  'Smolec', '55-080');
Insert Into addressData( streetNumber,  city, zipCode ) Values ( 188,  'Czemierniki', '21-306');
Insert Into addressData( streetNumber,  city, zipCode ) Values ( 129,  'Czemierniki', '21-306');
Insert Into addressData( streetNumber,  city, zipCode ) Values ( 240,  'Wyganów', '21-306');
Insert Into addressData( streetNumber,  city, zipCode ) Values ( 156,  'Wygnanów', '21-306');


Insert Into client( personalDataId, addressDataId) VALUES (1,1);
Insert Into client( personalDataId, addressDataId) VALUES (2,20);
Insert Into client( personalDataId, addressDataId) VALUES (3,3);
Insert Into client( personalDataId, addressDataId) VALUES (4,4);
Insert Into client( personalDataId, addressDataId) VALUES (5,5);
Insert Into client( personalDataId, addressDataId) VALUES (6,6);
Insert Into client( personalDataId, addressDataId) VALUES (7,11);
Insert Into client( personalDataId, addressDataId) VALUES (8,8);
Insert Into client( personalDataId, addressDataId) VALUES (9,9);
Insert Into client( personalDataId, addressDataId) VALUES (10,10);
Insert Into client( personalDataId, addressDataId) VALUES (12,12);
Insert Into client( personalDataId, addressDataId) VALUES (13,13);
Insert Into client( personalDataId, addressDataId) VALUES (14,14);
Insert Into client( personalDataId, addressDataId) VALUES (15,15);
Insert Into client( personalDataId, addressDataId) VALUES (16,16);
Insert Into client( personalDataId, addressDataId) VALUES (17,17);
Insert Into client( personalDataId, addressDataId) VALUES (18,18);
Insert Into client( personalDataId, addressDataId) VALUES (19,19);


Insert Into employee( hireDate, salary, workTime, job, personalDataId, addressDataId ) Values ( '2001-06-15', 10000, 180, 'Manager', 20, 2);
Insert Into employee( hireDate, salary, workTime, job, personalDataId, addressDataId ) Values ( '2001-06-15', 3200, 180, 'Receptionist', 11, 7);

Insert Into manager( employeeId) Values (1);

Insert Into receptionist ( employeeId) Values (2);

Insert Into reservation ( beginDate, endDate, reservationDate, clientId)
Values ( '2018-02-10', '2018-02-17', '2018-02-05', 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId, recepcionistId)
Values ( '2018-02-20', '2018-02-25', '2018-02-10', 2, 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId, recepcionistId)
Values ( '2018-02-12', '2018-02-22', '2018-02-08', 3, 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId, managerId)
Values ( '2018-03-01', '2018-03-20', '2018-02-28', 4, 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId, recepcionistId)
Values ( '2018-03-08', '2018-03-10', '2018-03-07', 5, 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId, recepcionistId)
Values ( '2018-03-12', '2018-02-21', '2018-03-10', 6, 1);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId)
Values ( '2018-03-22', '2018-03-31', '2018-03-20', 7);
Insert Into reservation ( beginDate, endDate, reservationDate, clientId)
Values ( '2018-03-28', '2018-04-05', '2018-03-26', 8);
Insert Into reservation ( beginDate, endDate, clientId, recepcionistId)
Values ( '2018-04-02', '2018-04-10', 9, 1);
Insert Into reservation ( beginDate, endDate, clientId, managerId)
Values ( '2018-04-10', '2018-04-17', 10, 1);

Insert Into hotel ( numberOfRooms) Values ( 50);


Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 001, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 002, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 003, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 004, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 005, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 006, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 007, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 008, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 111, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 112, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 113, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 114, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 115, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 116, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 117, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 221, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 222, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 223, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 224, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 225, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 226, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 227, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 338, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 339, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 340, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 446, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 447, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 448, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 449, 2, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'double', 450, 2, null);

Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 009, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 010, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 333, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 334, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 335, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 336, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 337, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 443, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 444, 1, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'single', 445, 1, null);

Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 118, 3, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 119, 3, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 120, 3, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 228, 3, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 229, 3, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'triple', 230, 3, null);

Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'appartment', 331, 4, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'appartment', 332, 4, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'appartment', 441, 4, null);
Insert Into room( type, roomNumber, maxGuestNumber, equipment  ) Values ( 'appartment', 442, 4, null);

Insert into roomReservation(roomId, reservationId) Values (1,1);
Insert into roomReservation(roomId, reservationId) Values (2,2);
Insert into roomReservation(roomId, reservationId) Values (31,3);
Insert into roomReservation(roomId, reservationId) Values (32,4);
Insert into roomReservation(roomId, reservationId) Values (41,5);
Insert into roomReservation(roomId, reservationId) Values (42,6);
Insert into roomReservation(roomId, reservationId) Values (47,7);
Insert into roomReservation(roomId, reservationId) Values (49,8);
Insert into roomReservation(roomId, reservationId) Values (34,9);
Insert into roomReservation(roomId, reservationId) Values (17,10);
