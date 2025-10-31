JJF - Registro de Inasistencias Docentes

JJF - Registro de Inasistencias Docentes es una aplicación de escritorio desarrollada en Java (NetBeans) que permite registrar, gestionar y publicar las inasistencias de los docentes,
especialmente aquellas asociadas a licencias médicas.
El sistema facilita la comunicación entre la administración y los estudiantes, mostrando información relevante como:

Nombre del docente ausente
Turno y grupo afectado
Motivo y justificación de la ausencia
Fechas de inicio y finalización de la licencia
La aplicación utiliza MySQL como sistema de gestión de base de datos relacional y cuenta con una interfaz desarrollada en Swing, orientada a una experiencia visual clara y funcional.
Además, la topología de red física y lógica del sistema se simula mediante Cisco Packet Tracer.

Propósito del Sistema
El propósito principal del sistema es brindar una herramienta eficiente para registrar y publicar licencias docentes, garantizando transparencia y organización en los registros administrativos.

Tecnologías Utilizadas
Área	Tecnología
Lenguaje principal	Java (JDK 17 o superior)
Entorno de desarrollo	NetBeans IDE
Interfaz gráfica	Swing
Base de datos	MySQL (XAMPP)
Librerías JDBC	Conector MySQL para Java
Simulación de red	Cisco Packet Tracer
Control de versiones	Git / GitHub

Requisitos del Sistema

Software necesario:
Java Development Kit (JDK) 17 o superior
NetBeans IDE 12 o superior
XAMPP con MySQL (versión 8 o superior)
Conector JDBC para MySQL (mysql-connector-j-8.x.jar)


Hardware recomendado:
Sistema operativo Windows 10 o superior
Procesador Dual-Core 2.0 GHz o superior
4 GB de RAM (mínimo)
500 MB de espacio disponible en disco

Organización del Equipo
Integrante	Rol
Facundo Rodríguez	Programador Backend
Julián Rodríguez	Diseñador de Interfaz Gráfica
Joaquín Schol	Líder y Gestor de Base de Datos

BASE DE DATOS_____
CREATE TABLE ADMINISTRADOR (
    usuario VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(50) NOT NULL
);

-- Tabla GRUPO (sin clave primaria)
CREATE TABLE GRUPO (
    id_grupo INT,
    nombre_grupo VARCHAR(50) NOT NULL
);

-- Tabla PROFESOR (sin foreign key hacia GRUPO)
CREATE TABLE PROFESOR (
    cedula INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono INT,
    turno VARCHAR(20),
    id_grupo INT,
    materias VARCHAR(100)
);

-- Tabla LICENCIA
CREATE TABLE LICENCIA (
    id_licencia INT PRIMARY KEY AUTO_INCREMENT,
    fecha_inicio VARCHAR(20),
    fecha_cierre VARCHAR(20),
    justificacion VARCHAR(100),
    cedula INT,
    FOREIGN KEY (cedula) REFERENCES PROFESOR(cedula)
);

-- Tabla REGISTRA
CREATE TABLE REGISTRA (
    id_admin VARCHAR(50),
    cedula INT,
    id_licencia INT,
    fecha_registro VARCHAR(20),
    PRIMARY KEY (id_admin, cedula, id_licencia),
    FOREIGN KEY (id_admin) REFERENCES ADMINISTRADOR(usuario),
    FOREIGN KEY (cedula) REFERENCES PROFESOR(cedula),
    FOREIGN KEY (id_licencia) REFERENCES LICENCIA(id_licencia)
);

-- Tabla INASISTENCIA
CREATE TABLE INASISTENCIA (
    cedula INT,
    id_licencia INT,
    fecha_inicio VARCHAR(20),
    fecha_cierre VARCHAR(20),
    justificacion VARCHAR(100),
    PRIMARY KEY (cedula, id_licencia),
    FOREIGN KEY (cedula) REFERENCES PROFESOR(cedula),
    FOREIGN KEY (id_licencia) REFERENCES LICENCIA(id_licencia)
);
