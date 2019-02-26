package txtreader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    
    public static String dbUrl="jdbc:sqlserver://10.0.201.31;databaseName=CDRSERVICE";
    public static String dbUserName="sa";
    public static String dbPassword="Aa123456!";
    
//    public static String dbUrl="jdbc:postgresql://localhost:5432/CDRTEST";
//    public static String dbUserName="postgres";
//    public static String dbPassword="Aa123456!";
    

    public DbConnection() throws ClassNotFoundException, SQLException {
        
        openDbConnection();
    }

    public static Connection openDbConnection() throws ClassNotFoundException, SQLException {
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
