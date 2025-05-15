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
import spotfifai.util.located.IService;

public class DBConnector implements IService
{
    static final String DATABASE_NAME = "SPOTFIFAI";
    static final String SERVER_NAME = "HieuCute\\MSSQLSERVER01";
    static final int PORT = 1433;
    
    static final String USER_NAME = "sa";
    static final String PASSWORD = "hiudz123";
    
    private Connection connection;
    
    public Connection getConnection()
    {
        if(connection == null)
            connect();
        
        return connection;
    }
    
    public void connect()
    {
        String url = "jdbc:sqlserver://"+ SERVER_NAME + ":" + PORT + ";databaseName="+ DATABASE_NAME +";encrypt=true;trustServerCertificate=true";

        try
        {
            Connection conn = DriverManager.getConnection(url, USER_NAME, PASSWORD);
            System.out.println("db connected !");
            
            connection =  conn;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
