-- 1. Crear la base de datos de forma segura
CREATE DATABASE IF NOT EXISTS sistema_comercio DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistema_comercio;

-- 2. Categorías
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- 3. Proveedores
CREATE TABLE proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa VARCHAR(100),
    contacto VARCHAR(50),
    telefono VARCHAR(20)
) ENGINE=InnoDB;

-- 4. Productos
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_barras VARCHAR(50) UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    id_categoria INT,
    precio_compra DECIMAL(10,2),
    precio_venta DECIMAL(10,2),
    stock_actual INT DEFAULT 0,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 5. Clientes
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni_rfc VARCHAR(20) UNIQUE,
    nombre VARCHAR(100),
    email VARCHAR(100)
) ENGINE=InnoDB;

-- 6. Usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    rol ENUM('admin', 'vendedor')
) ENGINE=InnoDB;

-- 7. Métodos de Pago
CREATE TABLE metodos_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    metodo VARCHAR(30)
) ENGINE=InnoDB;

-- 8. Ventas (Cabecera)
CREATE TABLE ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT,
    id_usuario INT,
    id_pago INT,
    total DECIMAL(10,2),
    FOREIGN KEY (id_cliente) REFERENCES clientes(id) ON DELETE SET NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_pago) REFERENCES metodos_pago(id)
) ENGINE=InnoDB;

-- 9. Detalle de Ventas
CREATE TABLE detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    FOREIGN KEY (id_venta) REFERENCES ventas(id) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id)
) ENGINE=InnoDB;

-- 10. Entradas de Mercancía
CREATE TABLE entradas_inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_proveedor INT,
    cantidad INT,
    fecha_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES productos(id),
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 11. Arqueos de Caja
CREATE TABLE arqueos_caja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_apertura DATETIME,
    monto_inicial DECIMAL(10,2),
    monto_final DECIMAL(10,2),
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
) ENGINE=InnoDB;