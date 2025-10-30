/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2025.InterfazGrafica;

import Proyecto2025.CapaExcepcion.BDException;
import Proyecto2025.CapaPersistencia.PersistenciaApp;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import proyecto2025.CapaLogica.FachadaLogica;

/**
 *
 * @author USUARIO
 */
public class PantallaInformacionAdministracion extends javax.swing.JFrame {
    // Clase que representa la ventana de administración con información detallada
    // sobre profesores, grupos y licencias.

    private static PantallaInformacionAdministracion instancia;
    // Instancia estática para implementar el patrón singleton.
    // Esto garantiza que solo exista una ventana de administración abierta a la vez.

    public static PantallaInformacionAdministracion getInstancia() {
        // Método público para obtener la instancia única de la ventana.
        if (instancia == null) {
            instancia = new PantallaInformacionAdministracion("defecto");
            // Si no existe la instancia, se crea con un usuario por defecto.
        }
        return instancia;
    }


        
    private int controlError;
// Variable interna para controlar el estado o paso en que se encuentra la carga de datos.
// Útil para depuración si ocurre algún error.

private void cargarDatosDesdeBD() {
    controlError = -1;
    try {
        // 1. Solicita los datos a la capa de lógica/persistencia
        ResultSet rs = FachadaLogica.obtenerDatosAdministracion();
        // Obtiene todos los registros de profesores, grupos y licencias
        // usando un JOIN para traer toda la información necesaria.

        // 2. Obtener el modelo de la tabla y limpiar filas existentes
        DefaultTableModel modelo = (DefaultTableModel) tablaAdm.getModel();
        modelo.setRowCount(0); // Limpia filas anteriores antes de cargar nuevos datos

        // 3. Recorrer los resultados y agregarlos al modelo de la tabla
        while (rs.next()) {
            Object[] fila = {
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getInt("cedula"),
                "Desconocida", // Valor temporal para materias, se puede actualizar luego
                rs.getString("nombre_grupo"),
                rs.getString("fecha_inicio"),
                rs.getString("fecha_cierre"),
                rs.getString("justificacion"),
                rs.getInt("id_grupo"),
                rs.getInt("id_licencia"),
                rs.getString("telefono")
            };
            modelo.addRow(fila);
            // Cada fila del ResultSet se agrega a la tabla de administración
        }

        rs.close(); // Cerramos el ResultSet para liberar recursos
    } catch (Exception e) {
        // Manejo de errores general mostrando un mensaje al usuario
        JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
    }
}


    /**
     * Creates new form PantallaInformacionAdministracion
     */
    public PantallaInformacionAdministracion(String usuario) {
    controlError = 1;
    // Inicializa control de errores para depuración.

    instancia = this;
    // Asigna esta instancia al atributo estático, implementando el patrón singleton.

    initComponents();
    // Inicializa los componentes gráficos generados por NetBeans.

    cargarDatosDesdeBD();
    // Carga los datos desde la base de datos y los muestra en la tabla.

    setDefaultCloseOperation(0);
    // Evita que cerrar la ventana termine la aplicación.

    setLocationRelativeTo(null);
    // Centra la ventana en la pantalla.

    setResizable(false);
    // Bloquea cambios de tamaño de la ventana.

    tablaAdm.setDefaultEditor(Object.class, null);
    // Ninguna celda de la tabla es editable por el usuario.

    tablaAdm.getTableHeader().setReorderingAllowed(false);
    // Evita que las columnas se puedan reordenar manualmente.

    tablaAdm.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    // Ajusta automáticamente el ancho de las columnas al contenido.

    tablaAdm.getTableHeader().setResizingAllowed(false);
    // Impide que el usuario cambie manualmente el tamaño de las columnas.
}

public void agregarFilaATabla(String nombre, String apellido, int cedula, String materia, int id_grupo, String nombre_grupo, String fecha_inicio, String fecha_fin, String justificacion, int id_Licencia, int telefono) {
    controlError = 2;
    // Control de errores para identificar el paso en la depuración.

    DefaultTableModel modelo = (DefaultTableModel) tablaAdm.getModel();
    modelo.addRow(new Object[]{
        nombre,
        apellido,
        cedula,
        materia,
        nombre_grupo,
        fecha_inicio,
        fecha_fin,
        justificacion,
        id_grupo,
        id_Licencia,
        telefono
    });
    // Agrega una nueva fila con los datos del profesor, grupo y licencia.
    // Este método se utiliza cuando se registra un nuevo profesor para reflejar
    // inmediatamente la información en la tabla de administración.
}




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAdm = new javax.swing.JTable();
        btneliminar = new javax.swing.JButton();
        volver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaAdm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Cedula", "Materia", "Grupo", "Desde", "Hasta", "Motivo", "id_grupo", "id_licencia", "telefono"
            }
        ));
        jScrollPane1.setViewportView(tablaAdm);

        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        volver.setText("volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 917, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(volver)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(volver))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
     //Vuelve a la pantalla anterior
        controlError = 3;
        dispose();
    PantallaPrincipal principal = new PantallaPrincipal();
    principal.setVisible(true);
    
    
// TODO add your handling code here:
    }//GEN-LAST:event_volverActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        // TODO add your handling code here:
       controlError = 4;
// Actualiza el control de errores indicando que estamos en el paso de eliminación.

int fila = tablaAdm.getSelectedRow();
if (fila < 0) {
    JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
    return;
}
// Verifica que el usuario haya seleccionado una fila de la tabla.
// Si no hay fila seleccionada, muestra un mensaje y termina la operación.

int cedula = Integer.parseInt(tablaAdm.getValueAt(fila, 2).toString());
// Obtiene la cédula del profesor de la fila seleccionada.
// Se asume que la columna 2 contiene la cédula, se convierte a entero.

int confirm = JOptionPane.showConfirmDialog(this,
    "¿Seguro que desea eliminar completamente los datos de la cédula " + cedula + "?",
    "Confirmar eliminación",
    JOptionPane.YES_NO_OPTION);
// Pregunta al usuario si realmente desea eliminar el registro.

if (confirm == JOptionPane.YES_OPTION) {
    try {
        FachadaLogica fachada = new FachadaLogica();
        fachada.eliminarProfesorCompleto(cedula);
        // Llama a la fachada lógica para eliminar todos los datos del profesor
        // incluyendo inasistencias, licencias y registros asociados.

        JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
        cargarDatosDesdeBD(); // refresca la tabla para reflejar la eliminación
    } catch (Exception e) {
        // Manejo de errores mostrando mensaje claro
        JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
    }
}



    }//GEN-LAST:event_btneliminarActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btneliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaAdm;
    private javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables
}
