package com.empresa.globitos; 

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String numeroPedido;
    private String fecha;
    private Clientes cliente;
    private List<PedidoProducto> productos;  
    private List<Modificacion> modificaciones;

    public Pedido(String numeroPedido, String fecha, Clientes cliente) {
        this.numeroPedido = numeroPedido;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = new ArrayList<>(); 
        this.modificaciones = new ArrayList<>();
    }
    
    public void agregarProducto(Producto producto, int cantidad) {
        this.productos.add(new PedidoProducto(producto, cantidad));
    }

    public void agregarModificacion(Modificacion modificacion) {
        this.modificaciones.add(modificacion);
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public List<PedidoProducto> getProductos() {
        return productos;
    }

    public List<Modificacion> getModificaciones() {
        return modificaciones;
    }
}
