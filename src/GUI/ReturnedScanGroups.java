package GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DocumentRec.DBconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class ReturnedScanGroups {
	
	public static List<ScanGroup> ListScanGroup;
	
    public static void main() {

        // Create a variable for the connection string. <hostname>:<port>;instanceName=<instance_name
        // String connectionUrl = "jdbc:sqlserver://beta:1433;databaseName=htp_wierzyciel_skok_scaner;integratedSecurity=true;";
        String db = "wierzytelnosci";
        try (
        		Statement stmt = DBconnection.connectionDB(db).createStatement();) {
        	   
        	 
        	           	
        	   String SQL = "SELECT [sk_grupy],[data_skanu],[nazwa],[strony],[dzial]FROM [wierzytelnosci].[dbo].[skany_grupy] WHERE nazwa like '%zajecia%' ORDER BY [sk_grupy] DESC";
        	  // System.out.println(SQL);
               ResultSet rs = stmt.executeQuery(SQL);
               ResultSetMetaData rsmd = rs.getMetaData();
               int columnsNumber = rsmd.getColumnCount();
               String enter = System.getProperty("line.separator");
               ListScanGroup = new ArrayList<ScanGroup>();
               // Iterate through the data in the result set and display it.
               while (rs.next()) {
//                   for (int i = 1; i <= columnsNumber; i++) {
////                	   System.out.print(" | " + rsmd.getColumnName(i)+" | ");
//                	   System.out.print(" | " +rs.getString(i).replaceAll(" ", ""));
//                	   
//                   }
                   ScanGroup ScanG= new ScanGroup(rs.getInt(1),rs.getString(2).replaceAll(" ", ""),rs.getString(3).replaceAll(" ", ""), rs.getInt(4), rs.getString(5).replaceAll(" ", "")); 
                   ListScanGroup.add(ScanG);
                   MainController.data.add(ScanG);
                   System.out.println(enter);
               }
               
 //              returnScanGroupsFromListScanGroup(ListScanGroup);
 //              returnScanGroupsFromDataScanGroup(data);
               
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return;
    }
    
    
    public static void returnScanGroupsFromListScanGroup(List<ScanGroup> r) {
    	int v = 0;
		for (ScanGroup d : r) {

			{

				System.out.println(v + ". " + d.getSkgr() + "  |  " + d.getDataSkanu() + "  |  " + d.getNazwaGrupy()
						+ "  |  " + d.getImagepages() + "  |  " + d.getDzialDocelowy());
			}
			v = v + 1;
		}
    	
    }
    


    
    
}