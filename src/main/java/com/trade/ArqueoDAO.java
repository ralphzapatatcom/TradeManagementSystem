package com.trade;

import com.trade.model.Arqueo;
import java.sql.*;

public class ArqueoDAO {

    public boolean abrirCaja(Arqueo arqueo) {
        String sql = "INSERT INTO arqueos_caja (fecha_apertura, monto_inicial, id_usuario) VALUES (NOW(), ?, ?)";
        try (Connection con = TradeManagementSystem.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, arqueo.getMontoInicial());
            ps.setInt(2, arqueo.getIdUsuario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al abrir caja: " + e.getMessage());
            return false;
        }
    }

    public boolean cerrarCaja(int idArqueo, double montoFinal) {
        // Se actualiza el monto final y se guarda el momento exacto del cierre
        String sql = "UPDATE arqueos_caja SET monto_final = ?, fecha_cierre = NOW() WHERE id = ?";
        try (Connection con = TradeManagementSystem.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, montoFinal);
            ps.setInt(2, idArqueo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al cerrar caja: " + e.getMessage());
            return false;
        }
    }
}