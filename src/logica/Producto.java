/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.Objects;

/**
 *
 * @author magda
 */
public class Producto {

    private String nombre;
    private int precio;
    private int unidades;
    private Proveedor proveedor;
    private int codigo;

    public Producto() {
    }

    public Producto(String nombre, int precio, int stock, Proveedor proveedor) {
        this.nombre = nombre;
        this.precio = precio;
        this.unidades = stock;
        this.proveedor = proveedor;

        proveedor.agregar(this);
    }

    public int getUnidades() {
        return unidades;
    }

    public boolean setUnidades(int unidades) {
        int unidadesAnteriores = this.unidades;
        this.unidades = unidades;

        Boolean unidadesOk = validarUnidades();
        if (!unidadesOk) {
            this.unidades = unidadesAnteriores;
        }

        return unidadesOk;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public float getPrecio() {
        return precio;
    }

    public Boolean setPrecio(int precio) {
        int precioAnterior = this.precio;

        this.precio = precio;
        Boolean precioOk = validarPrecio();
        if (!precioOk) {
            this.precio = precioAnterior;
        }
        return precioOk;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        String nombreAnterior = this.nombre;
        this.nombre = nombre;
        Boolean nombreOk = validarNombre();

        if (!nombreOk) {
            this.nombre = nombreAnterior;
        }

        return nombreOk;
    }

    @Override
    public String toString() {
        return "Producto{codigo=" + codigo + ", nombre=" + nombre + ", precio=" + precio + ", unidades=" + unidades + ", proveedor=" + proveedor + ", codigo=" + codigo + '}';
    }

    public void setCodigo(int cod) {
        codigo = cod;
    }

    public int getCodigo() {
        return codigo;
    }

    public Boolean validar() {
        return validarPrecio() && validarUnidades() && validarNombre();
    }

    private Boolean validarNombre() {
        return this.nombre != null && !this.nombre.isBlank();
    }

    private Boolean validarPrecio() {
        return this.precio >= 0;
    }

    private Boolean validarUnidades() {
        return this.unidades > 0;
    }

    Boolean hayStock(int cantidad) {
        return getUnidades() >= cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

}
