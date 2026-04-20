package com.trade.view;

import com.trade.EntradaInventarioDAO; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EntradaInventarioFrame extends JFrame {

    private JTextField txtIdProducto, txtIdProveedor, txtCantidad;
    private JButton btnGuardar;
    private EntradaInventarioDAO entradaDAO; 

    public EntradaInventarioFrame() {
        // Inicializar el nuevo DAO
        entradaDAO = new EntradaInventarioDAO();

        setTitle("Registrar Entrada de Mercancía");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        this.getContentPane().setLayout(new GridBagLayout());
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- ID Producto ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID Producto:"), gbc);
        txtIdProducto = new JTextField(15);
        gbc.gridx = 1;
        add(txtIdProducto, gbc);

        // --- ID Proveedor ---
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("ID Proveedor:"), gbc);
        txtIdProveedor = new JTextField(15);
        gbc.gridx = 1;
        add(txtIdProveedor, gbc);

        // --- Cantidad ---
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Cantidad:"), gbc);
        txtCantidad = new JTextField(15);
        gbc.gridx = 1;
        add(txtCantidad, gbc);

        // --- Botón ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnGuardar = new JButton("Registrar Entrada");
        btnGuardar.addActionListener(this::procesarEntrada);
        add(btnGuardar, gbc);
    }

    private void procesarEntrada(ActionEvent e) {
        try {
            // Capturar y convertir a enteros (los campos deben tener solo números)
            int idProd = Integer.parseInt(txtIdProducto.getText().trim());
            int idProv = Integer.parseInt(txtIdProveedor.getText().trim());
            int cant = Integer.parseInt(txtCantidad.getText().trim());

            // Llamada al DAO (que hará el INSERT y el UPDATE a la vez)
            boolean exito = entradaDAO.registrarEntrada(idProd, idProv, cant);

            if (exito) {
                JOptionPane.showMessageDialog(this, "✅ Registro exitoso y stock actualizado.");
                // Limpiar campos
                txtIdProducto.setText("");
                txtIdProveedor.setText("");
                txtCantidad.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error: Verifique que los IDs existan en la BD.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Por favor, ingrese solo números enteros en todos los campos.");
        }
    }

    // --- MÉTODO EJECUTABLE ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EntradaInventarioFrame().setVisible(true);
        });
    }
}