/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proyecto2025.CapaPersistencia;

import Proyecto2025.CapaExcepcion.BDException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import proyecto2025.CapaLogica.Login;

/**
 *
 * @author USUARIO
 */
public class PersistenciaApp {
    // Clase encargada de manejar la persistencia de datos, es decir, la comunicación con la base de datos
    // a través de sentencias SQL predefinidas.

    private int controlError; 
    // Variable privada para controlar o almacenar códigos de error dentro de la clase.

    // Declaración de sentencias SQL como constantes estáticas. 
    // Esto permite reutilizarlas en distintos métodos sin reescribirlas.
    public static final String SQL_REGISTRO = "INSERT INTO inasistencia.administrador(usuario, contrasena) VALUES (?, ?)";
    // Inserta un nuevo registro en la tabla administrador con usuario y contraseña.

    public static final String SQL_INICIAR = "SELECT * FROM inasistencia.administrador WHERE usuario=? AND contrasena=?";
    // Consulta para verificar credenciales al iniciar sesión en la aplicación.

    public static final String SQL_ADMIN = "INSERT INTO ADMINISTRADOR (usuario, contrasena) VALUES (?, ?)";
    // Inserta un nuevo administrador en la tabla ADMINISTRADOR (similar a SQL_REGISTRO, probablemente redundante).

    public static final String SQL_GRUPO = "INSERT INTO GRUPO (id_grupo, nombre_grupo) VALUES (?,?)";
    // Inserta un nuevo grupo con su ID y nombre en la tabla GRUPO.

    public static final String SQL_PROFESOR = "INSERT INTO PROFESOR (cedula, nombre, apellido, telefono, turno, id_grupo,materias) VALUES (?, ?, ?, ?, ?, ?, ?)";
    // Inserta un nuevo profesor con todos sus datos, incluyendo la relación con un grupo y materias.

    public static final String SQL_LICENCIA = "INSERT INTO LICENCIA (fecha_inicio, fecha_cierre, justificacion, cedula) VALUES (?, ?, ?, ?)";
    // Inserta una licencia para un profesor, indicando fechas, justificación y cedula del profesor.

    public static final String SQL_REGISTRA = "INSERT INTO REGISTRA (id_admin, cedula, id_licencia, fecha_registro) VALUES (?, ?, ?, ?)";
    // Inserta un registro de acción administrativa que vincula un administrador con una licencia y un profesor.

    public static final String SQL_INASISTENCIA = "INSERT INTO INASISTENCIA (cedula, id_licencia, fecha_inicio, fecha_cierre, justificacion) VALUES (?, ?, ?, ?, ?)";
    // Inserta una inasistencia de un profesor, con sus fechas, justificación y relación con licencia.

    public Conexion cone = new Conexion();
    // Instancia de la clase Conexion para usarla en los métodos de esta clase y obtener conexiones a la BD.

      public PreparedStatement ps;//prepara los datos
    public ResultSet rs;//devuelve los datos
//PreparedStatement es una interfaz en Java que hereda de la interfaz Statement. 
//Se utiliza para representar una sentencia SQL precompilada. 
//A diferencia de un objeto Statement normal, un PreparedStatement se precompila 
//y se almacena en caché en el servidor de la base de datos,lo que puede mejorar significativamente 
//el rendimiento en situaciones donde se ejecutan consultas similares varias veces.  
// Un PreparedStatement se compila una vez y puede ser reutilizado con diferentes 
//     conjuntos de parámetros. Esto puede ser más eficiente 
    //que crear y ejecutar nuevas declaraciones SQL cada vez que se necesita realizar 
    //una consulta o una actualización.  
    /**

 * @param cedula la cédula del profesor cuyos datos se eliminarán
 * @throws SQLException si ocurre un error en la BD (se hace rollback)
 */
public void eliminarRegistroCompleto(int cedula) throws SQLException, BDException, CapaException.BDException {
  
    PreparedStatement psInasistencia = null;
    PreparedStatement psRegistra = null;
    PreparedStatement psLicencia = null;
    PreparedStatement psProfesor = null;
    PreparedStatement psGrupo = null;

   Connection cone = null;
try {
    // 1) Obtener la conexión desde tu clase Conexion
    cone = Conexion.getConnection(); //  AQUÍ se inicializa cone
    
    // 2) Desactivar auto-commit para manejar transacción
    cone.setAutoCommit(false);
 //Desactiva la comfirmacio desde la BD, Y permite hacerla desde la BD
        cone.setAutoCommit(false);

     
        // 3) Eliminar filas dependientes (primero las que referencian licencias/profesor)
     

        // 3.1 Eliminar de INASISTENCIA todas las filas que tengan esa cédula.
        //     INASISTENCIA probablemente tiene FK a PROFESOR (cedula) y/o LICENCIA.
        psInasistencia = cone.prepareStatement(
            "DELETE FROM INASISTENCIA WHERE cedula = ?"
        );
        psInasistencia.setInt(1, cedula);
        psInasistencia.executeUpdate();

        // 3.2 Eliminar de REGISTRA (registro administrativo) por cédula.
        //     REGISTRA contiene id_licencia y cedula; borramos filas con esta cédula.
        psRegistra = cone.prepareStatement(
            "DELETE FROM REGISTRA WHERE cedula = ?"
        );
        psRegistra.setInt(1, cedula);
        psRegistra.executeUpdate();

        // 3.3 Eliminar de LICENCIA todas las licencias asociadas a la cédula.
        //     Hacemos esto después de borrar INASISTENCIA y REGISTRA para evitar violaciones de FK.
        psLicencia = cone.prepareStatement(
            "DELETE FROM LICENCIA WHERE cedula = ?"
        );
        psLicencia.setInt(1, cedula);
        psLicencia.executeUpdate();

        
        // 4) Eliminar el PROFESOR
       
        psProfesor = cone.prepareStatement(
            "DELETE FROM PROFESOR WHERE cedula = ?"
        );
        psProfesor.setInt(1, cedula);
        psProfesor.executeUpdate();

      
        // 5) Limpieza opcional: eliminar GRUPOs que hayan quedado sin profesores.
        //    Esto borra grupos cuyo id_grupo ya no existe en la tabla PROFESOR.
        //    Si no querés eliminar grupos automáticamente, podés quitar este paso.
      
        psGrupo = cone.prepareStatement(
            "DELETE FROM GRUPO WHERE id_grupo NOT IN (SELECT DISTINCT id_grupo FROM PROFESOR WHERE id_grupo IS NOT NULL)"
        );
        psGrupo.executeUpdate();

        // 6) Si llegamos acá sin excepciones, confirmar la transacción.
        cone.commit();

    } catch (SQLException e) {
        // Si falla cualquier cosa, deshacer todos los cambios hechos en esta transacción.
        if (cone != null) {
            try {
                cone.rollback();
            } catch (SQLException rollbackEx) {
                // Si el rollback también falla, lo registramos (no lanzamos otro para no ocultar la excepción original).
                rollbackEx.printStackTrace();
            }
        }
        // Re-lanzamos la excepción con mensaje claro para que la capa superior la maneje.
throw new SQLException(
    "Error al eliminar datos del profesor (cedula=" + cedula + "): " + e.getMessage(), e
);
// Si ocurre un error durante la eliminación de datos de un profesor, se captura la excepción original 'e'
// y se lanza una nueva SQLException con un mensaje más claro y específico, incluyendo la cédula del profesor.
// Esto permite que la capa que llama al método reciba información detallada del error.

} finally {
    // 7) Cierre ordenado de recursos. 
    // El bloque finally se ejecuta siempre, haya ocurrido excepción o no, garantizando que se liberen recursos.
    
    if (psInasistencia != null) {
        try { psInasistencia.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra el PreparedStatement para la tabla INASISTENCIA. Se usa try/catch para evitar que un error al cerrar 
    // detenga el cierre de los demás recursos.

    if (psRegistra != null) {
        try { psRegistra.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra el PreparedStatement para la tabla REGISTRA.

    if (psLicencia != null) {
        try { psLicencia.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra el PreparedStatement para la tabla LICENCIA.

    if (psProfesor != null) {
        try { psProfesor.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra el PreparedStatement para la tabla PROFESOR.

    if (psGrupo != null) {
        try { psGrupo.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra el PreparedStatement para la tabla GRUPO.

    if (cone != null) {
        try { 
            cone.setAutoCommit(true); // Devuelve el auto-commit a su estado normal antes de cerrar.
            cone.close(); 
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
    // Cierra la conexión a la base de datos. Se asegura de que no queden transacciones abiertas
    // y captura cualquier excepción al cerrar para que no interrumpa la limpieza de recursos.
}
}
}