package com.crnahuas.comicshopapp;

import com.crnahuas.comicshopapp.comics.Comics;
import com.crnahuas.comicshopapp.users.Usuarios;
import com.crnahuas.comicshopapp.validacion.Validacion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeSet;

// Sistema principal que administra la tienda de cómics y usuarios.
// Utiliza colecciones, manejo de archivos y validaciones para una gestión robusta.
public class ComicShopApp {

    // Almacena todos los cómics disponibles.
    static ArrayList<Comics> comicsDisponibles = new ArrayList<>();
    // Asocia RUT con los objetos Usuario.
    static HashMap<String, Usuarios> usuarios = new HashMap<>();
    // Asegura que códigos de cómics sea unico.
    static HashSet<String> codigosUnicos = new HashSet<>();
    // Ordenada RUTs para mostrar/exportar usuarios ordenadamente.
    static TreeSet<String> usuariosOrdenados = new TreeSet<>();
    // Lectura desde consola.
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        // Bucle principal del menú
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
                switch (opcion) {
                    case 1 ->
                        cargarComicsDesdeArchivo("comics.csv");
                    case 2 ->
                        registrarUsuario();
                    case 3 ->
                        agregarCompraUsuario();
                    case 4 ->
                        guardarUsuarios("usuarios.txt");
                    case 5 ->
                        mostrarComicsDisponibles();
                    case 6 ->
                        eliminarUsuario();
                    case 7 ->
                        eliminarComic();
                    case 8 ->
                        exportarUsuariosCSV("usuarios.csv");
                    case 9 ->
                        System.out.println("\nGracias por usar ComicCollectorSystem. ¡Hasta la próxima!");
                    default ->
                        System.out.println("\nOpción inválida. Intente nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nDebe ingresar un número válido. Intente nuevamente.");
            } catch (Exception e) {
                System.out.println("\nOcurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 9);

    }

    // Opciones del menú.
    public static void mostrarMenu() {
        System.out.println("\n=== ComicCollectorSystem ===\n");
        System.out.println("1. Cargar cómics desde archivo");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Agregar compra a usuario");
        System.out.println("4. Guardar usuarios y compras");
        System.out.println("5. Mostrar cómics disponibles");
        System.out.println("6. Eliminar usuario");
        System.out.println("7. Eliminar cómic");
        System.out.println("8. Exportar usuarios a CSV");
        System.out.println("9. Salir");
        System.out.println("\nSelecciona una opción:");
    }

    // Carga cómics desde un archivo CSV
    public static void cargarComicsDesdeArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                if (procesarLineaComic(linea)) {
                    contador++;
                }
            }

            System.out.println("\nSe cargaron " + contador + " cómics correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("\nEl archivo no fue encontrado: " + ruta);
        } catch (IOException e) {
            System.out.println("\nError al leer el archivo: " + e.getMessage());
        }
    }

    // Procesa una línea del CSV y la convierte en objeto Comic.
    private static boolean procesarLineaComic(String linea) {
        try {
            String[] datos = linea.split(",");
            if (datos.length < 4) {
                System.out.println("\nLínea incompleta: " + linea);
                return false;
            }

            Comics comic = new Comics(datos[0], datos[1], datos[2], Double.parseDouble(datos[3]));
            if (codigosUnicos.add(comic.getCodigo())) {
                comicsDisponibles.add(comic);
                return true;
            }

        } catch (NumberFormatException e) {
            System.out.println("\nError de formato numérico en línea: " + linea);
        }

        return false;
    }

    // Registra un nuevo usuario con validación de RUT y nombre.
    public static void registrarUsuario() {
        try {
            System.out.print("Ingrese RUT: ");
            String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", ""); // Elimina puntos y espacios.

            if (!Validacion.validarRut(rut)) {
                System.out.println("\nFormato de RUT inválido.");
                return;
            }

            if (usuarios.containsKey(rut)) {
                System.out.println("\nEl usuario ya está registrado.");
                return;
            }

            System.out.print("Ingrese nombre: ");
            String nombre = sc.nextLine().trim();

            if (!Validacion.validarNombre(nombre)) {
                System.out.println("\nEl nombre debe contener solo letras y espacios.");
                return;
            }

            usuarios.put(rut, new Usuarios(rut, nombre));
            usuariosOrdenados.add(rut);
            System.out.println("\nUsuario registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("\nError al registrar el usuario: " + e.getMessage());
        }
    }

    // Agrega una compra a un usuario registrado.
    public static void agregarCompraUsuario() {
        try {
            System.out.print("Ingrese RUT del usuario: ");
            String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", "");

            Usuarios usuario = usuarios.get(rut);
            if (usuario == null) {
                System.out.println("\nUsuario no encontrado.");
                return;
            }

            mostrarComicsDisponibles();
            System.out.print("Ingrese código del cómic a comprar: ");
            String codigo = sc.nextLine().trim();

            Optional<Comics> comic = comicsDisponibles.stream()
                    .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                    .findFirst();

            if (comic.isPresent()) {
                usuario.agregarCompra(comic.get());
                System.out.println("\nCompra registrada correctamente.");
            } else {
                System.out.println("\nCómic no encontrado.");
            }

        } catch (Exception e) {
            System.out.println("\nError al agregar compra: " + e.getMessage());
        }
    }

    // Guarda los usuarios y sus compras en un archivo de texto.
    public static void guardarUsuarios(String archivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String rut : usuariosOrdenados) {
                Usuarios u = usuarios.get(rut);
                bw.write(u.toString() + "\n");
                for (Comics c : u.getCompras()) {
                    bw.write("  -> " + c + "\n");
                }
            }

            System.out.println("\nUsuarios y compras guardados correctamente en " + archivo);
        } catch (IOException e) {
            System.out.println("\nError al guardar archivo: " + e.getMessage());
        }
    }

    // Muestra todos los cómics disponibles actualmente.
    public static void mostrarComicsDisponibles() {
        if (comicsDisponibles.isEmpty()) {
            System.out.println("\nNo hay cómics disponibles.");
        } else {
            System.out.println("\n=== Cómics Disponibles ===");
            for (Comics c : comicsDisponibles) {
                System.out.println(c);
            }
        }
    }
    
    // Elimina un usuario del sistema por RUT.
    public static void eliminarUsuario() {
        System.out.print("Ingrese RUT del usuario a eliminar: ");
        String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", "");
        if (usuarios.remove(rut) != null) {
            usuariosOrdenados.remove(rut);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    // Elimina un cómic del sistema por su código
    public static void eliminarComic() {
        System.out.print("Ingrese código del cómic a eliminar: ");
        String codigo = sc.nextLine().trim();
        boolean eliminado = comicsDisponibles.removeIf(c -> c.getCodigo().equalsIgnoreCase(codigo));

        if (eliminado) {
            codigosUnicos.remove(codigo);
            System.out.println("Cómic eliminado correctamente.");
        } else {
            System.out.println("Cómic no encontrado.");
        }
    }
    
        // Exporta los usuarios a un archivo CSV con nombre y total de compras
    public static void exportarUsuariosCSV(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("RUT,Nombre,Cantidad de Compras");
            for (String rut : usuariosOrdenados) {
                Usuarios u = usuarios.get(rut);
                pw.printf("%s,%s,%d%n", u.getRut(), u.getNombre(), u.getCompras().size());
            }
            System.out.println("Usuarios exportados correctamente a " + archivo);
        } catch (IOException e) {
            System.out.println("Error al exportar CSV: " + e.getMessage());
        }
    }

}
