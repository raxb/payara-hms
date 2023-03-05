create table if not exists PatientEntity(patient_id int not null, first_name varchar(255), last_name varchar(255), gender varchar(10), age int, email varchar(30), address_line1 varchar(100), address_line2 varchar(100), city varchar(20), state varchar(20), country varchar(20), zip_code varchar(20), last_appointment date, upcoming_appointment date, primary key (patient_id));

insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1001, 'Ben','Smith','MALE',36,'ben@smith.com','addressLine1','addressLine2','city','state','country','zip_code','2012-02-08T00:00:00Z','2023-03-05T08:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1002, 'Bruce','Banner','MALE',45,'bruce@banner.com','addressLine1','addressLine2','city','state','country','zip_code','2019-12-26T00:00:00Z','2023-03-05T10:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1003, 'Tony','Stark','MALE',58,'tony@stark.com','addressLine1','addressLine2','city','state','country','zip_code','2021-11-30T00:00:00Z','2023-03-06T22:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1004, 'Bruce','Wayne','MALE',50,'bruce@wayne.com','addressLine1','addressLine2','city','state','country','zip_code','2022-06-21T00:00:00Z','2023-06-04T00:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1005, 'Kent','Clark','MALE',55,'kent@clark.com','addressLine1','addressLine2','city','state','country','zip_code','2022-04-19T00:00:00Z','2023-03-07T00:00:00Z');

create table if not exists PharmacyEntity(item_id int not null, item_name varchar(255), item_quantity bigint, item_price decimal(6,2), primary key (item_id));
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2001, 'Ativan', 125, 10.50);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2002, 'Benzonatate', 150, 13.50);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2003, 'Ciprofloxacin', 10, 54.54);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2004, 'Doxycycline', 76, 85.74);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2005, 'Entresto', 53, 285.57);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2006, 'Fentanyl Patch', 68, 34.00);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2007, 'Gilenya', 548, 545.54);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2008, 'Hydrochlorothiazide', 264, 19.57);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2009, 'Omeprazole', 98, 2.32);
insert into PharmacyEntity (item_id, item_name, item_quantity, item_price) values (2010, 'Metformin', 51, 98.78);

create table if not exists ItemNotificationEntity(notify_id int not null, item_id int, email_id varchar(30), is_notified boolean, primary key (notify_id));

create table if not exists WardEntity(ward_id int not null, patient_name varchar(255), patient_email varchar(30), ward_type varchar(30), booked_from_date date, primary key (ward_id));
insert into WardEntity (ward_id, patient_name, patient_email, ward_type, booked_from_date) values (4001, 'Ben', 'ben@smith.com', 'GENERAL', '2023-03-01T08:00:00Z');
insert into WardEntity (ward_id, patient_name, patient_email, ward_type, booked_from_date) values (4002, 'Kent', 'kent@clark.com', 'PRIVATE_PLUS', '2023-03-03T08:00:00Z');