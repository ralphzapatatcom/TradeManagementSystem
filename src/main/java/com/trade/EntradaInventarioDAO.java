package com.trade;

import com.trade.model.EntradaInventario;
import java.sql.*;

public class EntradaInventarioDAO {

    public boolean registrarEntrada(int idProducto, int idProveedor, int cantidad) {
        String sqlEntrada = "INSERT INTO entradas_inventario (id_producto, id_proveedor, cantidad) VALUES (?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock_actual = stock_actual + ? WHERE id = ?";
        
        Connection con = null;
        try {
            con = TradeManagementSystem.getConexion();
            con.setAutoCommit(false); // Iniciamos transacción para que ambas consultas sean una sola unidad

            // Usamos try-with-resources para asegurar que los statements se cierren solos
            try (PreparedStatement psEntrada = con.prepareStatement(sqlEntrada);
                 PreparedStatement psStock = con.prepareStatement(sqlStock)) {
                
                // 1. Registrar la entrada de mercancía
                psEntrada.setInt(1, idProducto);
                psEntrada.setInt(2, idProveedor);
                psEntrada.setInt(3, cantidad);
                psEntrada.executeUpdate();

                // 2. Aumentar el stock del producto
                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.executeUpdate();
            }

            con.commit(); // Guardamos los cambios de forma permanente
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Si algo falla, revertimos el aumento de stock y la entrada
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("❌ ERROR en Entrada de Mercancía: " + e.getMessage());
            return false;
        } finally {
            try { 
                if (con != null) {
                    con.setAutoCommit(true); // Restauramos el comportamiento normal
                    con.close(); 
                }
            } catch (SQLException e) { 
                e.printStackTrace(); 
            }
        }
    }
}