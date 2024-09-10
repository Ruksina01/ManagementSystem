
package librarysystem;

import java.sql.*;

public class ConnectionDatabase {     
    Connection c;
    Statement s;

    ConnectionDatabase () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql:///library", "root", "1234");
            s = c.createStatement();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
