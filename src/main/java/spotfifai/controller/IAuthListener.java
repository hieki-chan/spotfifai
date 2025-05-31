/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import spotfifai.models.User;

/**
 *
 * @author admin
 */
public interface IAuthListener
{
    void onSignedIn(User user);
    void onSignedOut();
}
