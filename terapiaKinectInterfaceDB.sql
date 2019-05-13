
--
-- BBDD de la aplicacion KinectInterface
-- 


-- CREACION DE BBDD


-- Creacion de la base de datos terapiaKinectInterfaceDB
DROP DATABASE IF EXISTS terapiaKinectInterfaceDB;
CREATE DATABASE IF NOT EXISTS terapiaKinectInterfaceDB;
USE terapiaKinectInterfaceDB;


-- CREACION DE ENTIDADES


-- Creacion de la entidad terapia
DROP TABLE IF EXISTS terapia;
CREATE TABLE terapia (
	id_terapia INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	PRIMARY KEY (id_terapia)
);

-- Creacion de la entidad especialista
DROP TABLE IF EXISTS especialista;
CREATE TABLE especialista (
	id_especialista INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellidos VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	usuario VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	PRIMARY KEY (id_especialista)
);

-- Creacion de la entidad tratamiento
DROP TABLE IF EXISTS tratamiento;
CREATE TABLE tratamiento (
	id_tratamiento INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL, -- NO estaba en el esquema
	fecha_inicio DATE NOT NULL,
	fecha_fin DATE NOT NULL,
	habilitado BOOLEAN NOT NULL, -- SI O NO CAMBIAR BOOLEANO 
	descripcion VARCHAR(100), -- DEJAR OPCIONALMENTE NULL
	PRIMARY KEY (id_tratamiento)
);

-- Creacion de la entidad sesion
DROP TABLE IF EXISTS sesion;
CREATE TABLE sesion (
	id_sesion INT AUTO_INCREMENT NOT NULL,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
	duracion INT NOT NULL, -- minutos
	archivoTxt VARCHAR(100) NOT NULL, -- txt RUTAS
	archivoMp4 VARCHAR(100) NOT NULL, -- mp4 RUTAS 100
	id_terapia INT NOT NULL,
	PRIMARY KEY (id_sesion),
	FOREIGN KEY (id_terapia) REFERENCES terapia(id_terapia)
);

-- Creacion de la entidad actividad
DROP TABLE IF EXISTS actividad;
CREATE TABLE actividad (
	id_actividad INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	descripcion VARCHAR(100), -- DEJAR OPCIONALMENTE NULL
	PRIMARY KEY (id_actividad)
);

-- Creacion de la entidad motricidad
DROP TABLE IF EXISTS motricidad;
CREATE TABLE motricidad (
	imagen_fondo VARCHAR(100) NOT NULL, -- ruta de archivo
	id_actividad INT AUTO_INCREMENT NOT NULL,
	PRIMARY KEY (id_actividad), -- estaria bien ?¿
    FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
);

-- Creacion de la entidad posicion
DROP TABLE IF EXISTS posicion;
CREATE TABLE posicion (
	id_posicion INT AUTO_INCREMENT NOT NULL,
	x INT NOT NULL,
	y INT NOT NULL,
	orden INT NOT NULL,
	objeto VARCHAR(50) NOT NULL,
	tiempo INT NOT NULL, -- segundos (1seg minimo)
	id_actividad INT NOT NULL,
	PRIMARY KEY (id_posicion),
    FOREIGN KEY (id_actividad) REFERENCES motricidad(id_actividad) -- estaria bien asi o seria REFERENCES actividad(id_actividad) ?¿
);

-- Creacion de la entidad paciente
DROP TABLE IF EXISTS paciente;
CREATE TABLE paciente (
	id_paciente INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	apellidos VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	foto VARCHAR(100) NOT NULL, -- ruta de archivo
	fecha_nacimiento DATE NOT NULL,
	sexo VARCHAR(1) NOT NULL,
	rfid VARCHAR(16) NOT NULL, -- puede ser de 8 o 16 de largo
	usuario VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	PRIMARY KEY (id_paciente)
);

-- Creacion de la entidad diagnostico
DROP TABLE IF EXISTS diagnostico;
CREATE TABLE diagnostico (
	id_diagnostico INT AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	fecha DATE NOT NULL,
	valoracion VARCHAR(50) NOT NULL,
	anotaciones VARCHAR(50) NOT NULL,
	id_paciente INT NOT NULL,
	PRIMARY KEY (id_diagnostico),
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);


-- CREACION DE LAS RELACIONES ENTRE ENTIDADES


-- Creacion de la relacion terapia_especialista --> N a M
DROP TABLE IF EXISTS terapia_especialista;
CREATE TABLE terapia_especialista (
	fecha DATE NOT NULL,
	id_terapia INT NOT NULL,
	id_especialista INT NOT NULL,
	PRIMARY KEY (fecha, id_terapia, id_especialista),
    FOREIGN KEY (id_terapia) REFERENCES terapia(id_terapia),
    FOREIGN KEY (id_especialista) REFERENCES especialista(id_especialista)
);

-- Creacion de la relacion terapia_tratamiento --> N a M
DROP TABLE IF EXISTS terapia_tratamiento;
CREATE TABLE terapia_tratamiento (
	id_terapia INT NOT NULL,
	id_tratamiento INT NOT NULL,
	PRIMARY KEY (id_terapia, id_tratamiento),
    FOREIGN KEY (id_terapia) REFERENCES terapia(id_terapia),
    FOREIGN KEY (id_tratamiento) REFERENCES tratamiento(id_tratamiento)
);

-- Creacion de la relacion terapia_sesion --> 1 a N
-- SE USA id_terapia COMO CLAVE FORANEA EN la tabla sesion

-- Creacion de la relacion terapia_actividad --> N a M
DROP TABLE IF EXISTS terapia_actividad;
CREATE TABLE terapia_actividad (
	id_terapia INT NOT NULL,
	id_actividad INT NOT NULL,
	PRIMARY KEY (id_terapia, id_actividad),
    FOREIGN KEY (id_terapia) REFERENCES terapia(id_terapia),
    FOREIGN KEY (id_actividad) REFERENCES actividad(id_actividad)
);

-- Creacion de la relacion especialista_tratamiento --> N a M
DROP TABLE IF EXISTS especialista_tratamiento;
CREATE TABLE especialista_tratamiento (
	id_especialista INT NOT NULL,
	id_tratamiento INT NOT NULL,
	PRIMARY KEY (id_especialista, id_tratamiento),
    FOREIGN KEY (id_especialista) REFERENCES especialista(id_especialista),
    FOREIGN KEY (id_tratamiento) REFERENCES tratamiento(id_tratamiento)
);

-- Creacion de la relacion paciente_tratamiento --> N a M
DROP TABLE IF EXISTS paciente_tratamiento;
CREATE TABLE paciente_tratamiento (
	id_paciente INT NOT NULL,
	id_tratamiento INT NOT NULL,
	PRIMARY KEY (id_paciente, id_tratamiento),
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    FOREIGN KEY (id_tratamiento) REFERENCES tratamiento(id_tratamiento)
);

-- Creacion de la relacion especialista_paciente --> N a M
DROP TABLE IF EXISTS especialista_paciente;
CREATE TABLE especialista_paciente (
	id_especialista INT NOT NULL,
	id_paciente INT NOT NULL,
	PRIMARY KEY (id_especialista, id_paciente),
	FOREIGN KEY (id_especialista) REFERENCES especialista(id_especialista),
    FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente)
);

-- Creacion de la relacion paciente_diagnostico --> 1 a N
-- SE USA id_paciente COMO CLAVE FORANEA EN la tabla diagnostico


-- Creacion de la relacion actividad_motricidad --> 1 a N HERENCIA
-- SE USA id_actividad COMO CLAVE FORANEA EN la tabla motricidad

-- Creacion de la relacion motricidad_posicion --> 1 a N
-- SE USA id_actividad COMO CLAVE FORANEA EN la tabla posicion QUE REFERENCIA A MOTRICIDAD


-- CREACION DE TUPLAS DE PRUEBA

-- ESPECIALISTA --

-- INSERT INTO NombreTabla [(Campo1, …, CampoN)] VALUES (Valor1, …, ValorN)
INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "RAMON", "SEOANE MARTIN","I02SEMAR@UCO.ES","RSM21","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "PEDRO", "LOPEZ RUBIO","I06LORUP@UCO.ES","PELO22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "ELENA", "GARCIA MORALES","I02GAMOE@UCO.ES","ELEGA22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "LUIS", "PEREZ FERNANDEZ","I02PEFEL@UCO.ES","LUPE22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "MARTA", "RICO CALZADA","I08RICAM@UCO.ES","MARI22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "CARLOS", "RUIZ NAJAS","I22RUNAC@UCO.ES","CARU22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "SARA", "MENDEZ YEGUAS","I32MEYES@UCO.ES","SAME22","PRUEBA1");

INSERT INTO especialista (nombre, apellidos, email, usuario, password) VALUES ( "GREGORIO", "CORPAS PRIETO","I02COPRG@UCO.ES","GRCO22","PRUEBA1");


-- PACIENTE --

-- INSERT INTO NombreTabla [(Campo1, …, CampoN)] VALUES (Valor1, …, ValorN)
INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "SANDRA", "GOMEZ PEREZ","I34GOPES@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/M1.jpg", '1990-04-22', "M", "SJEU37DJ","SAGO33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "CARLOS", "SANCHEZ ROSAS","I22SAROC@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h1.jpg", '1995-10-05', "H", "SJEU37DJ","CASA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "FRANCISCO", "PINO OLMO","I34PIOLF@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h2.jpg", '1995-11-07', "H", "SJEU37DJ","FRPI33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "LUISA", "MARTINEZ LLANOS","I31MALLL@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/M2.jpg", '1994-01-08', "M", "SJEU37DJ","LUMA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "PABLO", "PRADO RIOS","I09PRRIP@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h3.jpg", '1992-04-03', "H", "SJEU37DJ","PAPR33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "HOUDINI", "MARIO ELUIGI","I07MAELH@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h4.jpg", '1998-05-12', "H", "SJEU37DJ","HOMA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "RAFAEL", "CASTRO VAZQUEZ","I25CAVAR@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h5.jpg", '1992-03-15', "H", "SJEU37DJ","RACA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "NICOLAS", "REBECAS DEPUNTO","I22REDEN@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h6.jpg", '1990-08-18', "H", "SJEU37DJ","NIRE33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "TERESA", "CARTON POVEDANO","I03CAPOT@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/M3.jpg", '1991-09-02', "M", "SJEU37DJ","TECA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "EVA", "GALERA OLMEDO","I05GAOLE@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/M4.jpg", '1997-10-09', "M", "SJEU37DJ","EVGA33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "JAVIER", "JEMEZ LUJAN","I26JELUJ@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/h7.jpg", '1999-12-11', "H", "SJEU37DJ","JAJE33","PRUEBA2");

INSERT INTO paciente (nombre, apellidos, email, foto, fecha_nacimiento, sexo, rfid, usuario, password) VALUES ( "LAURA", "MUÑOZ BENAVENTE","I30MUBEL@UCO.ES", "/Users/ramonseoane/Desktop/InterfazTFG/images/Logos/FotosPac/M5.jpg", '1992-06-14', "M", "SJEU37DJ","LAMU33","PRUEBA2");


-- ESPECIALISTA_PACIENTE --

-- INSERT INTO NombreTabla [(Campo1, …, CampoN)] VALUES (Valor1, …, ValorN)
INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (1, 10);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (1, 11);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (1, 12);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (3, 11);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (3, 8);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (3, 3);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (3, 1);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (8, 9);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (8, 2);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (5, 4);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (5, 7);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (5, 5);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (7, 6);

INSERT INTO especialista_paciente (id_especialista, id_paciente) VALUES (4, 9);



