package com.trade;

import com.trade.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // 1. Insertar Categoría
    public boolean insertar(String nombre) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar categoría: " + e.getMessage());
            return false;
        }
    }

    // 2. Listar todas las categorías
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar categorías: " + e.getMessage());
        }
        return lista;
    }

    // 3. Modificar
    public boolean modificar(Categoria c) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar categoría: " + e.getMessage());
            return false;
        }
    }

    // 4. Eliminar
    public boolean eliminar(int id) {
        String sql = "DELETE FROM categorias WHERE id = ?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar categoría: " + e.getMessage());
            return false;
        }
    }

    // 5. TU QUERY SOLICITADO: Reporte de Productos por Categoría
    // Retorna una lista de arrays de objetos: [nombre_categoria, total_productos]
    public List<Object[]> obtenerReporteCategorias() {
        List<Object[]> reporte = new ArrayList<>();
        String sql = "SELECT c.nombre, COUNT(p.id) AS total_productos " +
                     "FROM categorias c " +
                     "LEFT JOIN productos p ON c.id = p.id_categoria " +
                     "GROUP BY c.id";
                     
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("nombre");
                fila[1] = rs.getInt("total_productos");
                reporte.add(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al generar reporte de categorías: " + e.getMessage());
        }
        return reporte;
    }
}