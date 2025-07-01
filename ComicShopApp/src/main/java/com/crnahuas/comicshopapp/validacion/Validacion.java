package com.crnahuas.comicshopapp.validacion;

// Clase para validar entradas de usuario como RUT y nombre.
public class Validacion {

   // Valida que el RUT esté en formato XXXXXXXX-X o XXXXXXXX-K
    public static boolean validarRut(String rut) {
        return rut.matches("\\d{7,8}-[\\dkK]");
    }

    // Valida que el nombre solo contenga letras y espacios
    public static boolean validarNombre(String nombre) {
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
}
