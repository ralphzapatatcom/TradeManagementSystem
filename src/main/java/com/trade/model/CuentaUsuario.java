package com.trade.model;

public class CuentaUsuario {
    private int id;
    private String alias;
    private String clave;
    private String nivelAcceso;

    public CuentaUsuario() {}

    public CuentaUsuario(int id, String alias, String clave, String nivelAcceso) {
        this.id = id;
        this.alias = alias;
        this.clave = clave;
        this.nivelAcceso = nivelAcceso;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public String getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(String nivelAcceso) { this.nivelAcceso = nivelAcceso; }
}
