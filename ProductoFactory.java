package com.empresa.globitos;

public class ProductoFactory {
    public Producto crearProducto(String tipo, String nombre, int cantidad, float precio, int tiempoProduccion) {
        if (tipo.equalsIgnoreCase("Fabrica")) {
            return new ProductoFabrica(nombre, tipo, cantidad, precio, tiempoProduccion);
        } else {
            return new Producto(nombre, tipo, cantidad, precio);
        }
    }
}