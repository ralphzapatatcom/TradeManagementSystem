package com.trade.view;

import com.trade.ArqueoDAO;
import com.trade.model.Arqueo;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ArqueoFrame extends JFrame {

    private JTextField txtMontoInicial, txtUsuario;
    private JButton btnAbrir, btnCerrar;
    private ArqueoDAO arqueoDAO;
    private int idUsuarioLogueado = 1;

    public ArqueoFrame() {
        arqueoDAO = new ArqueoDAO();
        this.getContentPane().setLayout(new GridBagLayout());
        
        initComponents();
        configurarVentana();
    }

    private void configurarVentana() {
        setTitle("Control de Arqueo de Caja");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Filtro de números ---
        DocumentFilter soloNumeros = new SoloNumerosFilter();

        // --- Fila 0: Usuario ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Usuario:"), gbc);
        
        txtUsuario = new JTextField("Administrador", 15);
        txtUsuario.setEditable(false);
        gbc.gridx = 1;
        add(txtUsuario, gbc);

        // --- Fila 1: Monto Inicial ---
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Monto Inicial:"), gbc);
        
        txtMontoInicial = new JTextField(10);
        ((AbstractDocument) txtMontoInicial.getDocument()).setDocumentFilter(soloNumeros);
        gbc.gridx = 1;
        add(txtMontoInicial, gbc);

        // --- Fila 2: Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAbrir = new JButton("Abrir Caja");
        btnAbrir.setBackground(new Color(40, 167, 69));
        btnAbrir.setForeground(Color.WHITE);
        
        btnCerrar = new JButton("Cerrar Caja");
        btnCerrar.setEnabled(false);
        btnCerrar.setBackground(new Color(220, 53, 69));
        btnCerrar.setForeground(Color.WHITE);
        
        panelBotones.add(btnAbrir);
        panelBotones.add(btnCerrar);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(panelBotones, gbc);

        // Eventos
        btnAbrir.addActionListener(e -> accionAbrir());
        btnCerrar.addActionListener(e -> accionCerrar());
    }

    private void accionAbrir() {
        String texto = txtMontoInicial.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un monto.");
            return;
        }

        double monto = Double.parseDouble(texto);
        if (monto <= 0) {
            JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero.");
            return;
        }

        Arqueo nuevo = new Arqueo();
        nuevo.setMontoInicial(monto);
        nuevo.setIdUsuario(idUsuarioLogueado);

        if (arqueoDAO.abrirCaja(nuevo)) {
            JOptionPane.showMessageDialog(this, "✅ Caja abierta.");
            btnAbrir.setEnabled(false);
            txtMontoInicial.setEditable(false);
            btnCerrar.setEnabled(true);
        }
    }

    private void accionCerrar() {
        // Creamos un diálogo personalizado para asegurar que NO acepten letras
        JTextField txtMontoFinal = new JTextField(10);
        ((AbstractDocument) txtMontoFinal.getDocument()).setDocumentFilter(new SoloNumerosFilter());
        
        Object[] message = {"Ingrese el monto final:", txtMontoFinal};
        int option = JOptionPane.showConfirmDialog(this, message, "Cierre de Caja", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String texto = txtMontoFinal.getText().trim();
            if (!texto.isEmpty()) {
                double montoF = Double.parseDouble(texto);
                if (arqueoDAO.cerrarCaja(1, montoF)) {
                    JOptionPane.showMessageDialog(this, "🏁 Caja cerrada.");
                    this.dispose();
                }
            }
        }
    }

    // --- FILTRO REUTILIZABLE (CLASE INTERNA) ---
    private class SoloNumerosFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string.matches("[0-9]*\\.?[0-9]*")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
            if (newText.matches("[0-9]*\\.?[0-9]*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArqueoFrame().setVisible(true));
    }
}