package com.trade.model;

import java.sql.Timestamp;

public class Venta {
    private int id;
    private Timestamp fecha;
    private int idCliente;
    private int idUsuario;
    private int idPago;
    private double total;

    // Campos auxiliares para mostrar nombres en la interfaz (JTable)
    private String nombreCliente;
    private String nombreUsuario;
    private String nombrePago;

    // Constructor vacío
    public Venta() {
    }

    // Constructor completo (usado por el DAO al listar)
    public Venta(int id, Timestamp fecha, int idCliente, int idUsuario, int idPago, double total) {
        this.id = id;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idPago = idPago;
        this.total = total;
    }

    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    // Getters y Setters para campos auxiliares
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getNombrePago() { return nombrePago; }
    public void setNombrePago(String nombrePago) { this.nombrePago = nombrePago; }

    @Override
    public String toString() {
        return "Venta #" + id + " | Total: " + total;
    }
}