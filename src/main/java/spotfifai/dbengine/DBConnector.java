/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dbengine;

/**
 *
 * @author admin
 */
import java.sql.*;

public class DBConnector
{
    static final String databaseName = "SPOTFIFAI";
    static final String serverName = "HieuCute\\MSSQLSERVER01";
    static final int port = 1433;
    
    static final String username = "sa";
    static final String password = "hiudz123";
    
    private Connection connection;
    
    public Connection getConnection()
    {
        if(connection == null)
            connect();
        
        return connection;
    }
    
    public void connect()
    {
        String url = "jdbc:sqlserver://"+ serverName + ":" + port + ";databaseName="+ databaseName +";encrypt=true;trustServerCertificate=true";

        try
        {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("db connected !");
            
            connection =  conn;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
