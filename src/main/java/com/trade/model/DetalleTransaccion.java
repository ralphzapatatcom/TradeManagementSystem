package com.trade.model;

public class DetalleTransaccion {
    private int id;
    private int idVenta;
    private int idArticulo;
    private int cantidadVendida;
    private double precioUnitario;

    public DetalleTransaccion() {}

    public DetalleTransaccion(int id, int idVenta, int idArticulo, int cantidadVendida, double precioUnitario) {
        this.id = id;
        this.idVenta = idVenta;
        this.idArticulo = idArticulo;
        this.cantidadVendida = cantidadVendida;
        this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }
    public int getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(int cantidadVendida) { this.cantidadVendida = cantidadVendida; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}
