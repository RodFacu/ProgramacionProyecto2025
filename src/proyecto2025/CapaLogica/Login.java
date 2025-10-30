/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2025.CapaLogica;

/**
 *
 * @author USUARIO
 */
public class Login {
    // Clase que representa la información de login de un usuario.
    // Contiene campos para el usuario y la contraseña, además de un campo estático para almacenar el usuario que ha iniciado sesión actualmente.

    public String usuario;
    // Campo público que almacena el nombre de usuario. Puede ser accedido directamente o mediante getters/setters.

    public String contrasena;
    // Campo público que almacena la contraseña asociada al usuario.

    private static String usuarioActual; 
    // Campo estático privado que almacena el usuario que ha iniciado sesión actualmente.
    // Es estático para que mantenga el valor compartido entre todas las instancias de Login.
    
    public static String getUsuarioActual() {
        return usuarioActual;
    }
    // Getter estático para obtener el usuario actual que ha iniciado sesión.

    public static void setUsuarioActual(String usuarioActual) {
        Login.usuarioActual = usuarioActual;
    }
    // Setter estático para actualizar el usuario actual que ha iniciado sesión.

    public String getUsuario() {
        return usuario;
    }
    // Getter para el campo 'usuario' de la instancia.

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    // Setter para el campo 'usuario' de la instancia.

    public String getContrasena() {
        return contrasena;
    }
    // Getter para el campo 'contrasena' de la instancia.

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    // Setter para el campo 'contrasena' de la instancia.
}
