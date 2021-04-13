create database zookeeper;
use zookeeper;
CREATE TABLE animals (
	id int NOT NULL AUTO_INCREMENT,
	animal_type varchar(50),
    name varchar(50),
    age numeric,
    heath_concerns varchar(255),
    feeding_schedule varchar(255),
    primary key (id)
);

CREATE TABLE habitats (
	id int NOT NULL AUTO_INCREMENT,
	habitat_type varchar(50),
    temperature varchar(50),
    food_source varchar(150),
    cleanliness varchar(255),
    primary key (id)
);


INSERT into animals(
animal_type, name, age, health_concerns, feeding_schedule
)
values ("Lion","Leo",5,"*****Cut on left front paw","Twice daily"),

("Tiger","Maj",15,"None","3x daily"),

("Bear","Baloo",1,"None","*****None on record"),

("Giraffe","Spots",12,"None","Grazing");

INSERT into habitats(
habitat_type, temperature, food_source, cleanliness
)
values ("Penguin","Freezing","*****Fish in water running low","Passed"),

("Bird","Moderate","Natural from environment","Passed"),

("Aquarium","Varies with output temperature","Added daily","*****Needs cleaning from algae");

