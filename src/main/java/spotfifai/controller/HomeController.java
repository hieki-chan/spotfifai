/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import spotfifai.dao.SongDAO;
import spotfifai.models.Song;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class HomeController implements IService
{
    final SongDAO songDAO;
    
    public HomeController(SongDAO songDAO)
    {
        this.songDAO = songDAO;
    }
    
    public List<Song> loadTopSongs()
    {
        var songCollection = songDAO.getEntitiesAll();
        var songList =  new ArrayList<>(songCollection);
        
        Random rand = new Random();
        while(songList.size() > 10)
        {
            songList.remove(rand.nextInt(songList.size()));
        }
        
        return songList;
    }
    
    public Song searchSong()
    {
        return null;
    }
}
