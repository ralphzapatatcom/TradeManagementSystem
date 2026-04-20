# Trade Management System

This project is a commercial management system developed in Java, designed to optimize inventory control, client/supplier management, and sales recording. The system allows for detailed tracking of daily operations, facilitating decision-making and cash flow supervision through cash reconciliation.

## Key Features

The system is structured to meet the fundamental needs of a commercial business:

* **Inventory Management:** Product control with barcodes, purchase/sale price tracking, and current stock.
* **Supplier and Client Management:** Relational database to manage contacts and tax information for suppliers and clients.
* **Sales Module:** Comprehensive sales recording, including the association of client, salesperson (user), and payment method.
* **Sales Details:** Automated recording of each item sold, allowing for an accurate transaction history.
* **Cash Control:** Cash reconciliation module to record opening and closing balances, ensuring operational transparency.
* **Security:** Role-based access control (Administrator and Salesperson).

## Technologies Used

* **Language:** Java
* **IDE:** NetBeans
* **Database:** MariaDB / MySQL
* **Interface:** Java Swing

## Environment Setup

### Prerequisites
1.  Have **XAMPP** installed (or a local server with MySQL/MariaDB).
2.  Have **JDK** configured in your development environment.
3.  The project requires access to the `sistema_comercio` database.

### Database Installation
To ensure the system works correctly, configure the database by following these steps:

1.  Start **MySQL** from the XAMPP Control Panel.
2.  Access `phpMyAdmin` (usually at `http://localhost/phpmyadmin`).
3.  Execute the SQL script included in this repository (located in the `/database/database.sql` folder or the corresponding file).
    * This script will automatically create all necessary tables with the required referential integrity relationships.

## Database Structure

The system uses a robust relational model (InnoDB) that guarantees data integrity, including:
* `categorias`, `productos`
* `proveedores`, `entradas_inventario`
* `clientes`, `usuarios`
* `ventas`, `detalle_ventas`
* `metodos_pago`, `arqueos_caja`

---
*Developed as a commercial management system project.*