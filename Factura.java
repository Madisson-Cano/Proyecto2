package com.empresa.globitos; 

public class Factura {

    private String numeroFactura;
    private String fechaEmision;
    private float montoTotal;
    private Pedido pedido;

    public Factura(String numeroFactura, String fechaEmision, float montoTotal, Pedido pedido) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.montoTotal = montoTotal;
        this.pedido = pedido;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void mostrarFactura() {
        System.out.println("Factura N°: " + numeroFactura);
        System.out.println("Fecha de Emisión: " + fechaEmision);
        System.out.println("Productos:");

        for (PedidoProducto pedidoProducto : pedido.getProductos()) {
            Producto producto = pedidoProducto.getProducto();
            int cantidad = pedidoProducto.getCantidad();

            System.out.println("- " + cantidad + " x " + producto.getNombre() + " - Q" + producto.getPrecio());
        }
        System.out.println("Modificaciones:");
        for (Modificacion modificacion : pedido.getModificaciones()) {
            System.out.println("- " + modificacion.getDescripcion() + " (Costo: Q" + modificacion.getCosto() + ")");
        }

        System.out.println("Monto Total: Q" + montoTotal);
    }
}
