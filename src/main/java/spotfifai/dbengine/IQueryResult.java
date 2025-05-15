/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dbengine;

import java.sql.ResultSet;
import java.sql.SQLException;


@FunctionalInterface
public interface IQueryResult
{
    void onNext(ResultSet rs) throws SQLException;
}
