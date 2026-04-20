package com.trade;

import com.trade.model.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {

    public boolean registrarDetalle(DetalleVenta dv) {
        String sql = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dv.getIdVenta());
            ps.setInt(2, dv.getIdProducto());
            ps.setInt(3, dv.getCantidad());
            ps.setDouble(4, dv.getPrecioUnitario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar detalle: " + e.getMessage());
            return false;
        }
    }

    public List<DetalleVenta> listarPorVenta(int idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "SELECT dv.*, p.nombre as producto FROM detalle_ventas dv " +
                     "INNER JOIN productos p ON dv.id_producto = p.id WHERE dv.id_venta = ?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleVenta dv = new DetalleVenta(rs.getInt("id"), rs.getInt("id_venta"), 
                                                 rs.getInt("id_producto"), rs.getInt("cantidad"), 
                                                 rs.getDouble("precio_unitario"));
                dv.setNombreProducto(rs.getString("producto"));
                lista.add(dv);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }
}