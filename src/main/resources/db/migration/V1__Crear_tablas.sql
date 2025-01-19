CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_electronico VARCHAR(150) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE Curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL
);

CREATE TABLE Topico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    autor_id INT NOT NULL,
    curso_id INT NOT NULL,
    UNIQUE (titulo, mensaje),
    FOREIGN KEY (autor_id) REFERENCES Usuario(id),
    FOREIGN KEY (curso_id) REFERENCES Curso(id)
);
