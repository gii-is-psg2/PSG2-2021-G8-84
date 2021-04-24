-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiología');
INSERT INTO specialties VALUES (2, 'cirugía');
INSERT INTO specialties VALUES (3, 'dentista');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'gato');
INSERT INTO types VALUES (2, 'perro');
INSERT INTO types VALUES (3, 'lagarto');
INSERT INTO types VALUES (4, 'serpiente');
INSERT INTO types VALUES (5, 'pájaro');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

--Adoptions and adoption requests
INSERT INTO adoptions(id,description,owner_id,pet_id) VALUES (1, 'Hamster de 10 años', 2, 2);
INSERT INTO adoptions(id,description,owner_id,pet_id) VALUES (2, 'Leo el gatito', 1, 1);
INSERT INTO adoptions(id,description,owner_id,pet_id) VALUES (3, 'Sly el gatete', 10, 13);
INSERT INTO adoptions(id,description,owner_id,pet_id) VALUES (4, 'Freddy el pajarito', 9, 11);

UPDATE pets
SET adoption_id = 1
WHERE id = 2;

UPDATE pets
SET adoption_id = 2
WHERE id = 1;

UPDATE pets
SET adoption_id = 3
WHERE id = 13;

UPDATE pets
SET adoption_id = 4
WHERE id = 11;

INSERT INTO adoption_requests(id,description,owner_id,adoption_id) VALUES (1,'Tengo 2 gatos más, me encantan',2,2);
INSERT INTO adoption_requests(id,description,owner_id,adoption_id) VALUES (2,'Siempre he querido un hámster',1,1);
INSERT INTO adoption_requests(id,description,owner_id,adoption_id) VALUES (3,'Otro gatito para la familia',1,3);
INSERT INTO adoption_requests(id,description,owner_id,adoption_id) VALUES (4,'Tengo 2 gatos más, me requete-encantan',3,2);

--Causes and donations
INSERT INTO causes(id,name,description,target,ngo,gathered,owner_id) VALUES (1,'Construccion de refugios','Refugios para animales abandonados en verano', 3000, 'PerrONG', 700,1);
INSERT INTO causes(id,name,description,target,ngo,gathered,owner_id) VALUES (2,'Construccion de comederos','Comederos para animales abandonados en verano', 300, 'PerrONG', 300,2);
INSERT INTO causes(id,name,description,target,ngo,gathered,owner_id) VALUES (3,'Centro de rehabilitacion','Centro medico para la rehabilitación de animales con lesiones graves', 4000, 'ONGato', 0,3);
INSERT INTO causes(id,name,description,target,ngo,gathered,owner_id) VALUES (4,'Construccion de parques','Parques para animales en los refugios de la zona de Sevilla', 1000, 'ONGato', 200,4);

INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (1,200,'2021-03-27',1,1);
INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (2,200,'2021-03-29',1,2);
INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (3,200,'2021-04-05',1,3);
INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (4,100,'2021-04-19',1,4);

INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (5,200,'2021-02-27',2,5);

INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (6,50,'2021-02-28',4,6);
INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (7,50,'2021-03-05',4,7);
INSERT INTO donations(id,amount,date,cause_id,owner_id) VALUES (8,100,'2021-03-19',4,8);


