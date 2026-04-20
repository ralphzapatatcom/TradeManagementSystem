package com.trade.view;

import com.trade.CategoriaDAO;
import com.trade.model.Categoria;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoriaFrame extends JFrame {

    private CategoriaDAO dao = new CategoriaDAO();
    private JTable tablaCategorias;
    private DefaultTableModel model;
    private JTextField txtNombre;
    private JButton btnGuardar, btnModificar, btnEliminar;

    public CategoriaFrame() {
        setTitle("Gestión de Categorías");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Espaciado entre elementos

        // 1. Panel Superior (Formulario)
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre);
        add(panelFormulario, BorderLayout.NORTH);

        // 2. Tabla (Centro)
        model = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0);
        tablaCategorias = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tablaCategorias);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel de Botones (Sur - Debajo del grid)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos al iniciar
        listarCategorias();

        // Listeners para botones
        btnGuardar.addActionListener(e -> guardar());
        btnModificar.addActionListener(e -> modificar());
        btnEliminar.addActionListener(e -> eliminar());
        
        // Listener para seleccionar fila
        tablaCategorias.getSelectionModel().addListSelectionListener(e -> {
            if (tablaCategorias.getSelectedRow() != -1) {
                txtNombre.setText(model.getValueAt(tablaCategorias.getSelectedRow(), 1).toString());
            }
        });
    }

    private void listarCategorias() {
        model.setRowCount(0);
        List<Categoria> lista = dao.listar();
        for (Categoria c : lista) {
            model.addRow(new Object[]{c.getId(), c.getNombre()});
        }
    }

    private void guardar() {
        if (dao.insertar(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Guardado con éxito");
            listarCategorias();
            txtNombre.setText("");
        }
    }

    private void modificar() {
        int row = tablaCategorias.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Categoria c = new Categoria(id, txtNombre.getText());
            if (dao.modificar(c)) {
                JOptionPane.showMessageDialog(this, "Modificado con éxito");
                listarCategorias();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría");
        }
    }

    private void eliminar() {
        int row = tablaCategorias.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado con éxito");
                listarCategorias();
                txtNombre.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoriaFrame().setVisible(true));
    }
}