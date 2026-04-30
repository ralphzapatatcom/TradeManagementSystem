package com.trade.model;

import java.sql.Timestamp;

public class ReporteVenta {
    private Timestamp fecha;
    private int folioVenta;
    private String cliente;
    private String vendedor;
    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String metodoPago;

    // Constructor vacío
    public ReporteVenta() {}

    // Constructor con campos
    public ReporteVenta(Timestamp fecha, int folioVenta, String cliente, String vendedor, 
                        String producto, int cantidad, double precioUnitario, 
                        double subtotal, String metodoPago) {
        this.fecha = fecha;
        this.folioVenta = folioVenta;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.metodoPago = metodoPago;
    }

    // Getters
    public Timestamp getFecha() { return fecha; }
    public int getFolioVenta() { return folioVenta; }
    public String getCliente() { return cliente; }
    public String getVendedor() { return vendedor; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    public String getMetodoPago() { return metodoPago; }
}