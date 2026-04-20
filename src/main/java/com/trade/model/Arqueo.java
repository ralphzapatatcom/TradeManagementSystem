package com.trade.model;

import java.sql.Timestamp;

public class Arqueo {
    private int id;
    private Timestamp fechaApertura;
    private double montoInicial;
    private double montoFinal;
    private int idUsuario;

    public Arqueo() {}

    public Arqueo(int id, Timestamp fechaApertura, double montoInicial, double montoFinal, int idUsuario) {
        this.id = id;
        this.fechaApertura = fechaApertura;
        this.montoInicial = montoInicial;
        this.montoFinal = montoFinal;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Timestamp getFechaApertura() { return fechaApertura; }
    public void setFechaApertura(Timestamp fechaApertura) { this.fechaApertura = fechaApertura; }
    public double getMontoInicial() { return montoInicial; }
    public void setMontoInicial(double montoInicial) { this.montoInicial = montoInicial; }
    public double getMontoFinal() { return montoFinal; }
    public void setMontoFinal(double montoFinal) { this.montoFinal = montoFinal; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}