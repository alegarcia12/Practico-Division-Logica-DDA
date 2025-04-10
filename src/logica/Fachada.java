package logica;

import java.util.ArrayList;
import java.util.Collection;

public class Fachada {

	private static Fachada instancia = new Fachada();

	private ControlClientes controlClientes = ControlClientes.getInstancia();

	private ControlFacturas controlFacturas = ControlFacturas.getInstancia();

	private ControlStock controlStock = ControlStock.getInstancia();

	public static Fachada getInstancia() {
		return instancia;
	}

	/**
	 *  
	 */
	private void Fachada() {
            
	}

    public boolean agregar(Proveedor unProveedor) {
        return controlStock.agregar(unProveedor);
    }

    public Collection<Cliente> getClientes() {
       return controlClientes.getClientes();
    }

    public ArrayList<Proveedor> getProveedores() {
       return controlStock.getProveedores();
    }

    public boolean agregar(Cliente unCliente) {
       return controlClientes.agregar(unCliente);
    }

    public Collection<Producto> getProductos() {
        return controlStock.getProductos();
    }

    public boolean agregar(Producto unProducto) {
        return controlStock.agregar(unProducto);
    }

    public Proveedor getProveedor(int posicion) {
        return controlStock.getProveedor(posicion);
    }

    public Cliente getCliente(String cedula){
        return controlClientes.getCliente(cedula);
    }

    public Producto getProducto(int codigo) {
        return controlStock.getProducto(codigo);
    }

    public void agregar(Factura fc) {
        controlFacturas.agregar(fc);
    }

    public Producto getProductoMenorPrecio() {
       return controlStock.getProductoMenorPrecio();
    }

    public Collection<Factura> ultimaFcDeCadaClienteQueCompro(Producto producto) {
        return controlFacturas.ultimaFcDeCadaClienteQueCompro(producto);
    }
        

}
