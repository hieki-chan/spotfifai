/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util;

/**
 *
 * @author admin
 */
public class TimeUtil
{
    public static String getTimeInString(float duration)
    {
        int totalSeconds = Math.round(duration);
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%2d:%02d", minutes, seconds);
    }
}
