package com.empresa.globitos; 

public class ProductoFabrica extends Producto {
    private int tiempoProduccion;

    public ProductoFabrica(String nombre, String tipo, int cantidadDisponible, float precio, int tiempoProduccion) {
        super(nombre, tipo, cantidadDisponible, precio);
        setTiempoProduccion(tiempoProduccion); 
    }

    public int getTiempoProduccion() {
        return tiempoProduccion;
    }

    public void setTiempoProduccion(int tiempoProduccion) {
        if (tiempoProduccion > 0) {
            this.tiempoProduccion = tiempoProduccion;
        } else {
            throw new IllegalArgumentException("El tiempo de producción debe ser positivo.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " - Tiempo de Producción: " + tiempoProduccion + " días";
    }

    public boolean esFabricable() {
        return true;
    }
    public String getEstado() {
        if (getCantidadDisponible() > 0) {
            return "Disponible";
        } else {
            return "En producción";
        }
    }
}
