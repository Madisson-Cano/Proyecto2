package com.empresa.globitos;

public class Clientes {
	private String nombre;
    private String direccion;
    private String telefono;
    private String email;

	public Clientes(String nombre, String direccion, String telefono, String email) {
		// TODO Auto-generated constructor stub
		this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

} 
