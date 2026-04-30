package com.trade;

import com.trade.model.Venta;
import com.trade.model.DetalleVenta;
import com.trade.model.ReporteVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    /**
     * Registra una venta completa: Encabezado, Detalles y descuenta Stock.
     * Utiliza una transacción para asegurar la integridad de los datos.
     */
    public String guardarVentaCompleta(Venta v, List<DetalleVenta> detalles) {
        String sqlVenta = "INSERT INTO ventas (id_cliente, id_usuario, id_pago, total) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlStock = "UPDATE productos SET stock_actual = stock_actual - ? WHERE id = ? AND stock_actual >= ?";

        Connection con = null;
        try {
            con = TradeManagementSystem.getConexion();
            con.setAutoCommit(false); // Inicia transacción

            int idVentaGenerado = 0;
            // 1. Insertar Venta
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

            // 2. Insertar Detalles y Actualizar Stock
            try (PreparedStatement psD = con.prepareStatement(sqlDetalle);
                 PreparedStatement psS = con.prepareStatement(sqlStock)) {

                for (DetalleVenta dv : detalles) {
                    // Registrar detalle
                    psD.setInt(1, idVentaGenerado);
                    psD.setInt(2, dv.getIdProducto());
                    psD.setInt(3, dv.getCantidad());
                    psD.setDouble(4, dv.getPrecioUnitario());
                    psD.executeUpdate();

                    // Actualizar Stock
                    psS.setInt(1, dv.getCantidad());
                    psS.setInt(2, dv.getIdProducto());
                    psS.setInt(3, dv.getCantidad());
                    
                    if (psS.executeUpdate() == 0) {
                        throw new Exception("Stock insuficiente para el producto ID: " + dv.getIdProducto());
                    }
                }
            }
            con.commit(); // Todo correcto
            return null; 
        } catch (Exception e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return e.getMessage();
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    /**
     * Lista todas las ventas con información detallada (Joins con clientes, usuarios y pagos)
     */
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
            System.err.println("❌ ERROR al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Genera el reporte solicitado de ventas detalladas
     */
    public List<ReporteVenta> obtenerReporteDetallado() {
        List<ReporteVenta> lista = new ArrayList<>();
        String sql = "SELECT v.fecha, v.id AS folio_venta, c.nombre AS cliente, " +
                     "u.username AS vendedor, p.nombre AS producto, dv.cantidad, " +
                     "dv.precio_unitario, (dv.cantidad * dv.precio_unitario) AS subtotal, " +
                     "m.metodo AS metodo_pago " +
                     "FROM ventas v " +
                     "JOIN detalle_ventas dv ON v.id = dv.id_venta " +
                     "JOIN productos p ON dv.id_producto = p.id " +
                     "JOIN clientes c ON v.id_cliente = c.id " +
                     "JOIN usuarios u ON v.id_usuario = u.id " +
                     "JOIN metodos_pago m ON v.id_pago = m.id " +
                     "ORDER BY v.fecha DESC";

        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new ReporteVenta(
                    rs.getTimestamp("fecha"),
                    rs.getInt("folio_venta"),
                    rs.getString("cliente"),
                    rs.getString("vendedor"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getDouble("subtotal"),
                    rs.getString("metodo_pago")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene el total de ventas del día actual
     */
    public double obtenerTotalVentasDelDia() {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM ventas WHERE DATE(fecha) = CURDATE()";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al sumar ventas del día: " + e.getMessage());
        }
        return 0.0;
    }
}