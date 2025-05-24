/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    List<IDAOListener> listeners;

    protected BaseDAO()
    {
        cachedEntities = new HashMap<>();
        listeners = new ArrayList<>();
        initialize();
    }

    abstract void onQuerySelector();

    abstract boolean update(T entity);

    abstract boolean delete(T entity);

    abstract boolean add(T entity);

    private void initialize()
    {
        onQuerySelector();
    }
    
    public void addListener(IDAOListener listener)
    {
        listeners.add(listener);
    }
    
    public void notifyAllListeners()
    {
        for(var l : listeners)
        {
            l.onChanged();
        }
    }

    public void saveChanges()
    {

    }

    public T getEntity(Integer idInteger)
    {
        return cachedEntities.get(idInteger);
    }

    public T getEntity(String idString)
    {
        return getEntity(Objects.hashCode(idString));
    }

    public Collection<T> getEntitiesAll()
    {
        return cachedEntities.values();
    }

    public int getCount()
    {
        return cachedEntities.size();
    }

    protected void addToCacheInternal(T entity)
    {
        cachedEntities.put(entity.hashCode(), entity);
        notifyAllListeners();
    }

    protected void removeFromCacheInternal(T entity)
    {
        cachedEntities.remove(entity.hashCode());
        notifyAllListeners();
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
        for (T entity : cachedEntities.values())
        {
            System.out.println(entity);
        }
    }
}
