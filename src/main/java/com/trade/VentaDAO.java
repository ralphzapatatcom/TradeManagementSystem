package com.trade;

import com.trade.model.Venta;
import com.trade.model.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    /**
     * Registra la venta, los detalles y descuenta stock.
     * @return null si todo fue correcto. Si hay error, devuelve el mensaje de texto.
     */
    public String guardarVentaCompleta(Venta v, List<DetalleVenta> detalles) {
        String sqlVenta = "INSERT INTO ventas (id_cliente, id_usuario, id_pago, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        // Validación crítica: el UPDATE solo afecta si hay stock (stock_actual >= cantidad)
        String sqlStock = "UPDATE productos SET stock_actual = stock_actual - ? WHERE id = ? AND stock_actual >= ?";

        Connection con = null;
        try {
            con = TradeManagementSystem.getConexion();
            con.setAutoCommit(false); // Inicia transacción

            // 1. Insertar la Venta
            int idVentaGenerado = 0;
            try (PreparedStatement psV = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psV.setInt(1, v.getIdCliente());
                psV.setInt(2, v.getIdUsuario());
                psV.setInt(3, v.getIdPago());
                psV.setDouble(4, v.getTotal());
                psV.executeUpdate();

                try (ResultSet rs = psV.getGeneratedKeys()) {
                    if (rs.next()) {
                        idVentaGenerado = rs.getInt(1);
                    }
                }
            }

            // 2. Insertar detalles y actualizar stock
            try (PreparedStatement psD = con.prepareStatement(sqlDetalle);
                 PreparedStatement psS = con.prepareStatement(sqlStock)) {

                for (DetalleVenta dv : detalles) {
                    // Registrar detalle
                    psD.setInt(1, idVentaGenerado);
                    psD.setInt(2, dv.getIdProducto());
                    psD.setInt(3, dv.getCantidad());
                    psD.setDouble(4, dv.getPrecioUnitario());
                    psD.executeUpdate();

                    // Descontar stock con validación
                    psS.setInt(1, dv.getCantidad());
                    psS.setInt(2, dv.getIdProducto());
                    psS.setInt(3, dv.getCantidad());
                    
                    int filasAfectadas = psS.executeUpdate();
                    
                    if (filasAfectadas == 0) {
                        // Si no se afectó ninguna fila, el stock era insuficiente
                        throw new Exception("Stock insuficiente para el producto ID: " + dv.getIdProducto());
                    }
                }
            }

            con.commit();
            return null; // Éxito

        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback(); // Deshacemos todo ante error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return e.getMessage(); // Retornamos el mensaje para el JOptionPane
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nombre as cliente, u.username as usuario, p.metodo as pago " +
                     "FROM ventas v " +
                     "INNER JOIN clientes c ON v.id_cliente = c.id " +
                     "INNER JOIN usuarios u ON v.id_usuario = u.id " +
                     "INNER JOIN metodos_pago p ON v.id_pago = p.id " +
                     "ORDER BY v.id DESC";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta v = new Venta(rs.getInt("id"), rs.getTimestamp("fecha"), 
                                    rs.getInt("id_cliente"), rs.getInt("id_usuario"), 
                                    rs.getInt("id_pago"), rs.getDouble("total"));
                v.setNombreCliente(rs.getString("cliente"));
                v.setNombreUsuario(rs.getString("usuario"));
                v.setNombrePago(rs.getString("pago"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("❌ ERROR: " + e.getMessage());
        }
        return lista;
    }
}

