
-- drop table if exists cliente, empleado, proyecto, tarea,tareamepleado;

CREATE TABLE USUARIO (
IdUsuario VARCHAR(20) PRIMARY KEY,
Password VARCHAR(40) NOT NULL,
Administrador BOOLEAN NOT NULL,
Apellidos VARCHAR(56) NOT NULL,
Nombre VARCHAR(20) NOT NULL,
NIF CHAR(9) NOT NULL, /* NIF[i] = {0-9}, 0 <= i <= 7 && NIF[8] = {A-Z} */
Direccion VARCHAR(200) NOT NULL,
Telefono VARCHAR(13) NOT NULL, /* Telefono[i] = {0-9}, 0 <= i <= 12 */
Email VARCHAR(40) NOT NULL,
Bonificado INT NOT NULL,  /* 0<=Bonificado<=100 */
/* CONSTRAINT chk_Bonificado CHECK (Bonificado>=0 AND Bonificado<=100)*/
);

CREATE TABLE PEDIDO (
IdPedido INT PRIMARY KEY,
NumCuenta CHAR(20) NOT NULL, /* Numero[i] = {0-9}, 0 <= i <= 19 */
FechaPedido,FCaducidad INT NOT NULL,
Direccion VARCHAR(200) NOT NULL,
Nombre_Cl VARCHAR(40) NOT NULL,
Estado VARCHAR(10) NOT NULL, /* ESTADO = (realizado, preparacion,enviado); */
CIF_NIF CHAR(9) NOT NULL, /* Numero[i] = {0-9}, 1 <= i <= 7 && (Numero[0] = {A-Z} || Numero[8] = {A-Z}) */
IdUsuario VARCHAR(20) NOT NULL,
FOREIGN KEY(IdUsuario) REFERENCES USUARIO(IdUsuario)
);

CREATE TABLE PRODUCTO (
IdProducto INT PRIMARY KEY,
Agotado BOOLEAN NOT NULL,
Ejemplares INT NOT NULL,   /* Ejemplares>=0 */
Puntuacion INT NOT NULL,	/* 0 <= Puntuacion <= 10 */
NumVotos INT NOT NULL,
PrecioUd INT NOT NULL,
Genero VARCHAR(40) NOT NULL,
Titulo VARCHAR(40) NOT NULL,
Fichero VARCHAR(100) NOT NULL,
Fecha INT NOT NULL,
Sinopsis VARCHAR(1000) NOT NULL
);

CREATE TABLE PELICULA(
IdProducto INT PRIMARY KEY,
Soporte CHAR(3) NOT NULL, /*  Soporte=DVD Ó Soporte=B-R ( Blue-Ray ) */
Director VARCHAR(40) NOT NULL,
Actores VARCHAR(200),
FOREIGN KEY(IdProducto) REFERENCES PRODUCTO(IdProducto));

CREATE TABLE VIDEOJUEGO(
IdProducto INT PRIMARY KEY,
Plataforma VARCHAR(4) NOT NULL, /*  Plataforma=(Wii,PS3,PC,GB(GameBoy),Xbox,DS,PSP) */
Companya VARCHAR(40) NOT NULL,
FOREIGN KEY(IdProducto) REFERENCES PRODUCTO(IdProducto));


CREATE TABLE PRODUCTOSPEDIDO (
IdPedido INT NOT NULL,
IdProducto INT NOT NULL,
Cantidad INT NOT NULL,
PRIMARY KEY(IdPedido,IdProducto),
FOREIGN KEY(IdPedido) REFERENCES PEDIDO(IdPedido),
FOREIGN KEY(IdProducto) REFERENCES PRODUCTO(IdProducto),
);

CREATE TABLE PUEDEVOTAR (
IdUsuario INT NOT NULL,
IdProducto INT NOT NULL,
Cantidad INT NOT NULL,
PRIMARY KEY(IdUsuario,IdProducto),
FOREIGN KEY(IdUsuario) REFERENCES USUARIO(IdUsuario),
FOREIGN KEY(IdProducto) REFERENCES PRODUCTO(IdProducto),
);




