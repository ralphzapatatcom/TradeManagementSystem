package com.trade.view;

import com.trade.ArqueoDAO;
import com.trade.VentaDAO;
import com.trade.model.ReporteVenta; // Asegúrate de tener esta clase DTO
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ArqueoFrame extends JFrame {

    private JLabel lblTotalVentas;
    private JTextField txtIdArqueo;
    private JButton btnCerrarCaja;
    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    
    private VentaDAO ventaDAO;
    private ArqueoDAO arqueoDAO;

    public ArqueoFrame() {
        ventaDAO = new VentaDAO();
        arqueoDAO = new ArqueoDAO();
        
        setTitle("Control de Arqueo y Reporte Detallado");
        setSize(900, 600); // Tamaño mayor para mostrar la tabla
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // Layout más flexible para combinar tabla y controles
        
        initComponents();
        cargarVentasDiarias();
        cargarTablaReporte(); // Ejecuta tu consulta SQL
    }

    private void initComponents() {
        // Panel Superior para controles de Cierre
        JPanel panelControles = new JPanel(new GridBagLayout());
        panelControles.setBorder(BorderFactory.createTitledBorder("Gestión de Caja"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // -- Elementos de Control --
        gbc.gridx = 0; gbc.gridy = 0;
        panelControles.add(new JLabel("Total Ventas del Día:"), gbc);
        
        lblTotalVentas = new JLabel("$ 0.00");
        lblTotalVentas.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotalVentas.setForeground(Color.BLUE);
        gbc.gridx = 1;
        panelControles.add(lblTotalVentas, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelControles.add(new JLabel("ID del Arqueo:"), gbc);

        txtIdArqueo = new JTextField(10);
        gbc.gridx = 1;
        panelControles.add(txtIdArqueo, gbc);

        btnCerrarCaja = new JButton("Cerrar Caja");
        btnCerrarCaja.addActionListener(e -> realizarCierre());
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panelControles.add(btnCerrarCaja, gbc);

        // -- Configuración de la Tabla --
        String[] columnas = {"Fecha", "Folio", "Cliente", "Vendedor", "Producto", "Cant", "P. Unit", "Subtotal", "Metodo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaReporte = new JTable(modeloTabla);
        
        // Agregar componentes al Frame
        add(panelControles, BorderLayout.NORTH);
        add(new JScrollPane(tablaReporte), BorderLayout.CENTER);
    }

    private void cargarTablaReporte() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<ReporteVenta> lista = ventaDAO.obtenerReporteDetallado(); // Tu consulta SQL
        
        for (ReporteVenta r : lista) {
            Object[] fila = {
                r.getFecha(),
                r.getFolioVenta(),
                r.getCliente(),
                r.getVendedor(),
                r.getProducto(),
                r.getCantidad(),
                String.format("%.2f", r.getPrecioUnitario()),
                String.format("%.2f", r.getSubtotal()),
                r.getMetodoPago()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void cargarVentasDiarias() {
        double total = ventaDAO.obtenerTotalVentasDelDia();
        lblTotalVentas.setText(String.format("$ %.2f", total));
    }

    private void realizarCierre() {
        try {
            if (txtIdArqueo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa el ID del arqueo.");
                return;
            }

            int idArqueo = Integer.parseInt(txtIdArqueo.getText());
            double totalHoy = ventaDAO.obtenerTotalVentasDelDia();

            int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Cerrar caja con monto: $ " + totalHoy + "?", 
                    "Confirmar Cierre", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (arqueoDAO.cerrarCaja(idArqueo, totalHoy)) {
                    JOptionPane.showMessageDialog(this, "Caja cerrada correctamente.");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error. Verifica el ID.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new ArqueoFrame().setVisible(true));
    }
}