
package com.trade.model;

/**
 * Clase POJO que representa la entidad Proveedor.
 * Ajustada para ser compatible con ProveedorDAO y ProveedorFrame.
 */
public class Proveedor {
    private int id;
    private String nombreEmpresa; // Antes era 'nombre'
    private String contacto;      // Antes era 'ruc', pero tu Frame pide 'contacto'
    private String telefono;

    // Constructor vacío
    public Proveedor() {
    }

    // Constructor completo
    public Proveedor(int id, String nombreEmpresa, String contacto, String telefono) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    // --- Getters y Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombreEmpresa; // Útil para mostrar el nombre en ComboBoxes
    }
}