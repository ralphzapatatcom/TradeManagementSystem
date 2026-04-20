package com.trade;

import com.trade.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Cambiamos el nombre a listarProductos para que tu ProductoFrame no de error
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setNombre(rs.getString("nombre"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setPrecioCompra(rs.getDouble("precio_compra"));
                p.setPrecioVenta(rs.getDouble("precio_venta"));
                p.setStockActual(rs.getInt("stock_actual"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    // Método alias para que VentaFrame (que usa .listar()) también funcione
    public List<Producto> listar() {
        return listarProductos();
    }
    public Producto buscarPorCodigo(String codigo) {
    String sql = "SELECT * FROM productos WHERE codigo_barras = ?";
    try (Connection con = TradeManagementSystem.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Producto(
                rs.getInt("id"),
                rs.getString("codigo_barras"),
                rs.getString("nombre"),
                rs.getInt("id_categoria"),
                rs.getDouble("precio_compra"),
                rs.getDouble("precio_venta"),
                rs.getInt("stockActual")
            );
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar: " + e.getMessage());
    }
    return null;
}

    public boolean registrarProducto(Producto pro) {
        String sql = "INSERT INTO productos (codigo_barras, nombre, id_categoria, precio_compra, precio_venta, stock_actual) VALUES (?,?,?,?,?,?)";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pro.getCodigoBarras());
            ps.setString(2, pro.getNombre());
            ps.setInt(3, pro.getIdCategoria());
            ps.setDouble(4, pro.getPrecioCompra());
            ps.setDouble(5, pro.getPrecioVenta());
            ps.setInt(6, pro.getStockActual());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar: " + e.getMessage());
            return false;
        }
    }

    public boolean modificarProducto(Producto pro) {
        String sql = "UPDATE productos SET codigo_barras=?, nombre=?, id_categoria=?, precio_compra=?, precio_venta=?, stock_actual=? WHERE id=?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pro.getCodigoBarras());
            ps.setString(2, pro.getNombre());
            ps.setInt(3, pro.getIdCategoria());
            ps.setDouble(4, pro.getPrecioCompra());
            ps.setDouble(5, pro.getPrecioVenta());
            ps.setInt(6, pro.getStockActual());
            ps.setInt(7, pro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}