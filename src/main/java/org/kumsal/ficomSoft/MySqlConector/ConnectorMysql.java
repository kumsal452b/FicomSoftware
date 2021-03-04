package org.kumsal.ficomSoft.MySqlConector;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.jdbc.Driver;

import java.nio.channels.ConnectionPendingException;

public class ConnectorMysql {
    private String user = "hbstudent";
    private String pass = "hbstudent";

    private String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
    private String driver = "com.mysql.cj.jdbc.Driver";
    public static MysqlDataSource connect(){
        MysqlDataSource nese = new MysqlDataSource();
        String jdbcConnection="jdbc:mysql://localhost:3306/ficomdb?useSSL=false&serverTimezone=GMT";
        String user="hbstudent";
        String password="hbstudent";
        nese.setUrl(jdbcConnection);
        nese.setPassword(password);
        nese.setUser(user);
        return nese;
    }
}
