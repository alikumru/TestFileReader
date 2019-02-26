/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txtreader;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Fujitsu
 */
public class TxtReader {
public static Connection conn = null;
static File file ;
static ArrayList<String> confNames;
static ArrayList<String> confLengths;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        
        conn=DbConnection.openDbConnection();
        file = new File("C:\\Users\\Fujitsu\\Desktop\\data.txt");
        List<String> lines = FileUtils.readLines(file);
        System.out.println(file.getName()); 
//        getConf();
//        importData(lines);
        
        
    }
    
    static public ArrayList<String> getConf() throws SQLException{
    
    confNames= new ArrayList();
    confLengths= new ArrayList();
    
    String sql="select * from cdr_conf";
    Statement st =conn.createStatement();
    ResultSet rs=st.executeQuery(sql);
    
    while(rs.next()){
    String confName= rs.getString("conf_key");
    confNames.add(confName);
    String confLength=rs.getString("conf_value");
    confLengths.add(confLength);
    
    }    
    return null;
    }

    static public void importData(List<String> lines ) throws SQLException, ClassNotFoundException {
        
        for(int i=0;i<lines.size();i++){
        String line =lines.get(i);
        String insertScript = parseData(line);
        String sql = "INSERT INTO CALL_LOG_1 VALUES " + insertScript;       
        if(conn.isClosed()){
        conn =DbConnection.openDbConnection();
        }
        Statement stmt=conn.createStatement();
        int sonuc= stmt.executeUpdate(sql);
     }
        
    }   
   static public String parseData(String line) {
       int count=0;
        StringBuilder buffer = null;
        StringBuilder stringBuilder = null;
        String son1 = "";
        if (line.length() > 30) {
            line = line.trim();
            buffer = null;
            stringBuilder = new StringBuilder();
            for (int i = 0; i < confNames.size(); i++) {
                String string = null;
                String string2 = null;
                string = confLengths.get(i).toString();

                string2 = confNames.get(i).toString();

                int deger = Integer.parseInt(string);

                if (i == 0) {
                    String buf2 = line.substring(0, deger);

                    buffer = stringBuilder.append("('");
                    buffer = stringBuilder.append(buf2.trim());
                    buffer = stringBuilder.append("'");
                    count = count + deger;
                }
                if (i > 0) {
                    if (string2.equals("bosluk")) {
                        count = count + deger;
                    } else {
                        int count2 = count + deger;
                        buffer = stringBuilder.append(",'");
                        String buf1 = line.substring(count, count2);
                        buffer = stringBuilder.append(buf1.trim());
                        buffer = stringBuilder.append("'");
                        count = count2;
                    }
                }
            }
            buffer = stringBuilder.append(")");
            StringBuilder son = buffer;
            son1 = son.toString();
            //System.out.println("SON1 = " + son1);
            count = 0;
        }
        return son1;
    }
}
