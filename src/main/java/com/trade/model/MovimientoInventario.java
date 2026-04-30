package com.trade.model;

import java.sql.Timestamp;

public class MovimientoInventario {
    private int id;
    private int idArticulo;
    private int idDistribuidor;
    private int cantidadEntrante;
    private Timestamp fechaRegistro;

    public MovimientoInventario() {}

    public MovimientoInventario(int id, int idArticulo, int idDistribuidor, int cantidadEntrante, Timestamp fechaRegistro) {
        this.id = id;
        this.idArticulo = idArticulo;
        this.idDistribuidor = idDistribuidor;
        this.cantidadEntrante = cantidadEntrante;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }
    public int getIdDistribuidor() { return idDistribuidor; }
    public void setIdDistribuidor(int idDistribuidor) { this.idDistribuidor = idDistribuidor; }
    public int getCantidadEntrante() { return cantidadEntrante; }
    public void setCantidadEntrante(int cantidadEntrante) { this.cantidadEntrante = cantidadEntrante; }
    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
