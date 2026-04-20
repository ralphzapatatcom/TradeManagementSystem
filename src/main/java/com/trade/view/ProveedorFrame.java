package com.trade.view;

import com.trade.ProveedorDAO;
import com.trade.model.Proveedor;
import javax.swing.*;
import javax.swing.text.*; // Import necesario para el filtro
import java.awt.*;

public class ProveedorFrame extends JFrame {

    private JTextField txtEmpresa, txtContacto, txtTelefono;
    private JButton btnGuardar, btnLimpiar;
    private ProveedorDAO proveedorDAO;

    public ProveedorFrame() {
        proveedorDAO = new ProveedorDAO();
        
        // Configurar Layout al ContentPane
        this.getContentPane().setLayout(new GridBagLayout());
        
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Registro de Proveedores");
        setSize(450, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- FUENTES CON CUERPO (NEGRITA) ---
        Font labelFont = new Font("Tahoma", Font.BOLD, 13);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 13);
        Font buttonFont = new Font("Tahoma", Font.BOLD, 13);
        Dimension campoDimension = new Dimension(220, 28);

        // --- Fila 0: Empresa ---
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblEmpresa = new JLabel("Empresa:");
        lblEmpresa.setFont(labelFont);
        add(lblEmpresa, gbc);

        txtEmpresa = new JTextField();
        txtEmpresa.setFont(fieldFont);
        txtEmpresa.setPreferredSize(campoDimension);
        gbc.gridx = 1;
        add(txtEmpresa, gbc);

        // --- Fila 1: Contacto ---
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblContacto = new JLabel("Contacto:");
        lblContacto.setFont(labelFont);
        add(lblContacto, gbc);

        txtContacto = new JTextField();
        txtContacto.setFont(fieldFont);
        txtContacto.setPreferredSize(campoDimension);
        gbc.gridx = 1;
        add(txtContacto, gbc);

        // --- Fila 2: Teléfono ---
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblTel = new JLabel("Teléfono:");
        lblTel.setFont(labelFont);
        add(lblTel, gbc);

        txtTelefono = new JTextField();
        txtTelefono.setFont(fieldFont);
        txtTelefono.setPreferredSize(campoDimension);
        
        // --- APLICACIÓN DEL FILTRO DE VALIDACIÓN ---
        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new LimitadorTelefono());
        
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        // --- Fila 3: Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBotones.setOpaque(false);

        btnGuardar = new JButton("GUARDAR");
        btnGuardar.setFont(buttonFont);
        btnGuardar.setBackground(new Color(180, 200, 255));
        btnGuardar.setForeground(Color.BLACK);

        btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setFont(buttonFont);
        btnLimpiar.setBackground(new Color(230, 230, 230));
        btnLimpiar.setForeground(Color.BLACK);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 15, 10, 15);
        add(panelBotones, gbc);

        // --- Eventos ---
        btnGuardar.addActionListener(e -> guardarProveedor());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void guardarProveedor() {
        String empresa = txtEmpresa.getText().trim();
        String contacto = txtContacto.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (empresa.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La empresa y el teléfono son obligatorios.");
            return;
        }

        Proveedor p = new Proveedor();
        p.setNombreEmpresa(empresa);
        p.setContacto(contacto);
        p.setTelefono(telefono);

        if (proveedorDAO.registrarProveedor(p)) {
            JOptionPane.showMessageDialog(this, "✅ Proveedor registrado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Error al registrar.");
        }
    }

    private void limpiarCampos() {
        txtEmpresa.setText("");
        txtContacto.setText("");
        txtTelefono.setText("");
        txtEmpresa.requestFocus();
    }

    // --- CLASE PARA VALIDAR NÚMEROS Y LÍMITE DE 10 DÍGITOS ---
    private class LimitadorTelefono extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            // Solo permite números y verifica que la longitud final no pase de 10
            if (string.matches("\\d*") && (fb.getDocument().getLength() + string.length()) <= 10) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            // Solo permite números y verifica que la longitud final no pase de 10
            if (text.matches("\\d*") && (fb.getDocument().getLength() - length + text.length()) <= 10) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProveedorFrame().setVisible(true));
    }
}