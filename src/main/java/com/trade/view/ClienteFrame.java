package com.trade.view;

import com.trade.ClienteDAO;
import com.trade.model.Cliente;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClienteFrame extends JFrame {

    private JTextField txtId, txtDni, txtNombre, txtEmail;
    private JButton btnGuardar, btnModificar, btnEliminar, btnNuevo;
    private JTable tablaClientes;
    private DefaultTableModel modelo;
    private ClienteDAO cliDao = new ClienteDAO();

    public ClienteFrame() {
        initComponents();
        this.setSize(950, 420); 
        this.setTitle("Mantenimiento de Clientes - TradeSystem");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listarClientes(); // Este es el método interno de esta clase
    }

    private void initComponents() {
        // --- PANEL LATERAL ---
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(280, 0));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(12); txtId.setEditable(false);
        txtId.setBackground(new Color(235, 235, 235));
        txtDni = new JTextField(12);
        txtNombre = new JTextField(12);
        txtEmail = new JTextField(12);

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; panelForm.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("DNI/RFC:"), gbc);
        gbc.gridx = 1; panelForm.add(txtDni, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panelForm.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panelForm.add(txtEmail, gbc);

        // Panel Botones
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
        modelo.addColumn("DNI/RFC");
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");

        tablaClientes = new JTable(modelo);
        tablaClientes.setRowHeight(20);
        JScrollPane scroll = new JScrollPane(tablaClientes);

        this.setLayout(new BorderLayout());
        this.add(panelLateral, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);

        // --- LÓGICA DE EVENTOS ---

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila != -1) {
                    txtId.setText(tablaClientes.getValueAt(fila, 0).toString());
                    txtDni.setText(tablaClientes.getValueAt(fila, 1).toString());
                    txtNombre.setText(tablaClientes.getValueAt(fila, 2).toString());
                    txtEmail.setText(tablaClientes.getValueAt(fila, 3).toString());
                }
            }
        });

        btnGuardar.addActionListener(e -> {
            if (txtDni.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y DNI son obligatorios");
                return;
            }
            Cliente cl = new Cliente(0, txtDni.getText(), txtNombre.getText(), txtEmail.getText());
            if (cliDao.registrarCliente(cl)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Cliente registrado con éxito");
            }
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar");
                return;
            }
            Cliente cl = new Cliente(Integer.parseInt(txtId.getText()), txtDni.getText(), txtNombre.getText(), txtEmail.getText());
            if (cliDao.modificarCliente(cl)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Modificado con éxito");
            } else {
                JOptionPane.showMessageDialog(this, "Error al intentar modificar");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente");
                return;
            }
            int pregunta = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar al cliente?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (pregunta == JOptionPane.YES_OPTION) {
                if (cliDao.eliminarCliente(Integer.parseInt(txtId.getText()))) {
                    limpiarYRefrescar();
                    JOptionPane.showMessageDialog(this, "Eliminado con éxito");
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar: El cliente tiene ventas asociadas.", 
                            "Error de Integridad", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNuevo.addActionListener(e -> limpiarCampos());
    }

    // CORRECCIÓN AQUÍ: Llamada a cliDao.listar() en lugar de listarClientes()
    private void listarClientes() {
        modelo.setRowCount(0);
        List<Cliente> lista = cliDao.listar(); // Cambiado para coincidir con el DAO
        if (lista != null) {
            for (Cliente c : lista) {
                modelo.addRow(new Object[]{c.getId(), c.getDniRfc(), c.getNombre(), c.getEmail()});
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText(""); txtDni.setText("");
        txtNombre.setText(""); txtEmail.setText("");
        txtDni.requestFocus();
    }

    private void limpiarYRefrescar() {
        limpiarCampos();
        listarClientes();
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        java.awt.EventQueue.invokeLater(() -> new ClienteFrame().setVisible(true));
    }
}