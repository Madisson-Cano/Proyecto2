package com.empresa.globitos; 

public class Producto {
    private String nombre;
    private String tipo;
    private int cantidadDisponible;
    private float precio;
    private String estado; 

    public Producto(String nombre, String tipo, int cantidadDisponible, float precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        setCantidadDisponible(cantidadDisponible);
        setPrecio(precio);
        setEstado();
    }

    public String getEstado() {
		return estado;
	}

    public void setEstado() {
        // Si la cantidad es 0, el estado será "En producción" por defecto
        if (cantidadDisponible == 0) {
            this.estado = "En producción";
        } else {
            this.estado = "Disponible";
        }
    }

	public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public float getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidadDisponible(int cantidad) {
        if (cantidad >= 0) {
            this.cantidadDisponible = cantidad;
        } else {
            throw new IllegalArgumentException("Cantidad no puede ser negativa.");
        }
    }

    public void setPrecio(float precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            throw new IllegalArgumentException("Precio no puede ser negativo.");
        }
    }

    public void modificarProducto(String nuevoNombre, String nuevoTipo, int nuevaCantidad, float nuevoPrecio) {
        setNombre(nuevoNombre);
        setTipo(nuevoTipo);
        setCantidadDisponible(nuevaCantidad);
        setPrecio(nuevoPrecio);
    }

    public void reducirCantidadDisponible(int cantidad) {
        if (cantidad > 0 && cantidad <= this.cantidadDisponible) {
            this.cantidadDisponible -= cantidad;
            // Si la cantidad disponible llega a 0 por una venta o reducción, cambiar a "Agotado"
            if (this.cantidadDisponible == 0 && !this.estado.equals("En producción")) {
                this.estado = "Agotado";
            }
        } else {
            System.out.println("Cantidad no válida para reducir.");
        }
    }

    public String toString() {
        return nombre + " - Precio: " + precio + " - Estado: " + estado;
    }

}
