package com.trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventarioDAO {

    public boolean registrarEntrada(String producto, double cantidad) {
        // CORRECCIÓN: Nombre de tabla actualizado a 'entradas_inventario'
        String sql = "INSERT INTO entradas_inventario (producto, cantidad) VALUES (?, ?)";
        
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, producto);
            ps.setDouble(2, cantidad);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar entrada: " + e.getMessage());
            return false;
        }
    }
}