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
public abstract class BaseDAO<TKey, TValue>
{
    protected final Map<TKey, TValue> cachedEntities;

    protected BaseDAO()
    {
        cachedEntities = new HashMap<>();
    }
    
    public int getCount()
    {
        return cachedEntities.size();
    }

    public TValue getEntity(TKey idKey)
    {
        return cachedEntities.get(idKey);
    }
    
    public Map<TKey, TValue> getEntitiesAll()
    {
        return cachedEntities;
    }
    
    public boolean contains(TValue entity)
    {
        return cachedEntities.containsValue(entity);
    }

    protected Connection getConnection()
    {
        return ServiceLocator.get(DBConnector.class).getConnection();
    }
}
