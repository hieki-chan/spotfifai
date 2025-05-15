/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util.located;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author admin
 */
public class ServiceLocator
{
    private static final Map<Class<?>, Object> services = new HashMap<>();
    
    public static <T> void register(T service)
    {
        services.put(service.getClass(), service);
    }
    
    public static <T> T get(Class<T> clazz)
    {
        return (T)services.get(clazz);
    }
}
