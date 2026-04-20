package com.trade.view;

import com.trade.VentaDAO;
import com.trade.model.*;
import com.trade.ClienteDAO;
import com.trade.UsuarioDAO;
import com.trade.MetodoPagoDAO;
import com.trade.ProductoDAO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentaFrame extends JFrame {

    private JComboBox<String> cbCliente, cbUsuario, cbPago;
    private JComboBox<Producto> cbProducto;
    private JTextField txtCantidad, txtTotalVenta;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;
    private List<DetalleVenta> listaDetalles = new ArrayList<>();
    private VentaDAO vDao = new VentaDAO();
    private double totalAcumulado = 0.0;

    public VentaFrame() {
        initComponents();
        this.setSize(950, 550);
        this.setTitle("Punto de Venta - TradeSystem");
        this.setLocationRelativeTo(null);
        cargarCombos();
    }

    private void initComponents() {
        cbCliente = new JComboBox<>();
        cbUsuario = new JComboBox<>();
        cbPago = new JComboBox<>();
        cbProducto = new JComboBox<>();

        cbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    Producto p = (Producto) value;
                    setText(p.getNombre() + " ($" + String.format(Locale.US, "%.2f", p.getPrecioVenta()) + ")");
                }
                return this;
            }
        });

        txtCantidad = new JTextField("1", 5);
        txtTotalVenta = new JTextField("0.00", 10);
        txtTotalVenta.setEditable(false);
        txtTotalVenta.setFont(new Font("Arial", Font.BOLD, 18));

        modeloCarrito = new DefaultTableModel(new Object[]{"ID", "Producto", "Cant", "Precio", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCarrito = new JTable(modeloCarrito);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnVender = new JButton("Finalizar Venta");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelSuperior = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panelSuperior.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; panelSuperior.add(cbCliente, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 3; panelSuperior.add(cbUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelSuperior.add(new JLabel("Producto:"), gbc);
        gbc.gridx = 1; panelSuperior.add(cbProducto, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Cant:"), gbc);
        gbc.gridx = 3; panelSuperior.add(txtCantidad, gbc);
        gbc.gridx = 4; panelSuperior.add(btnAgregar, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelSuperior.add(new JLabel("Método Pago:"), gbc);
        gbc.gridx = 1; panelSuperior.add(cbPago, gbc);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.add(new JLabel("TOTAL: $"));
        panelInferior.add(txtTotalVenta);
        panelInferior.add(btnVender);
        panelInferior.add(btnLimpiar);

        this.setLayout(new BorderLayout());
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarAlCarrito());
        btnVender.addActionListener(e -> ejecutarVenta());
        btnLimpiar.addActionListener(e -> limpiarTodo());

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void cargarCombos() {
        try {
            List<Cliente> clientes = (List<Cliente>) new ClienteDAO().listar();
            if (clientes != null) {
                cbCliente.removeAllItems();
                clientes.forEach(c -> cbCliente.addItem(c.getId() + " - " + c.getNombre()));
            }

            List<Usuario> usuarios = (List<Usuario>) new UsuarioDAO().listar();
            if (usuarios != null) {
                cbUsuario.removeAllItems();
                usuarios.forEach(u -> cbUsuario.addItem(u.getId() + " - " + u.getUsername()));
            }

            List<MetodoPago> pagos = (List<MetodoPago>) new MetodoPagoDAO().listar();
            if (pagos != null) {
                cbPago.removeAllItems();
                pagos.forEach(m -> cbPago.addItem(m.getId() + " - " + m.getMetodo()));
            }

            List<Producto> productos = (List<Producto>) new ProductoDAO().listar();
            if (productos != null) {
                cbProducto.removeAllItems();
                for (Producto p : productos) cbProducto.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void agregarAlCarrito() {
        Producto p = (Producto) cbProducto.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        try {
            String textoCantidad = txtCantidad.getText().replaceAll("[^\\d.]", "").trim();
            if (textoCantidad.isEmpty()) return;
            int cant = (int) Double.parseDouble(textoCantidad);

            if (cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }

            double subtotal = cant * p.getPrecioVenta();

            modeloCarrito.addRow(new Object[]{p.getId(), p.getNombre(), cant, String.format(Locale.US, "%.2f", p.getPrecioVenta()), String.format(Locale.US, "%.2f", subtotal)});
            
            DetalleVenta dv = new DetalleVenta();
            dv.setIdProducto(p.getId());
            dv.setCantidad(cant);
            dv.setPrecioUnitario(p.getPrecioVenta());
            listaDetalles.add(dv);

            totalAcumulado += subtotal;
            txtTotalVenta.setText(String.format(Locale.US, "%.2f", totalAcumulado));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + e.getMessage());
        }
    }

    private void ejecutarVenta() {
        if (listaDetalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.");
            return;
        }

        try {
            if (cbCliente.getSelectedItem() == null || cbUsuario.getSelectedItem() == null || cbPago.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione cliente, usuario y método de pago.");
                return;
            }

            int idCliente = Integer.parseInt(cbCliente.getSelectedItem().toString().split(" - ")[0]);
            int idUsuario = Integer.parseInt(cbUsuario.getSelectedItem().toString().split(" - ")[0]);
            int idPago = Integer.parseInt(cbPago.getSelectedItem().toString().split(" - ")[0]);

            Venta v = new Venta();
            v.setIdCliente(idCliente);
            v.setIdUsuario(idUsuario);
            v.setIdPago(idPago);
            v.setTotal(totalAcumulado);

            // CORRECCIÓN AQUÍ: Capturamos el String del resultado
            String resultado = vDao.guardarVentaCompleta(v, listaDetalles);

            if (resultado == null) {
                // ÉXITO
                JOptionPane.showMessageDialog(this, "¡Venta realizada con éxito!");
                limpiarTodo();
            } else {
                // ERROR (Resultado contiene el mensaje del problema)
                JOptionPane.showMessageDialog(this, "No se pudo realizar la venta:\n" + resultado, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fatal al finalizar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarTodo() {
        modeloCarrito.setRowCount(0);
        listaDetalles.clear();
        totalAcumulado = 0.0;
        txtTotalVenta.setText("0.00");
        txtCantidad.setText("1");
    }

    public static void main(String args[]) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        java.awt.EventQueue.invokeLater(() -> new VentaFrame().setVisible(true));
    }
}