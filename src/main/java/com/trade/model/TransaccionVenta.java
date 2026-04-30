package com.trade.model;

import java.sql.Timestamp;

public class TransaccionVenta {
    private int id;
    private Timestamp fechaHora;
    private int idComprador;
    private int idUsuario;
    private int idFormaPago;
    private double montoTotal;

    public TransaccionVenta() {}

    public TransaccionVenta(int id, Timestamp fechaHora, int idComprador, int idUsuario, int idFormaPago, double montoTotal) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.idComprador = idComprador;
        this.idUsuario = idUsuario;
        this.idFormaPago = idFormaPago;
        this.montoTotal = montoTotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Timestamp getFechaHora() { return fechaHora; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }
    public int getIdComprador() { return idComprador; }
    public void setIdComprador(int idComprador) { this.idComprador = idComprador; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdFormaPago() { return idFormaPago; }
    public void setIdFormaPago(int idFormaPago) { this.idFormaPago = idFormaPago; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
}