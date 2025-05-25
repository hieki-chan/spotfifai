/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package spotfifai.controller;

import spotfifai.models.Song;

/**
 *
 * @author admin
 */
public interface IMusicListenter
{
    void onOpen(Song song, float duration);
    void onProgress(float ratio, float progressInSeconds);
}
