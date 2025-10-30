/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto2025.CapaPersistencia;
import Proyecto2025.CapaExcepcion.BDException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author USUARIO
 */
//Es la encargada de inicializar la conexion con la BD
 public class Conexion {
    // Clase encargada de manejar la conexión a la base de datos.
    // Contiene métodos estáticos para obtener una conexión sin necesidad de instanciar la clase.

    public static Connection getConnection() throws BDException, CapaException.BDException {
        // Método estático que devuelve un objeto Connection.
        // Lanza excepciones personalizadas BDException o CapaException.BDException en caso de error.
        Connection con = null; // Inicializa la variable de conexión como null.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            // Carga el driver de MySQL. Esto es necesario para que Java pueda conectarse a la base de datos.
            
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruba?serverTimezone=UTC", "root","");
            // Esta línea estaba comentada; probablemente era para pruebas con otra base de datos.
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/inasistencia?serverTimezone=UTC", "root","");
            // Establece la conexión real con la base de datos "inasistencia" usando el usuario root y sin contraseña.
            // El parámetro serverTimezone=UTC asegura que no haya problemas de zona horaria.
        } catch (ClassNotFoundException e) {
            // Se lanza si no se encuentra el driver de MySQL en el classpath.
            throw new CapaException.BDException("No se encontró el driver de MySQL: " + e.getMessage());
        } catch (SQLException sqle) {
            // Se lanza si ocurre algún error al intentar conectarse a la base de datos.
            throw new CapaException.BDException("Error al conectar a la BD: " + sqle.getMessage());
        }
        return con; 
        // Devuelve la conexión establecida o null si ocurrió algún error antes de lanzarse la excepción.
    }
}

