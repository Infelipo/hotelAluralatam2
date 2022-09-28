package logica;

import datos.vHabitacion;
import datos.vProducto;
import datos.vReserva;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class fReserva {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        
        String titulos[] = {"ID", "IdHabitacion", "Número", "IdCliente", "idTrabajador", "Trabajador", "Tipo Reserva", "Fecha Reserva", "Fecha Ingreso", "Fecha Salida", "Costo alojamiento", "Estado"};
        String registro[] = new String[13];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT r.idreserva, r.idhabitacion, h.numero, r.idcliente, "+
                "(SELECT nombre FROM persona WHERE idpersona = r.idcliente) AS clienteN, "+
                "(SELECT apaterno FROM persona WHERE idpersona = r.idcliente) AS clienteAP, "+
                " r.idtrabajador, (SELECT nombre FROM persona WHERE idpersona = r.idtrabajador) AS trabajadorN, "+
                "(SELECT apaterno FROM persona WHERE idpersona = r.idtrabajador) AS trabajadorAP, "+
                "r.tipo_reserva, r.fecha_reserva, r.fecha_ingresa, r.fecha_salida, "+
                " r.costo_alojamiento, r.estado FROM reserva r INNER JOIN habitacion h ON r.idhabitacion = h.idhabitacion WHERE r.fecha_reserva LIKE '%" + buscar + "%' ORDER BY idreserva DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while (rs.next()) {
                registro[0] = rs.getString("idreserva");
                registro[1] = rs.getString("idhabitacion");
                registro[2] = rs.getString("numero");
                registro[3] = rs.getString("idcliente");
                registro[4] = rs.getString("clienteN") + " " + rs.getString("clienteAP");
                registro[5] = rs.getString("idtrabajador");
                registro[6] = rs.getString("trabajadorN") + " " + rs.getString("trabajadorAP");
                registro[7] = rs.getString("tipo_reserva");
                registro[8] = rs.getString("fecha_reserva");
                registro[9] = rs.getString("fecha_ingresa");
                registro[10] = rs.getString("fecha_salida");
                registro[11] = rs.getString("costo_alojamiento");
                registro[12] = rs.getString("estado");
                
                totalRegistros += 1;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
    }

    public boolean insertar(vReserva dts) {
        sSQL = "INSERT INTO reserva(idhabitacion, idcliente, idtrabajador, tipo_reserva, fecha_reserva, fecha_ingresa, fecha_salida, costo_alojamiento, estado)" 
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdHabitacion());
            pst.setInt(2, dts.getIdCliente());
            pst.setInt(3, dts.getIdTrabajador());
            pst.setString(4, dts.getTipoReserva());
            pst.setDate(5, (Date) dts.getFechaReserva());
            pst.setDate(6, (Date) dts.getFechaIngreso());
            pst.setDate(7, (Date) dts.getFechaSalida());
            pst.setDouble(8, dts.getCostoAlojamiento());
            pst.setString(9, dts.getEstado());
         
            
            int n = pst.executeUpdate();
            if (n != 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    public boolean editar(vReserva dts) {
        sSQL = "UPDATE reserva SET idhabitacion = ?, idcliente = ?, idtrabajador = ?, tipo_reserva = ?, fecha_reserva = ?, fecha_ingresa = ?, fecha_salida = ?, costo_alojamiento = ?, estado = ?" +
                " WHERE idreserva = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdHabitacion());
            pst.setInt(2, dts.getIdCliente());
            pst.setInt(3, dts.getIdTrabajador());
            pst.setString(4, dts.getTipoReserva());
            pst.setDate(5, (Date) dts.getFechaReserva());
            pst.setDate(6, (Date) dts.getFechaIngreso());
            pst.setDate(7, (Date) dts.getFechaSalida());
            pst.setDouble(8, dts.getCostoAlojamiento());
            pst.setString(9, dts.getEstado());
            pst.setInt(10, dts.getIdReserva());
            
            int n = pst.executeUpdate();
            if (n != 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    public boolean eliminar(vReserva dts) {
        sSQL = "DELETE FROM reserva WHERE idreserva = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdReserva());
            
            int n = pst.executeUpdate();
            if (n != 0) {
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    
}
