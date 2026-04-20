
package com.trade.model;

public class Cliente {
    private int id;
    private String dniRfc;
    private String nombre;
    private String email;

    public Cliente() {}

    public Cliente(int id, String dniRfc, String nombre, String email) {
        this.id = id;
        this.dniRfc = dniRfc;
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDniRfc() { return dniRfc; }
    public void setDniRfc(String dniRfc) { this.dniRfc = dniRfc; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}