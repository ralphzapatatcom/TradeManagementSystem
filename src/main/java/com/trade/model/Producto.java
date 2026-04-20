package com.trade.model;

/**
 * Clase POJO que representa la entidad Producto en la base de datos.
 */
public class Producto {

    private int id;
    private String codigoBarras;
    private String nombre;
    private int idCategoria;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;

    // Constructor vacío
    public Producto() {
    }

    // Constructor completo
    public Producto(int id, String codigoBarras, String nombre, int idCategoria,
            double precioCompra, double precioVenta, int stockActual) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.idCategoria = idCategoria;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
    }

    // --- Getters y Setters ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos para Código (Soporta ambos nombres)
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigo() {
        return codigoBarras;
    }

    public void setCodigo(String codigo) {
        this.codigoBarras = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    // Métodos para Stock (Soporta ambos nombres)
    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStock() {
        return stockActual;
    }

    public void setStock(int stock) {
        this.stockActual = stock;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", stock=" + stockActual + '}';
    }
}
