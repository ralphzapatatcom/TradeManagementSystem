package com.trade;

import com.trade.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean registrarCliente(Cliente cl) {
        String sql = "INSERT INTO clientes (dni_rfc, nombre, email) VALUES (?, ?, ?)";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cl.getDniRfc());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getEmail());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error Insert: " + e.getMessage());
            return false;
        }
    }

    // He cambiado el nombre de listarClientes() a listar() 
    // y me aseguré de que devuelva List<Cliente> en lugar de Object
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("id"), rs.getString("dni_rfc"), 
                                     rs.getString("nombre"), rs.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("Error List: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificarCliente(Cliente cl) {
        String sql = "UPDATE clientes SET dni_rfc=?, nombre=?, email=? WHERE id=?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cl.getDniRfc());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getEmail());
            ps.setInt(4, cl.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Update: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";
        try (Connection con = TradeManagementSystem.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error Delete: " + e.getMessage());
            return false;
        }
    }
    
    // ELIMINÉ el método listar() que lanzaba el "UnsupportedOperationException"
}
