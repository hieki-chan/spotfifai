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
    private final Map<Integer, T> cachedEntities;
    
    protected  BaseDAO()
    {
        cachedEntities = new HashMap<>();
        initialize();
    }
    
    abstract void onQuerySelector();
    abstract void update();
    abstract void delete();
    abstract void add(T entity);
    
    private void initialize()
    {
        onQuerySelector();
    }
    
    public void saveChanges()
    {
        
    }
    
    public T get(Integer hashCode)
    {
        return cachedEntities.get(hashCode);
    }
    
    public int getCount()
    {
        return cachedEntities.size();
    }
    
    protected void addToCacheInternal(T entity)
    {
        cachedEntities.put(entity.hashCode(), entity);
    }
    
    public boolean contains(T entity)
    {
        return cachedEntities.containsKey(entity.hashCode());
    }
    
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
