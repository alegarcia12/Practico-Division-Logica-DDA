package interfaz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import logica.Cliente;
import logica.Fachada;

import logica.Factura;
import logica.LineaFactura;
import logica.Producto;
import logica.Proveedor;
import utilidades.Consola;

public class IuConsola {

    /**
     * Ejecuta la consola
     */
    public void mostrarConsola() {
        boolean salir;
        do {
            
            int opcion = imprimirMenu();
            salir = procesarOpcion(opcion);
            
        } while (!salir);
    }

    /**
     * Imprime el menú y sus opciones a pantalla
     */
    private int imprimirMenu() {
        System.out.println("MENU");
        System.out.println("====");
        
        ArrayList<String> opciones = new ArrayList();
        opciones.add("Alta de Cliente");
        opciones.add("Alta de Proveedor");
        opciones.add("Alta de Producto");
        opciones.add("Alta de Factura");
        opciones.add("Mostrar cliente que compraron producto mas barato");
        opciones.add("Salir del menú");
        return Consola.menu(opciones);
    }

    /**
     * Captura la opción seleccionada por el usuario y ejecuta la acción
     * correspondiente
     */
    private boolean procesarOpcion(int opcion) {
        boolean salir = false;
        
        switch (opcion) {
            case 0:
                this.nuevoCliente();
                break;
            case 1:
                this.nuevoProveedor();
                break;
            case 2:
                this.nuevoProdcuto();
                break;
            case 3:
                this.nuevaFactura();
                break;
            case 4:
                this.consultaUltimaCompraDeClientesProductoMasBarato();
                break;
            case 5:
                salir = true;
                break;
            
        }
        return salir;
    }
    
    private void nuevoCliente() {
        
        System.out.println("ALTA DE CLIENTE");
        System.out.println("===============");
        
        Cliente unCliente = new Cliente();
        unCliente.setCedula(Consola.leer("Cedula:"));
        unCliente.setNombre(Consola.leer("Nombre:"));
        if (Fachada.getInstancia().agregar(unCliente)) {
            mostrarClientes();
        } else {
            System.out.println("EL CLIENTE NO FUE INGRESADO");
        }
        
    }
    
    private void mostrarClientes() {
        System.out.println("=================");
        System.out.println("CLIENTES ACTUALES");
        System.out.println("=================");
        Collection<Cliente> clientes = Fachada.getInstancia().getClientes();
        for (Cliente c : clientes) {
            System.out.println(c.getCedula() + " - " + c.getNombre());
        }
    }
    
    private void nuevoProveedor() {
        System.out.println("ALTA DE PROVEEDOR");
        System.out.println("===============");
        
        String nombre = Consola.leer("Nombre: ");
        Proveedor unProveedor = new Proveedor(nombre);
        
        if (Fachada.getInstancia().agregar(unProveedor)) {
            mostrarProveedores();
        } else {
            System.out.println("EL PROVEEDOR NO FUE INGRESADO");
        }
    }
    
    private void mostrarProveedores() {
        System.out.println("=================");
        System.out.println("PROVEEDORES ACTUALES");
        System.out.println("=================");
        Collection<Proveedor> proveedores = Fachada.getInstancia().getProveedores();
        for (Proveedor p : proveedores) {
            System.out.println(p.getNombre() + " - ");
        }
    }
    
    private void nuevoProdcuto() {
        System.out.println("ALTA DE PRODUCTO");
        System.out.println("===============");
        Producto producto = new Producto();
        
        while (!producto.setNombre(Consola.leer("Nombre: "))) {
        }
        
        boolean productoOk = false;
        if (producto.setUnidades(Consola.leerInt("Cantidad de Unidades: "))) {
            int idProv = Consola.menu(Fachada.getInstancia().getProveedores());
            producto.setProveedor(Fachada.getInstancia().getProveedor(idProv));
            
            int precio = Consola.leerInt("Precio: ");
            if (producto.setPrecio(precio)) {
                productoOk = Fachada.getInstancia().agregar(producto);
                mostrarProductos();
            }
        }
        
        if (!productoOk) {
            System.out.println("EL PRODUCTO NO FUE INGRESADO");
        }
        
    }

    //Mostrar Productos
    private void mostrarProductos() {
        System.out.println("=================");
        System.out.println("PRODUCTOS ACTUALES");
        System.out.println("=================");
        Collection<Producto> productos = Fachada.getInstancia().getProductos();
        for (Producto p : productos) {
            System.out.println(p.getNombre() + " - " + p.getPrecio() + " - " + p.getUnidades());
        }
    }
    
    private void nuevaFactura() {
        String cedula = Consola.leer("Cedula: ");
        Cliente cliente = Fachada.getInstancia().getCliente(cedula);
        
        if (cliente != null) {
            Boolean agregarProductos = true;
            Factura fc = new Factura(cliente);
            while (agregarProductos) {
                int codigo = Consola.leerInt("Codigo: ");
                int cantidad = Consola.leerInt("Cantidad: ");
                Producto producto = Fachada.getInstancia().getProducto(codigo);
                if (!fc.agregar(cantidad, producto)) {
                    Consola.println("No se pudo agregar el producto y la cantidad indicada.");
                }
                agregarProductos = quiereAgregarMasItems();
            }
            mostrarDetalleFc(fc);
            mostrarTotalFc(fc);
            if (quiereAgregarFc()) {
                Fachada.getInstancia().agregar(fc);
            }
        }
        
    }
    
    private Boolean quiereAgregarMasItems() {
        String respuesta = Consola.leer("Desea agregar mas productos? (s/n)");
        return "s".equals(respuesta);
    }
    
    private void mostrarDetalleFc(Factura fc) {
        for (LineaFactura lf : fc.getLineas()) {
            Producto p = lf.getProducto();
            String linea = p.getCodigo() + " " + p.getNombre() + " " + lf.getCantidad() + " " + lf.getTotal();
            Consola.println(linea);
        }
    }
    
    private void mostrarTotalFc(Factura fc) {
        Consola.println("Total Factura: " + fc.getTotal());
    }
    
    private boolean quiereAgregarFc() {
        String respuesta = Consola.leer("Quiere agrear la factura?");
        return "s".equals(respuesta);
    }
    
    private void consultaUltimaCompraDeClientesProductoMasBarato() {
        Producto producto = Fachada.getInstancia().getProductoMenorPrecio();
        
        if (producto != null) {
            Collection<Factura> facturas = Fachada.getInstancia().ultimaFcDeCadaClienteQueCompro(producto);
            mostrarDetalle(producto);
            mostrarClientesQueCompraron(facturas);
        } else {
            Consola.println("No se encontro el producto");
        }
        
    }
    
    private void mostrarDetalle(Producto producto) {
        String linea = "(" + producto.getCodigo() + ") " + producto.getNombre() + " - " + producto.getPrecio() + " " + producto.getUnidades();
        Consola.println(linea);
    }
    
    private void mostrarClientesQueCompraron(Collection<Factura> facturas) {
        if (facturas == null || facturas.isEmpty()) {
            Consola.println("No hay clientes que hayan comprado ese producto");
        } else {
            for (Factura fc : facturas) {
                Cliente c = fc.getCliente();
                Consola.println(c.getCedula() + " " + c.getNombre() + " " + fc.getFecha());
            }
        }
    }
}
