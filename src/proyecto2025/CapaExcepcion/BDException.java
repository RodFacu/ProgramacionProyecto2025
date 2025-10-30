/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto2025.CapaExcepcion;

/**
 *
 * @author USUARIO
 */
public class BDException extends Exception {
    // Se declara la clase BDException que extiende de Exception.
    // Esto significa que BDException es una excepción personalizada que puede ser lanzada y capturada en el código,
    // normalmente para manejar errores relacionados con la base de datos.

    public BDException(String message) {
        super(message);
        // Constructor de la excepción que recibe un mensaje como parámetro.
        // Llama al constructor de la clase base Exception para inicializar la excepción con ese mensaje.
    }
    
}
