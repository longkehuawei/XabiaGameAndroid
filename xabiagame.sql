BASE DE DATOS: XABIA GAME 

Base de datos en MySql

CREATE DATABASE xabiagame;

USE xabiagame;

CREATE TABLE administrador(
	id INT(10) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50),
	apellido VARCHAR(50),
	usuario VARCHAR(50),
	email VARCHAR(100)
	);
	
CREATE TABLE ruta(
	id INT(10) PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(50),
	descripcion TEXT,
	distancia FLOAT(4,2),
	nivel_dificultad VARCHAR(20),
	desnivel_acumulado_ascenso INT(10),
	id_admin INT(10),
	CONSTRAINT fk_id_admin FOREIGN KEY (id_admin) REFERENCES administrador(id)
	);
	
CREATE TABLE coordenadasrutas(
	id INT(10) PRIMARY KEY,
	coord_latitud FLOAT(4,30),
	coord_longitud FLOAT(4,30),
	nombre VARCHAR(50),
	descripcion TEXT
	);
	
CREATE TABLE rutacoordenadasNN(
	id_ruta INT(10),
	id_coordenadasrutas INT(10),
	PRIMARY KEY(id_ruta, id_coordenadasrutas),
	CONSTRAINT fk_id_ruta FOREIGN KEY (id_ruta) REFERENCES ruta(id),
	CONSTRAINT fk_id_coordenadasrutas FOREIGN KEY (id_coordenadasrutas) REFERENCES coordenadasrutas(id)
	);

CREATE TABLE misiongymkh(
	id INT(10) PRIMARY KEY,
	titulo VARCHAR(20),
	descripcion TEXT,
	nivel_dificultad VARCHAR(10),
	mision_completada TINYINT (1),
	id_admin INT(10),
	CONSTRAINT fk_id_admin FOREIGN KEY (id_admin) REFERENCES administrador(id)
	);
	
CREATE TABLE coordenadasmisiones(
	id INT(10) PRIMARY KEY,
	coord_latitud FLOAT(4,30), 
	coord_longitud FLOAT(4,30),
	nombre VARCHAR(20),
	descripcion TEXT
	);

CREATE TABLE misioncoordenadasNN(
	id_misionNN INT(10),
	id_coordenadas_mision_gymkNN INT(10),
	PRIMARY KEY(id_misionNN, id_coordenadas_mision_gymkNN),
	CONSTRAINT fk_id_misionNN FOREIGN KEY (id_misionNN) REFERENCES misiongymkh(id),
	CONSTRAINT fk_id_coordenadas_mision_gymkNN FOREIGN KEY (id_coordenadas_mision_gymkNN) REFERENCES coordenadasmisiones(id)
	);
	
CREATE TABLE pista(
	id INT(10) PRIMARY KEY,
	pista1 TEXT,
	pista2 TEXT,
	pista3 TEXT,
	id_coordenadas_mision_gymk INT(10),
	CONSTRAINT fk_id_coordenadas_mision_gymk FOREIGN KEY (id_coordenadas_mision_gymk) REFERENCES coordenadasmisiones(id)
	);

CREATE TABLE usuario(
	id INT(10) Primary Key AUTO_INCREMENT,
	nombre VARCHAR(20),
	apellidos VARCHAR(20),
	email VARCHAR(50),
	dni VARCHAR(10),
	contraseña VARCHAR(20),
	usuario VARCHAR(20),
	edad INT(3)
	);

CREATE TABLE misiones_completadas(
	id INT(10) PRIMARY KEY,
	titulo VARCHAR(20),
	descripcion TEXT,
	nivel_dificultad VARCHAR(10),
	distancia_total FLOAT (4,2),
	tiempo_total VARCHAR(10),
	id_misiongymkh INT(10),
	CONSTRAINT fk_id_misiongymkh FOREIGN KEY (id_misiongymkh) REFERENCES misiongymkh(id)
	);

CREATE TABLE misioncompletadausuario(
	id_usuario INT(10),
	id_mision_compl INT(10),
	PRIMARY KEY{id_usuario, id_mision_compl},
	CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	CONSTRAINT fk_id_mision_compl FOREIGN KEY (id_mision_compl) REFERENCES misiones_completadas(id)
	);


