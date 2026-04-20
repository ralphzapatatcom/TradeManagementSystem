package com.trade;

import com.trade.model.MetodoPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {

    public boolean registrar(MetodoPago mp) {
        String sql = "INSERT INTO metodos_pago (metodo) VALUES (?)";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mp.getMetodo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar método: " + e.getMessage());
            return false;
        }
    }

    public List<MetodoPago> listar() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodos_pago";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new MetodoPago(rs.getInt("id"), rs.getString("metodo")));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar métodos: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(MetodoPago mp) {
        String sql = "UPDATE metodos_pago SET metodo = ? WHERE id = ?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mp.getMetodo());
            ps.setInt(2, mp.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar método: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM metodos_pago WHERE id = ?";
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
