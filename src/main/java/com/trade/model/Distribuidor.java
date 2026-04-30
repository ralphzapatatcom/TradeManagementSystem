package com.trade.model;

public class Distribuidor {
    private int id;
    private String nombreEmpresa;
    private String nombreContacto;
    private String celular;

    public Distribuidor() {}

    public Distribuidor(int id, String nombreEmpresa, String nombreContacto, String celular) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreContacto = nombreContacto;
        this.celular = celular;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombreEmpresa() { return nombreEmpresa; }
    public void setNombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; }
    public String getNombreContacto() { return nombreContacto; }
    public void setNombreContacto(String nombreContacto) { this.nombreContacto = nombreContacto; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
}
