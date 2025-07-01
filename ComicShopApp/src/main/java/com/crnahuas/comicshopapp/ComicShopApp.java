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

    // Rutas de archivos centralizadas.
    private static final String RUTA_COMICS = "comics.csv";
    private static final String RUTA_USUARIOS_TXT = "usuarios.txt";
    private static final String RUTA_USUARIOS_CSV = "usuarios.csv";

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
                        cargarComicsDesdeArchivo(RUTA_COMICS);
                    case 2 ->
                        mostrarComicsDisponibles();
                    case 3 ->
                        registrarUsuario();
                    case 4 ->
                        agregarCompraUsuario();
                    case 5 ->
                        guardarUsuarios(RUTA_USUARIOS_TXT);
                    case 6 ->
                        eliminarUsuario();
                    case 7 ->
                        eliminarComic();
                    case 8 ->
                        exportarUsuariosCSV(RUTA_USUARIOS_CSV);
                    case 9 ->
                        System.out.println("\nGracias por usar ComicShopApp. ¡Hasta la próxima!");
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
        System.out.println("\n=== ComicShopApp ===\n");
        System.out.println("1. Cargar cómics desde archivo");
        System.out.println("2. Mostrar cómics disponibles");
        System.out.println("3. Registrar nuevo usuario");
        System.out.println("4. Compra cómics");
        System.out.println("5. Guardar usuario y compras");
        System.out.println("6. Eliminar usuario");
        System.out.println("7. Eliminar cómic");
        System.out.println("8. Exportar usuarios a CSV");
        System.out.println("9. Salir");
        System.out.print("\nSelecciona una opción: ");
    }

    // Carga cómics desde un archivo CSV
    public static void cargarComicsDesdeArchivo(String archivo) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            br.readLine(); // Saltar encabezado
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length != 4) {
                    System.out.println("\nError de formato en línea: " + linea);
                    continue;
                }
                String codigo = datos[0].trim();
                if (codigosUnicos.contains(codigo)) {
                    System.out.println("Código duplicado ignorado: " + codigo);
                    continue;
                }
                try {
                    String titulo = datos[1].trim();
                    String autor = datos[2].trim();
                    double precio = Double.parseDouble(datos[3].trim());

                    Comics nuevo = new Comics(codigo, titulo, autor, precio);
                    comicsDisponibles.add(nuevo);
                    codigosUnicos.add(codigo);
                    contador++;
                } catch (NumberFormatException e) {
                    System.out.println("\nError de formato numérico en línea: " + linea);
                }
            }
            System.out.println("\nCómics cargados: " + contador);
        } catch (IOException e) {
            System.out.println("\nNo se pudo leer el archivo: " + e.getMessage());
        }
    }

    // Registra un nuevo usuario con validación de RUT y nombre.
    public static void registrarUsuario() {
        try {
            System.out.print("\nIngrese RUT (XXXXXXXX-X o XX.XXX.XXX-X): ");
            String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", ""); // Elimina puntos y espacios

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
            System.out.print("Ingrese apellido paterno: ");
            String apellidoPaterno = sc.nextLine().trim();
            System.out.print("Ingrese apellido materno: ");
            String apellidoMaterno = sc.nextLine().trim();

            if (!Validacion.validarNombre(nombre) || !Validacion.validarNombre(apellidoPaterno) || !Validacion.validarNombre(apellidoMaterno)) {
                System.out.println("\nLos nombres y apellidos deben contener solo letras y espacios.");
                return;
            }

            usuarios.put(rut, new Usuarios(rut, nombre, apellidoPaterno, apellidoMaterno));
            usuariosOrdenados.add(rut);
            System.out.println("\nUsuario registrado exitosamente.");
        } catch (Exception e) {
            System.out.println("\nError al registrar el usuario: " + e.getMessage());
        }
    }

    // Agrega una compra a un usuario registrado.
    public static void agregarCompraUsuario() {
        try {
            System.out.print("\nIngrese RUT del usuario (XXXXXXXX-X o XX.XXX.XXX-X): ");
            String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", "");

            Usuarios usuario = usuarios.get(rut);
            if (usuario == null) {
                System.out.println("\nUsuario no encontrado.");
                return;
            }

            mostrarComicsDisponibles();
            System.out.print("\nIngrese código del cómic a comprar: ");
            String codigo = sc.nextLine().trim();

            Optional<Comics> comic = comicsDisponibles.stream()
                    .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                    .findFirst();

            if (comic.isPresent()) {
                usuario.agregarCompra(comic.get());
                comicsDisponibles.remove(comic.get()); // Retira del stock.
                codigosUnicos.remove(comic.get().getCodigo()); // Retira del código.
                System.out.println("\nCompra registrada correctamente.");
            } else {
                System.out.println("\nCómic no encontrado.");
            }

        } catch (Exception e) {
            System.out.println("\nError al agregar compra: " + e.getMessage());
        }
    }

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

    // Guarda los usuarios y sus compras en un archivo de texto.
    public static void guardarUsuarios(String archivo) {
        if (usuarios.isEmpty()) {
            System.out.println("\nNo hay usuarios registrados.");
            return;
        }

        System.out.print("\nIngrese RUT del usuario a guardar (XXXXXXXX-X o XX.XXX.XXX-X): ");
        String rut = sc.nextLine().trim().replaceAll("[.\\s]", "");

        if (!usuarios.containsKey(rut)) {
            System.out.println("\nUsuario no encontrado.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            Usuarios u = usuarios.get(rut);
            bw.write(u.toString() + "\n");
            for (Comics c : u.getCompras()) {
                bw.write("  -> " + c + "\n");
            }
            System.out.println("\nUsuario y sus compras guardados correctamente.");
        } catch (IOException e) {
            System.out.println("\nError al guardar el archivo: " + e.getMessage());
        }
    }

    // Exporta los usuarios a un archivo CSV con nombre y total de compras.
    public static void exportarUsuariosCSV(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("RUT,Nombre,Apellido Paterno,Apellido Materno,Cantidad de Compras");
            for (String rut : usuariosOrdenados) {
                Usuarios u = usuarios.get(rut);
                pw.printf("%s,%s,%s,%s,%d%n",
                        u.getRut(), u.getNombre(), u.getApellidoPaterno(), u.getApellidoMaterno(), u.getCompras().size());
            }
            System.out.println("\nUsuarios exportados correctamente a " + archivo);
        } catch (IOException e) {
            System.out.println("\nError al exportar CSV: " + e.getMessage());
        }
    }

    // Elimina un usuario del sistema por RUT.
    public static void eliminarUsuario() {
        if (usuariosOrdenados.isEmpty()) {
            System.out.println("\nNo hay usuarios registrados.");
            return;
        }
        System.out.println("\n--- Lista de Usuarios ---");
        for (String rut : usuariosOrdenados) {
            System.out.println(rut + " - " + usuarios.get(rut).getNombre());
        }
        System.out.print("\nIngrese el RUT del usuario a eliminar (XXXXXXXX-X o XX.XXX.XXX-X): ");
        String rut = sc.nextLine().trim().replaceAll("\\.|\\s+", "");

        if (usuarios.containsKey(rut)) {
            usuarios.remove(rut);
            usuariosOrdenados.remove(rut);
            System.out.println("\nUsuario eliminado correctamente.");
        } else {
            System.out.println("\nUsuario no encontrado.");
        }
    }

    // Elimina un cómic del sistema por su código.
    public static void eliminarComic() {
        if (comicsDisponibles.isEmpty()) {
            System.out.println("\nNo hay cómics para eliminar.");
            return;
        }
        System.out.println("\n--- Lista de Cómics Disponibles ---");
        for (Comics c : comicsDisponibles) {
            System.out.println(c);
        }
        System.out.print("Ingrese el código del cómic a eliminar: ");
        String codigo = sc.nextLine().trim().toUpperCase();

        Optional<Comics> comicAEliminar = comicsDisponibles.stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo)).findFirst();

        if (comicAEliminar.isPresent()) {
            comicsDisponibles.remove(comicAEliminar.get());
            codigosUnicos.remove(codigo);
            System.out.println("\nCómic eliminado correctamente.");
        } else {
            System.out.println("\nCómic no encontrado.");
        }
    }

}
