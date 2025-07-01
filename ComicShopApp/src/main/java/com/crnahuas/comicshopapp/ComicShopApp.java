package com.crnahuas.comicshopapp;

import com.crnahuas.comicshopapp.comics.Comics;
import com.crnahuas.comicshopapp.users.Usuarios;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
                if (procesarLineaComic(linea)) contador++;
            }

            System.out.println("\nSe cargaron " + contador + " cómics correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("\nEl archivo no fue encontrado: " + ruta);
        } catch (IOException e) {
            System.out.println("\nError al leer el archivo: " + e.getMessage());
        }
    }
    
}
