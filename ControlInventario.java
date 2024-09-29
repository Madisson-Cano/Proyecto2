package com.empresa.globitos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class ControlInventario {
    private static ControlInventario instance; // Instancia única
    private List<Producto> productos;
    private List<Clientes> clientes;
    private ProductoFactory productoFactory; // Para crear productos

    // Constructor privado
    ControlInventario() {
        this.productos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.productoFactory = new ProductoFactory(); // Inicializar la fábrica de productos
    }

    // Método para obtener la instancia única
    public static ControlInventario getInstance() {
        if (instance == null) {
            instance = new ControlInventario();
        }
        return instance;
    }

    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        } else {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
    }

    public Producto buscarProducto(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío.");
        }
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    public void cargarDesdeArchivo(String rutaArchivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            JSONArray jsonArray = new JSONArray(contenido);
            productos.clear(); // Limpiar la lista existente

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Producto producto;

                // Verificar si el producto tiene el campo tiempoProduccion
                if (jsonObject.has("tiempoProduccion")) {
                    producto = productoFactory.crearProducto(
                        "Fabrica", // Tipo
                        jsonObject.getString("nombre"),
                        jsonObject.getInt("cantidadDisponible"),
                        jsonObject.getFloat("precio"),
                        jsonObject.getInt("tiempoProduccion")
                    );
                } else {
                    producto = productoFactory.crearProducto(
                        "Normal", // Tipo
                        jsonObject.getString("nombre"),
                        jsonObject.getInt("cantidadDisponible"),
                        jsonObject.getFloat("precio"),
                        0 // Tiempo de producción no se necesita
                    );
                }
                productos.add(producto);
                System.out.println("Producto cargado: " + producto.getNombre()); // Mensaje de depuración
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    public void guardarEnArchivo(String rutaArchivo) {
        JSONArray jsonArray = new JSONArray();

        for (Producto producto : productos) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", producto.getNombre());
            jsonObject.put("tipo", producto.getTipo());
            jsonObject.put("cantidadDisponible", producto.getCantidadDisponible());
            jsonObject.put("precio", producto.getPrecio());

            // Si es un ProductoFabrica, agregar tiempo de producción
            if (producto instanceof ProductoFabrica) {
                jsonObject.put("tiempoProduccion", ((ProductoFabrica) producto).getTiempoProduccion());
            }

            jsonArray.put(jsonObject);
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(jsonArray.toString(4)); // Escribe el JSON con una indentación de 4 espacios
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void cargarDesdeArchivoExterno(String rutaArchivoExterno) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivoExterno)));
            JSONArray jsonArray = new JSONArray(contenido);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Producto producto;

                // Verificar si el producto tiene el campo tiempoProduccion
                if (jsonObject.has("tiempoProduccion")) {
                    producto = productoFactory.crearProducto(
                        "Fabrica",
                        jsonObject.getString("nombre"),
                        jsonObject.getInt("cantidadDisponible"),
                        jsonObject.getFloat("precio"),
                        jsonObject.getInt("tiempoProduccion")
                    );
                } else {
                    producto = productoFactory.crearProducto(
                        "Normal",
                        jsonObject.getString("nombre"),
                        jsonObject.getInt("cantidadDisponible"),
                        jsonObject.getFloat("precio"),
                        0
                    );
                }
                productos.add(producto);
                System.out.println("Producto cargado desde archivo externo: " + producto.getNombre()); // Mensaje de depuración
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo externo: " + e.getMessage());
        }
    }

    public void agregarProducto(Scanner scanner) {
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el tipo de producto: ");
        String tipo = scanner.nextLine();

        System.out.print("Ingrese la cantidad disponible: ");
        int cantidadDisponible = scanner.nextInt();

        System.out.print("Ingrese el precio: ");
        float precio = scanner.nextFloat();
        scanner.nextLine(); // Consumir nueva línea

        // Pregunta si es un producto de fábrica y obtener tiempo de producción si es necesario
        int tiempoProduccion = 0;
        System.out.print("¿Es un producto de fábrica? (s/n): ");
        String esProductoFabrica = scanner.nextLine();

        if (esProductoFabrica.equalsIgnoreCase("s")) {
            System.out.print("Ingrese el tiempo de producción: ");
            tiempoProduccion = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea
        }

        // Crea el producto usando la fábrica
        Producto nuevoProducto;
        if (tiempoProduccion > 0) {
            nuevoProducto = productoFactory.crearProducto("Fabrica", nombre, cantidadDisponible, precio, tiempoProduccion);
        } else {
            nuevoProducto = productoFactory.crearProducto("Normal", nombre, cantidadDisponible, precio, 0);
        }

        agregarProducto(nuevoProducto);
        System.out.println("Producto agregado: " + nuevoProducto.getNombre());
    }

    public void agregarCliente(Clientes cliente) {
        if (cliente != null) {
            clientes.add(cliente);
            guardarClientesEnArchivo("clientes.json"); // Guardar al archivo JSON
        } else {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
    }

    public void guardarClientesEnArchivo(String rutaArchivo) {
        JSONArray jsonArray = new JSONArray();

        for (Clientes cliente : clientes) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nombre", cliente.getNombre());
            jsonObject.put("direccion", cliente.getDireccion());
            jsonObject.put("telefono", cliente.getTelefono());
            jsonObject.put("email", cliente.getEmail());
            jsonArray.put(jsonObject);
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write(jsonArray.toString(4)); // Escribe el JSON con indentación de 4 espacios
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void cargarClientesDesdeArchivo(String rutaArchivo) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            JSONArray jsonArray = new JSONArray(contenido);
            clientes.clear(); // Limpiar la lista existente

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Clientes cliente = new Clientes(
                    jsonObject.getString("nombre"),
                    jsonObject.getString("direccion"),
                    jsonObject.getString("telefono"),
                    jsonObject.getString("email")
                );
                clientes.add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}