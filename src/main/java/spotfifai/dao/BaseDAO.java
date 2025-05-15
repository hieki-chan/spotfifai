/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import spotfifai.dbengine.DBConnector;
import spotfifai.util.located.ServiceLocator;

/**
 *
 * @author admin
 * @param <T>
 */
public abstract class BaseDAO<T>
{
    protected Map<Integer, T> cachedEntities = new HashMap<>();
    
    protected  BaseDAO()
    {
        cachedEntities = querySelector();
    }
    
    abstract Map<Integer, T> querySelector();
    abstract void update();
    abstract void delete();
    abstract void add(T entity);
    
    protected Connection getConnection()
    {
        return ServiceLocator.get(DBConnector.class).getConnection();
    }
    
    public void debug()
    {
        for(T entity : cachedEntities.values())
        {
            System.out.println(entity);
        }
    }
}
