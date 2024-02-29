--liquibase formatted sql

--changeset gturumtaev:1

CREATE TABLE shelter (
     id INT8 PRIMARY KEY,
     work_schedule VARCHAR(255) NOT NULL,
     address VARCHAR(255) NOT NULL,
     phone_number_security VARCHAR(255) NOT NULL,
     safety_precautions VARCHAR(255) NOT NULL
);

CREATE TABLE driving_directions (
     id INT8 PRIMARY KEY,
     file_path VARCHAR(255) NOT NULL,
     shelter_id INT8,
     FOREIGN KEY (shelter_id) REFERENCES shelter(id)
);

--changeset gturumtaev:2

CREATE TABLE volunteers (
     id INT8 PRIMARY KEY,
     first_name TEXT NOT NULL,
     phone_number TEXT NOT NULL,
     shelter_id INT8,
     FOREIGN KEY (shelter_id) REFERENCES shelter(id)
);

--changeset gturumtaev:3

CREATE TABLE clients (
     id INT8 PRIMARY KEY,
     first_name TEXT,
     phone_number TEXT,
     took_a_pet boolean,
     chat_id BIGINT,
     date_time_to_took timestamp
);

--changeset gturumtaev:4

CREATE TABLE report_tg (
     id INT8 PRIMARY KEY,
     date_added timestamp NOT NULL,
     general_well_being TEXT,
     photo_name TEXT NOT NULL,
     check_report boolean NOT NULL,
     clients_id BIGINT REFERENCES clients(id)
);

--changeset gturumtaev:5

CREATE TABLE animal_dog (
     id INT8 PRIMARY KEY,
     dog_name TEXT,
     age INT,
     color TEXT,
     breed TEXT,
     validity boolean,
     shelter_id INT,
     FOREIGN KEY (shelter_id) REFERENCES shelter(id)
);

--changeset gturumtaev:6

CREATE TABLE animal_cat (
    id INT8 PRIMARY KEY,
    cat_name TEXT,
    age INT,
    color TEXT,
    breed TEXT,
    validity boolean,
    shelter_id INT,
    FOREIGN KEY (shelter_id) REFERENCES shelter(id)
);

--changeset gturumtaev:7

CREATE TABLE owner (
    id INT8 PRIMARY KEY,
    client_name TEXT,
    animal_name TEXT,
    date_owner timestamp
);