/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2025.CapaLogica;


import Proyecto2025.CapaExcepcion.BDException;
import Proyecto2025.CapaPersistencia.Conexion;
import Proyecto2025.CapaPersistencia.PersistenciaApp;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_ADMIN;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_GRUPO;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_INASISTENCIA;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_INICIAR;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_LICENCIA;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_PROFESOR;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_REGISTRA;
import static Proyecto2025.CapaPersistencia.PersistenciaApp.SQL_REGISTRO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author USUARIO
 */
public class FachadaLogica {
    // Clase que funciona como "fachada" para la lógica de la aplicación.
    // Encapsula y centraliza el acceso a la capa de persistencia y operaciones sobre la base de datos.
    // Permite que la interfaz gráfica o cualquier otra capa pueda usar estos métodos sin conocer los detalles de SQL.

    public void registrarUsuarios(Login log) throws Exception {
        PersistenciaApp papp = new PersistenciaApp();
        // Método para registrar usuarios. Por ahora solo crea una instancia de PersistenciaApp,
        // se puede usar para delegar la inserción del usuario en la BD.
    }

    // Obtiene los datos para la tabla de administración (incluye materias)
    public static ResultSet obtenerDatosAdministracion() throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        // Obtiene la conexión a la base de datos usando la clase Conexion.

        String sql = "SELECT p.nombre, p.apellido, p.cedula, p.telefono, p.materias, " +
                     "g.nombre_grupo, g.id_grupo, l.fecha_inicio, l.fecha_cierre, " +
                     "l.justificacion, l.id_licencia " +
                     "FROM PROFESOR p " +
                     "JOIN GRUPO g ON p.id_grupo = g.id_grupo " +
                     "LEFT JOIN LICENCIA l ON p.cedula = l.cedula " +
                     "ORDER BY l.fecha_inicio DESC";
        // Consulta SQL que combina información de profesor, grupo y licencias.
        // LEFT JOIN permite incluir profesores sin licencia asignada.

        System.out.println("SQL obtenerDatosAdministracion: " + sql);
        // Depuración: imprime la sentencia SQL en consola.

        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
        // Ejecuta la consulta y devuelve el ResultSet con los datos.
    }

    // Obtiene los datos para la tabla de información general
    public static ResultSet obtenerDatosInformacion() throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();

        String sql = "SELECT p.nombre, p.apellido, p.cedula, p.materias, " +
                     "g.nombre_grupo, g.id_grupo, l.fecha_inicio, l.fecha_cierre " +
                     "FROM PROFESOR p " +
                     "JOIN GRUPO g ON p.id_grupo = g.id_grupo " +
                     "LEFT JOIN LICENCIA l ON p.cedula = l.cedula " +
                     "ORDER BY l.fecha_inicio DESC";
        // Similar al anterior pero con menos campos, para mostrar información general.

        System.out.println("SQL obtenerDatosInformacion: " + sql);

        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    // Registrar usuario en la BD
    public static void registrarUsuario(Login log) throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_REGISTRO);
        ps.setString(1, log.getUsuario());
        ps.setString(2, log.getContrasena());
        ps.executeUpdate();
        ps.close();
        con.close();
        // Inserta un nuevo usuario en la tabla administrador usando los valores de Login.
    }

    // Iniciar sesión: devuelve true si usuario y contraseña coinciden
    public static boolean iniciarSesion(String usuario, String contrasena) throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_INICIAR);
        ps.setString(1, usuario);
        ps.setString(2, contrasena);
        ResultSet rs = ps.executeQuery();
        boolean valido = rs.next();
        // Verifica si existe un registro que coincida con usuario y contraseña.
        rs.close();
        ps.close();
        con.close();
        return valido;
    }

    // Guardar administrador
    public static void guardarAdministrador(String usuario, String contrasena) throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_ADMIN);
        ps.setString(1, usuario);
        ps.setString(2, contrasena);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // Guardar grupo
    public static void guardarGrupo(int id_grupo, String nombreGrupo) throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_GRUPO);
        ps.setInt(1, id_grupo);
        ps.setString(2, nombreGrupo);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // Guardar profesor
    public static void guardarProfesor(int cedula, String nombre, String apellido, int telefono, String turno, int id_grupo, String materias) throws SQLException, BDException, CapaException.BDException {
      
    Connection con = null;
    PreparedStatement psVerificar = null;
    PreparedStatement psInsertar = null;
    ResultSet rs = null;

    try {
        con = Conexion.getConnection();

        String sqlVerificar = "SELECT COUNT(*) FROM profesor WHERE cedula = ?";
        psVerificar = con.prepareStatement(sqlVerificar);
        psVerificar.setInt(1, cedula);
        rs = psVerificar.executeQuery();
        rs.next();
        int existe = rs.getInt(1);

        if (existe > 0) {
            throw new SQLException("Ya existe un profesor con esa cédula.");
        }

        psInsertar = con.prepareStatement(SQL_PROFESOR);
        psInsertar.setInt(1, cedula);
        psInsertar.setString(2, nombre);
        psInsertar.setString(3, apellido);
        psInsertar.setInt(4, telefono);
        psInsertar.setString(5, turno);
        psInsertar.setInt(6, id_grupo);
        psInsertar.setString(7, materias);
        psInsertar.executeUpdate();

    } finally {
        if (rs != null) rs.close();
        if (psVerificar != null) psVerificar.close();
        if (psInsertar != null) psInsertar.close();
        if (con != null) con.close();
    }
}

    // Guardar licencia y devolver el ID generado
    public static int guardarLicencia(String fecha_inicio, String fecha_fin, String justificacion, int cedula) 
        throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_LICENCIA, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, fecha_inicio);
        ps.setString(2, fecha_fin);
        ps.setString(3, justificacion);
        ps.setInt(4, cedula);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        int idGenerado = 0;
        if (rs.next()) {
            idGenerado = rs.getInt(1);
        }
        rs.close();
        ps.close();
        con.close();
        return idGenerado;
    }

    // Guardar registro de acción administrativa
    public static void guardarRegistra(String usuario, int cedula, int id_Licencia, String fechaRegistro) throws SQLException, BDException, CapaException.BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_REGISTRA);
        ps.setString(1, usuario);
        ps.setInt(2, cedula);
        ps.setInt(3, id_Licencia);
        ps.setString(4, fechaRegistro);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // Guardar inasistencia
    public static void guardarInasistencia(int cedula, int idLicencia, String fechaInicio, String fechaFin, String justificacion) throws SQLException, CapaException.BDException, BDException {
        Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(SQL_INASISTENCIA);
        ps.setInt(1, cedula);
        ps.setInt(2, idLicencia);
        ps.setString(3, fechaInicio);
        ps.setString(4, fechaFin);
        ps.setString(5, justificacion);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    // Eliminar profesor completo
    public void eliminarProfesorCompleto(int cedula) throws SQLException, BDException, CapaException.BDException {
        PersistenciaApp persist = new PersistenciaApp();
        persist.eliminarRegistroCompleto(cedula); 
        // Llama a un método de PersistenciaApp para eliminar todos los registros relacionados a un profesor.
    }
   
// Validar si la cédula ya existe
public static boolean existeCedula(int cedula) throws SQLException, BDException, CapaException.BDException {
    String sql = "SELECT COUNT(*) FROM PROFESOR WHERE cedula = ?";
    Connection con = Conexion.getConnection();
try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cedula);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}


}