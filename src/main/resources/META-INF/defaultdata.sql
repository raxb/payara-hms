create table if not exists PatientEntity(patient_id int not null, first_name varchar(255), last_name varchar(255), gender varchar(10), age int, email varchar(30), address_line1 varchar(100), address_line2 varchar(100), city varchar(20), state varchar(20), country varchar(20), zip_code varchar(20), last_appointment date, upcoming_appointment date, primary key (patient_id));

insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1001, 'Ben','Smith','MALE',36,'ben@smith.com','addressLine1','addressLine2','city','state','country','zip_code','2012-02-08T00:00:00Z','2023-03-03T08:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1002, 'Bruce','Banner','MALE',45,'bruce@banner.com','addressLine1','addressLine2','city','state','country','zip_code','2019-12-26T00:00:00Z','2023-03-03T10:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1003, 'Tony','Stark','MALE',58,'tony@stark.com','addressLine1','addressLine2','city','state','country','zip_code','2021-11-30T00:00:00Z','2023-03-03T22:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1004, 'Bruce','Wayne','MALE',50,'bruce@wayne.com','addressLine1','addressLine2','city','state','country','zip_code','2022-06-21T00:00:00Z','2023-03-04T00:00:00Z');
insert into PatientEntity (patient_id, first_name, last_name, gender, age, email, address_line1, address_line2, city, state, country, zip_code, last_appointment, upcoming_appointment) values (1005, 'Kent','Clark','MALE',55,'kent@clark.com','addressLine1','addressLine2','city','state','country','zip_code','2022-04-19T00:00:00Z','2023-03-05T00:00:00Z');