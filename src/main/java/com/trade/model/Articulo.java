package com.trade.model;

public class Articulo {
    private int id;
    private String codigo;
    private String descripcion;
    private int idCategoria;
    private double costo;
    private double precio;
    private int existencias;

    public Articulo() {}

    public Articulo(int id, String codigo, String descripcion, int idCategoria, double costo, double precio, int existencias) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.costo = costo;
        this.precio = precio;
        this.existencias = existencias;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getExistencias() { return existencias; }
    public void setExistencias(int existencias) { this.existencias = existencias; }
}
