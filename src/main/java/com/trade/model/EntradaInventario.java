package com.trade.model; // 1. Agregado el paquete para que las otras clases la encuentren

import java.sql.Timestamp;

public class EntradaInventario {
    private int id;
    private int idProducto;
    private int idProveedor;
    private int cantidad;
    private Timestamp fechaEntrada;

    // Constructor vacío (Necesario para frameworks y reflexión)
    public EntradaInventario() {
    }

    // Constructor completo
    public EntradaInventario(int id, int idProducto, int idProveedor, int cantidad, Timestamp fechaEntrada) {
        this.id = id;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.cantidad = cantidad;
        this.fechaEntrada = fechaEntrada;
    }

    // --- Getters y Setters ---
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Timestamp getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(Timestamp fechaEntrada) { this.fechaEntrada = fechaEntrada; }

    // 2. Método toString: Te ahorrará horas de frustración al imprimir en consola para probar
    @Override
    public String toString() {
        return "EntradaInventario{" + "id=" + id + ", idProducto=" + idProducto + 
               ", idProveedor=" + idProveedor + ", cantidad=" + cantidad + 
               ", fecha=" + fechaEntrada + '}';
    }
}


