package logica;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
    
    public String db = "basereserva";
    public String url = "jdbc:mysql://localhost:3306/" + db + "?useUnicode=true&use"
            + "JDBCCompliantTimezoneShift=true&useLegacyDateTimeCode=false&"
            + "serverTimeZone=UTC";
    public String user = "root";
    public String pass = "";
    
    public Conexion() {        
    }
    
    public Connection conectar() {
        Connection link = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            link = DriverManager.getConnection(this.url, this.user, this.pass);
            
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showConfirmDialog(null, e);
            
        }
        
        return link;
    }
    
}
