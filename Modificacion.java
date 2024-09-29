package com.empresa.globitos; 

public class Modificacion {
	private String descripcion;
    private float costo;

	public Modificacion(String descripcion, float costo) {
		// TODO Auto-generated constructor stub
		this.descripcion = descripcion;
        this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public float getCosto() {
		return costo;
	}

}
