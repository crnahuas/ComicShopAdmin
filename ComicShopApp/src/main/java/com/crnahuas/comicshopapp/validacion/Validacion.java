package com.crnahuas.comicshopapp.validacion;

public class Validacion {

   // Valida que el RUT esté en formato XX.XXX.XXX-X
    public static boolean validarRut(String rut) {
        return rut.matches("\\d{7,8}-[\\dkK]");
    }

    // Valida que el nombre solo contenga letras y espacios
    public static boolean validarNombre(String nombre) {
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
}
