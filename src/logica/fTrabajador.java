package logica;

import datos.vCliente;
import datos.vProducto;
import datos.vTrabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class fTrabajador {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        
        String titulos[] = {"ID", "Nombre", "Apaterno", "Amaterno", "Doc", "Número Documento", "Dirección", "Teléfono", "email", "Sueldo", "Acceso", "Login", "Clave", "Estado"};
        String registro[] = new String[14];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT p.idpersona, p.nombre, p.apaterno, p.amaterno, p.tipo_documento, p.num_documento," +
                "p.direccion, p.telefono, p.email, t.sueldo, t.acceso, t.login, t.password, t.estado FROM persona p INNER JOIN trabajador t " +
                "ON p.idpersona = t.idpersona WHERE num_documento LIKE '%"+
                buscar + "%' ORDER BY idpersona DESC";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while (rs.next()) {
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apaterno");
                registro[3] = rs.getString("amaterno");
                registro[4] = rs.getString("tipo_documento");
                registro[5] = rs.getString("num_documento");
                registro[6] = rs.getString("direccion");
                registro[7] = rs.getString("telefono");
                registro[8] = rs.getString("email");
                registro[9] = rs.getString("sueldo");
                registro[10] = rs.getString("acceso");
                registro[11] = rs.getString("login");
                registro[12] = rs.getString("password");
                registro[13] = rs.getString("estado");
                                
                totalRegistros += 1;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
    }

    public boolean insertar(vTrabajador dts) {
        sSQL = "INSERT INTO persona(nombre, apaterno, amaterno, tipo_documento, num_documento, direccion, telefono, email)" 
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        sSQL2 = "INSERT INTO trabajador(idpersona, sueldo, acceso, login, password, estado)" 
                    + " VALUES((SELECT idpersona FROM persona ORDER BY idpersona desc limit 1), ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);
            
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getaPaterno());
            pst.setString(3, dts.getaMaterno());
            pst.setString(4, dts.getTipoDocumento());
            pst.setString(5, dts.getNumDocumento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            
            pst2.setDouble(1, dts.getSueldo());    
            pst2.setString(2, dts.getAcceso()); 
            pst2.setString(3, dts.getLogin()); 
            pst2.setString(4, dts.getPassword()); 
            pst2.setString(5, dts.getEstado());
            
            int n = pst.executeUpdate();
            
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    public boolean editar(vTrabajador dts) {
        sSQL = "UPDATE persona SET nombre = ?, apaterno = ?, amaterno = ?, tipo_documento = ?, num_documento = ?," +
                " direccion = ?, telefono = ?, email = ? WHERE idpersona = ?";
        sSQL2 = "UPDATE trabajador SET sueldo = ?, acceso = ?, login = ?, password = ?, estado = ?" +
                " WHERE idpersona = ?";
        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);
            
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getaPaterno());
            pst.setString(3, dts.getaMaterno());
            pst.setString(4, dts.getTipoDocumento());
            pst.setString(5, dts.getNumDocumento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getIdPersona());
            
            pst2.setDouble(1, dts.getSueldo()); 
            pst2.setString(2, dts.getAcceso());
            pst2.setString(3, dts.getLogin());
            pst2.setString(4, dts.getPassword());
            pst2.setString(5, dts.getEstado());
            pst2.setInt(6, dts.getIdPersona());
            
            int n = pst.executeUpdate();
            
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    public boolean eliminar(vTrabajador dts) {
        sSQL = "DELETE FROM trabajador WHERE idpersona = ?";
        sSQL2 = "DELETE FROM persona WHERE idpersona = ?";
        
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);
            
            pst.setInt(1, dts.getIdPersona());
            pst2.setInt(1, dts.getIdPersona());
            
            int n = pst.executeUpdate();
            
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null,e);
            return false;
        }
    }
    
    public DefaultTableModel login(String login, String password) {
        DefaultTableModel modelo;
        
        String titulos[] = {"ID", "Nombre", "Apaterno", "Amaterno", "Acceso", "Login", "Clave", "Estado"};
        String registro[] = new String[8];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT p.idpersona, p.nombre, p.apaterno, p.amaterno," +
                " t.acceso, t.login, t.password, t.estado FROM persona p INNER JOIN trabajador t " +
                "ON p.idpersona = t.idpersona WHERE t.login = '"+
                login + "' AND t.password = '" + password + "' AND t.estado = 'A'";
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            
            while (rs.next()) {
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apaterno");
                registro[3] = rs.getString("amaterno");
                registro[4] = rs.getString("acceso");
                registro[5] = rs.getString("login");
                registro[6] = rs.getString("password");
                registro[7] = rs.getString("estado");
                                
                totalRegistros += 1;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
    }
    
    
}
