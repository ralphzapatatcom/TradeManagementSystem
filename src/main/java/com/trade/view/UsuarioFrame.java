package com.trade.view;

import com.trade.UsuarioDAO;
import com.trade.model.Usuario;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioFrame extends JFrame {

    private JTextField txtId, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRol;
    private JButton btnGuardar, btnModificar, btnEliminar, btnNuevo;
    private JTable tablaUsuarios;
    private DefaultTableModel modelo;
    private UsuarioDAO usuDao = new UsuarioDAO();

    public UsuarioFrame() {
        initComponents();
        this.setSize(950, 420);
        this.setTitle("Gestión de Usuarios - TradeSystem");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        listarUsuarios();
    }

    private void initComponents() {
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(280, 0));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(12); txtId.setEditable(false);
        txtUsername = new JTextField(12);
        txtPassword = new JPasswordField(12);
        cbRol = new JComboBox<>(new String[]{"admin", "vendedor"});

        gbc.gridx = 0; gbc.gridy = 0; panelForm.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; panelForm.add(txtId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panelForm.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; panelForm.add(txtUsername, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panelForm.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panelForm.add(txtPassword, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panelForm.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; panelForm.add(cbRol, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 8));
        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnNuevo = new JButton("Nuevo");

        Dimension btnSize = new Dimension(115, 25);
        btnGuardar.setPreferredSize(btnSize); btnModificar.setPreferredSize(btnSize);
        btnEliminar.setPreferredSize(btnSize); btnNuevo.setPreferredSize(btnSize);

        panelBotones.add(btnGuardar); panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar); panelBotones.add(btnNuevo);

        panelLateral.add(panelForm, BorderLayout.NORTH);
        panelLateral.add(panelBotones, BorderLayout.CENTER);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modelo.addColumn("ID"); modelo.addColumn("Username"); modelo.addColumn("Rol");

        tablaUsuarios = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tablaUsuarios);

        this.setLayout(new BorderLayout());
        this.add(panelLateral, BorderLayout.WEST);
        this.add(scroll, BorderLayout.CENTER);

        // --- Eventos ---
        tablaUsuarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = tablaUsuarios.getSelectedRow();
                txtId.setText(tablaUsuarios.getValueAt(f, 0).toString());
                txtUsername.setText(tablaUsuarios.getValueAt(f, 1).toString());
                cbRol.setSelectedItem(tablaUsuarios.getValueAt(f, 2).toString());
                txtPassword.setText(""); // Por seguridad no mostramos la pass
            }
        });

        btnGuardar.addActionListener(e -> {
            Usuario u = new Usuario(0, txtUsername.getText(), new String(txtPassword.getPassword()), cbRol.getSelectedItem().toString());
            if (usuDao.registrar(u)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Usuario guardado");
            }
        });

        btnModificar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            Usuario u = new Usuario(Integer.parseInt(txtId.getText()), txtUsername.getText(), new String(txtPassword.getPassword()), cbRol.getSelectedItem().toString());
            if (usuDao.modificar(u)) {
                limpiarYRefrescar();
                JOptionPane.showMessageDialog(this, "Modificado con éxito");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                if (usuDao.eliminar(Integer.parseInt(txtId.getText()))) {
                    limpiarYRefrescar();
                    JOptionPane.showMessageDialog(this, "Eliminado");
                }
            }
        });

        btnNuevo.addActionListener(e -> limpiarCampos());
    }

    private void listarUsuarios() {
        modelo.setRowCount(0);
        List<Usuario> lista = usuDao.listar();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{u.getId(), u.getUsername(), u.getRol()});
        }
    }

    private void limpiarCampos() {
        txtId.setText(""); txtUsername.setText(""); txtPassword.setText("");
        cbRol.setSelectedIndex(0);
    }

    private void limpiarYRefrescar() {
        limpiarCampos(); listarUsuarios();
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        java.awt.EventQueue.invokeLater(() -> new UsuarioFrame().setVisible(true));
    }
}