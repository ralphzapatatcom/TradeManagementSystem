package com.trade;

import com.trade.model.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // IMPORTANTE
import java.util.List;      // IMPORTANTE

public class ProveedorDAO {

    /**
     * Registra un nuevo proveedor en la base de datos.
     */
    public boolean registrarProveedor(Proveedor prov) {
        String sql = "INSERT INTO proveedores (nombre_empresa, contacto, telefono) VALUES (?,?,?)";
        
        try (Connection con = TradeManagementSystem.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, prov.getNombreEmpresa());
            ps.setString(2, prov.getContacto());
            ps.setString(3, prov.getTelefono());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un número de teléfono ya existe en la tabla proveedores.
     */
    public boolean existeTelefono(String telefono) {
        String sql = "SELECT COUNT(*) FROM proveedores WHERE telefono = ?";
        
        try (Connection con = TradeManagementSystem.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar teléfono: " + e.getMessage());
        }
        return false;
    }

    /**
     * Devuelve una lista de todos los proveedores para llenar los combos.
     */
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        
        // Usamos TradeManagementSystem.getConexion() para mantener la coherencia
        try (Connection con = TradeManagementSystem.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setId(rs.getInt("id"));
                prov.setNombreEmpresa(rs.getString("nombre_empresa"));
                prov.setContacto(rs.getString("contacto"));
                prov.setTelefono(rs.getString("telefono"));
                lista.add(prov);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }
}
