package com.trade.view;

import com.trade.MetodoPagoDAO;
import com.trade.model.MetodoPago;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MetodoPagoFrame extends JFrame {

    private JTextField txtId, txtMetodo;
    private JButton btnGuardar, btnModificar, btnEliminar, btnNuevo;
    private JTable tablaMetodos;
    private DefaultTableModel modelo;
    private MetodoPagoDAO mpDao = new MetodoPagoDAO();

    public MetodoPagoFrame() {
        initComponents();
        this.setSize(950, 400); 
        this.setTitle("Métodos de Pago - TradeSystem");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listarMetodos();
    }

    private void initComponents() {
        // --- PANEL LATERAL ---
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(280, 0));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Método"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(12); txtId.setEditable(false);
        txtId.setBackground(new Color(235, 235, 235));
        txtMetodo = new JTextField(12);

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; panelForm.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Método:"), gbc);
        gbc.gridx = 1; panelForm.add(txtMetodo, gbc);

        // Panel Botones (Altura reducida)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 8));
        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnNuevo = new JButton("Nuevo");

        Dimension btnSize = new Dimension(115, 25); 
        btnGuardar.setPreferredSize(btnSize);
        btnModificar.setPreferredSize(btnSize);
        btnEliminar.setPreferredSize(btnSize);
        btnNuevo.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnNuevo);

        panelLateral.add(panelForm, BorderLayout.NORTH);
        panelLateral.add(panelBotones, BorderLayout.CENTER);

        // --- TABLA ---
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modelo.addColumn("ID");
        modelo.addColumn("MÉTODO DE PAGO");

        tablaMetodos = new JTable(modelo);
        tablaMetodos.setRowHeight(20);
        JScrollPane scroll = new JScrollPane(tablaMetodos);

        this.setLayout(new BorderLayout());
        this.add(panelLateral, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);

        // --- EVENTOS ---
        tablaMetodos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = tablaMetodos.getSelectedRow();
                if (f != -1) {
                    txtId.setText(tablaMetodos.getValueAt(f, 0).toString());
                    txtMetodo.setText(tablaMetodos.getValueAt(f, 1).toString());
                }
            }
        });

        btnGuardar.addActionListener(e -> {
            if(txtMetodo.getText().isEmpty()) return;
            MetodoPago mp = new MetodoPago(0, txtMetodo.getText());
            if (mpDao.registrar(mp)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Método guardado");
            }
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            MetodoPago mp = new MetodoPago(Integer.parseInt(txtId.getText()), txtMetodo.getText());
            if (mpDao.modificar(mp)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Modificado con éxito");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar este método de pago?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (mpDao.eliminar(Integer.parseInt(txtId.getText()))) {
                    limpiarYRefrescar();
                    JOptionPane.showMessageDialog(this, "Eliminado correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se puede eliminar si hay ventas con este método.");
                }
            }
        });

        btnNuevo.addActionListener(e -> limpiarCampos());
    }

    private void listarMetodos() {
        modelo.setRowCount(0);
        List<MetodoPago> lista = mpDao.listar();
        for (MetodoPago mp : lista) {
            modelo.addRow(new Object[]{mp.getId(), mp.getMetodo()});
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtMetodo.setText("");
        txtMetodo.requestFocus();
    }

    private void limpiarYRefrescar() {
        limpiarCampos();
        listarMetodos();
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        java.awt.EventQueue.invokeLater(() -> new MetodoPagoFrame().setVisible(true));
    }
}
