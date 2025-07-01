package com.crnahuas.comicshopapp;

import com.crnahuas.comicshopapp.comics.Comics;
import com.crnahuas.comicshopapp.users.Usuarios;
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
}
