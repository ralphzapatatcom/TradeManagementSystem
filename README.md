# Trade Management System

Este proyecto es un sistema de gestión comercial desarrollado en Java, diseñado para optimizar el control de inventarios, la gestión de clientes/proveedores y el registro de ventas. El sistema permite llevar un control detallado de las operaciones diarias, facilitando la toma de decisiones y la supervisión del flujo de efectivo mediante arqueos de caja.

## Características Principales

El sistema está estructurado para cubrir las necesidades fundamentales de un negocio comercial:

* **Gestión de Inventario:** Control de productos con código de barras, seguimiento de precios de compra/venta y stock actual.
* **Gestión de Proveedores y Clientes:** Base de datos relacional para gestionar contactos y datos fiscales de proveedores y clientes.
* **Módulo de Ventas:** Registro integral de ventas, incluyendo la asociación de cliente, vendedor (usuario) y método de pago.
* **Detalle de Ventas:** Registro automatizado de cada artículo vendido, permitiendo un historial preciso de transacciones.
* **Control de Caja:** Módulo de arqueo de caja para registrar montos iniciales y finales, garantizando la transparencia operativa.
* **Seguridad:** Control de acceso basado en roles (Administrador y Vendedor).

## Tecnologías Utilizadas

* **Lenguaje:** Java
* **IDE:** NetBeans
* **Base de Datos:** MariaDB / MySQL
* **Interfaz:** Java Swing

## Configuración del Entorno

### Requisitos previos
1.  Tener instalado **XAMPP** (o un servidor local con MySQL/MariaDB).
2.  Tener **JDK** configurado en su entorno de desarrollo.
3.  El proyecto requiere acceso a la base de datos `sistema_comercio`.

### Instalación de la Base de Datos
Para que el sistema funcione correctamente, es necesario configurar la base de datos siguiendo estos pasos:

1.  Inicia **MySQL** desde el Panel de Control de XAMPP.
2.  Accede a `phpMyAdmin` (usualmente en `http://localhost/phpmyadmin`).
3.  Ejecuta el script SQL incluido en este repositorio (carpeta `/database/database.sql` o el archivo correspondiente).
    * Este script creará automáticamente todas las tablas necesarias con las relaciones de integridad referencial requeridas.

## Estructura de la Base de Datos

El sistema utiliza un modelo relacional robusto (InnoDB) que garantiza la integridad de los datos, incluyendo:
* `categorias`, `productos`
* `proveedores`, `entradas_inventario`
* `clientes`, `usuarios`
* `ventas`, `detalle_ventas`
* `metodos_pago`, `arqueos_caja`

---
*Desarrollado como proyecto de sistema de gestión comercial.*