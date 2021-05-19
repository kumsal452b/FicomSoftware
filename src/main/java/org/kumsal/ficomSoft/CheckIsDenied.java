package org.kumsal.ficomSoft;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckIsDenied {
    static MysqlDataSource dbsource = ConnectorMysql.connect();
    static boolean temp=false;
    public static boolean isDenied(int userID, String username, boolean isAdmin) throws SQLException {
        if (!isAdmin){
            PreparedStatement statement1 = dbsource.getConnection().prepareStatement(
                    "select * from users where `username`=? and `UID`=?");
            statement1.setString(1,username);
            statement1.setInt(2,userID);
            ResultSet resultSet=statement1.executeQuery();
            while (resultSet.next()){
                temp=resultSet.getBoolean(7);
            }
            return temp;
        }else{
            return false;
        }
    }
}
