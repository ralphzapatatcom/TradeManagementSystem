package com.trade;

import com.trade.view.EntradaInventarioFrame; // Importamos la nueva interfaz
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

public class TradeManagementSystem {

    public static Connection getConexion() throws SQLException {
        // Reutilizamos tu configuración centralizada
        String url = "jdbc:mariadb://localhost:3306/sistema_comercio";
        String user = "root";
        String pass = ""; 
        return java.sql.DriverManager.getConnection(url, user, pass);
    }

    public static void main(String[] args) {
        // 1. Estética: Hace que la ventana se vea como una aplicación nativa de Windows/Mac/Linux
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // 2. Lanzamiento de la Interfaz
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // Validamos la conexión antes de mostrar nada
                if (probarConexion()) {
                    // Abrimos el módulo de entradas que estamos construyendo
                    EntradaInventarioFrame frame = new EntradaInventarioFrame();
                    frame.setLocationRelativeTo(null); // Centrar en pantalla
                    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                    
                    System.out.println("✅ Sistema de Inventario iniciado.");
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "No se pudo conectar a la base de datos.\nVerifica que XAMPP/MariaDB esté activo.", 
                        "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean probarConexion() {
        try (Connection c = getConexion()) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
