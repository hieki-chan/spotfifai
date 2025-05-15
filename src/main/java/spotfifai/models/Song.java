/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

/**
 *
 * @author admin
 */
public class Song
{

    private int id;
    private String name;
    private String description;

    public Song(int id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Song(String name, String description)
    {
        this(0, name, description);
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return "Song{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Song other = (Song) obj;
        return this.id == other.id;
    }

}
