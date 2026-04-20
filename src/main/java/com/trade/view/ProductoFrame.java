package com.trade.view;

import com.trade.ProductoDAO;
import com.trade.model.Producto;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductoFrame extends javax.swing.JFrame {

    private JTextField txtCodigo, txtNombre, txtPrecioCompra, txtPrecioVenta, txtStock, txtCategoria;
    private JButton btnGuardar, btnModificar, btnEliminar, btnNuevo;
    private JTable tablaProductos;
    private DefaultTableModel modelo;
    private ProductoDAO proDao = new ProductoDAO();

    public ProductoFrame() {
        initComponentsCustom();
        this.setTitle("Gestión de Inventario - Trade System");
        this.setSize(1000, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        listarProductos();
    }

    private void initComponentsCustom() {
        // Inicialización de campos
        txtCodigo = new JTextField(10);
        txtNombre = new JTextField(15);
        txtPrecioCompra = new JTextField(5);
        txtPrecioVenta = new JTextField(5);
        txtStock = new JTextField(5);
        txtCategoria = new JTextField("1"); 

        btnGuardar = new JButton("Guardar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnNuevo = new JButton("Nuevo");

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        panelForm.add(new JLabel(" Código:")); panelForm.add(txtCodigo);
        panelForm.add(new JLabel(" Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel(" P. Compra:")); panelForm.add(txtPrecioCompra);
        panelForm.add(new JLabel(" P. Venta:")); panelForm.add(txtPrecioVenta);
        panelForm.add(new JLabel(" Stock:")); panelForm.add(txtStock);
        panelForm.add(new JLabel(" ID Categoría:")); panelForm.add(txtCategoria);

        // Panel de Botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnNuevo);

        // Configuración de Tabla
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        modelo.addColumn("ID");
        modelo.addColumn("Código");
        modelo.addColumn("Nombre");
        modelo.addColumn("Stock");
        modelo.addColumn("Precio Venta"); // Aquí se mostrará formateado

        tablaProductos = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tablaProductos);

        // Evento para seleccionar datos de la tabla
        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaProductos.getSelectedRow();
                if (fila != -1) {
                    txtCodigo.setText(tablaProductos.getValueAt(fila, 1).toString());
                    txtNombre.setText(tablaProductos.getValueAt(fila, 2).toString());
                    txtStock.setText(tablaProductos.getValueAt(fila, 3).toString());
                    // El valor en la tabla es String "0.00", funciona directo con setText
                    txtPrecioVenta.setText(tablaProductos.getValueAt(fila, 4).toString());
                }
            }
        });

        // Ensamblado
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.add(panelForm, BorderLayout.NORTH);
        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(panelIzquierdo, BorderLayout.WEST);
        this.add(sp, BorderLayout.CENTER);

        // Lógica de botones
        btnGuardar.addActionListener(e -> {
            try {
                Producto pro = extraerDatosCampos();
                if (proDao.registrarProducto(pro)) {
                    JOptionPane.showMessageDialog(this, "Producto Registrado");
                    listarProductos();
                    limpiarCampos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Verifique que los campos numéricos sean correctos.");
            }
        });

        btnModificar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un producto");
                return;
            }
            try {
                Producto pro = extraerDatosCampos();
                pro.setId(Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString()));
                if (proDao.modificarProducto(pro)) {
                    JOptionPane.showMessageDialog(this, "Producto Actualizado");
                    listarProductos();
                    limpiarCampos();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar");
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila == -1) return;
            int id = Integer.parseInt(tablaProductos.getValueAt(fila, 0).toString());
            if (JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION) == 0) {
                if (proDao.eliminarProducto(id)) {
                    listarProductos();
                    limpiarCampos();
                }
            }
        });

        btnNuevo.addActionListener(e -> limpiarCampos());
    }

    private Producto extraerDatosCampos() {
        Producto pro = new Producto();
        pro.setCodigoBarras(txtCodigo.getText());
        pro.setNombre(txtNombre.getText());
        pro.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText()));
        pro.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText()));
        pro.setStockActual(Integer.parseInt(txtStock.getText()));
        pro.setIdCategoria(Integer.parseInt(txtCategoria.getText()));
        return pro;
    }

    public void listarProductos() {
        List<Producto> lista = proDao.listarProductos();
        modelo.setRowCount(0);
        Object[] ob = new Object[5];
        for (Producto p : lista) {
            ob[0] = p.getId();
            ob[1] = p.getCodigoBarras();
            ob[2] = p.getNombre();
            ob[3] = p.getStockActual();
            // --- CAMBIO AQUÍ: Formateo a 2 decimales ---
            ob[4] = String.format("%.2f", p.getPrecioVenta());
            modelo.addRow(ob);
        }
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStock.setText("");
        txtCategoria.setText("1");
        txtCodigo.requestFocus();
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        java.awt.EventQueue.invokeLater(() -> new ProductoFrame().setVisible(true));
    }
}