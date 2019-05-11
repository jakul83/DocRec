


package DocumentRec;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;


public class DBconnection {
    public static Connection connectionDB(String db) throws SQLException {
    	
        // Create a variable for the connection string. <hostname>:<port>;instanceName=<instance_name
        String connectionUrl = "jdbc:sqlserver://ASUS\\SQLEXPRESS:1433;databaseName="+db+";integratedSecurity=true;applicationName=DocRecognition";

        Connection con = DriverManager.getConnection(connectionUrl); 
    //    System.out.println("conncected :" + con);

        
        
        return con;
       
        }
    
      
    }
