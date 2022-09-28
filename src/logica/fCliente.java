package logica;

import datos.vCliente;
import datos.vProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class fCliente {
    
    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalRegistros;
    
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        
        String titulos[] = {"ID", "Nombre", "Apaterno", "Amaterno", "Doc", "Número Documento", "Dirección", "Teléfono", "email", "Código"};
        String registro[] = new String[10];
        totalRegistros = 0;
        modelo = new DefaultTableModel(null, titulos);
        sSQL = "SELECT p.idpersona, p.nombre, p.apaterno, p.amaterno, p.tipo_documento, p.num_documento," +
                "p.direccion, p.telefono, p.email, c.codigo_cliente FROM persona p INNER JOIN cliente c " +
                "ON p.idpersona = c.idpersona WHERE num_documento LIKE '%"+
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
                registro[9] = rs.getString("codigo_cliente");
                
                totalRegistros += 1;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
    }

    public boolean insertar(vCliente dts) {
        sSQL = "INSERT INTO persona(nombre, apaterno, amaterno, tipo_documento, num_documento, direccion, telefono, email)" 
                    + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        sSQL2 = "INSERT INTO cliente(idpersona, codigo_cliente)" 
                    + " VALUES((SELECT idpersona FROM persona ORDER BY idpersona desc limit 1), ?)";
        
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
            
            pst2.setString(1, dts.getCodigoCliente());           
            
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
    
    public boolean editar(vCliente dts) {
        sSQL = "UPDATE persona SET nombre = ?, apaterno = ?, amaterno = ?, tipo_documento = ?, num_documento = ?," +
                " direccion = ?, telefono = ?, email = ? WHERE idpersona = ?";
        sSQL2 = "UPDATE cliente SET codigo_cliente = ?" +
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
            
            pst2.setString(1, dts.getCodigoCliente());  
            pst2.setInt(2, dts.getIdPersona());
            
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
    
    public boolean eliminar(vCliente dts) {
        sSQL = "DELETE FROM cliente WHERE idpersona = ?";
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
    
    
}
