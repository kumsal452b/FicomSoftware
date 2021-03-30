package org.kumsal.ficomSoft.MySqlConector;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.jdbc.Driver;
import org.kumsal.ficomSoft.PropertiesCache;

import java.nio.channels.ConnectionPendingException;

public class ConnectorMysql {

    public static MysqlDataSource connect(){
        MysqlDataSource nese = new MysqlDataSource();

        String username= PropertiesCache.getInstance().getProperty("username");
        String password=PropertiesCache.getInstance().getProperty("password");
        String domain=PropertiesCache.getInstance().getProperty("domain");
        String db=PropertiesCache.getInstance().getProperty("database");

        String jdbcConnection="jdbc:mysql://"+domain+"/"+db+"?useSSL=false&serverTimezone=GMT";
        nese.setUrl(jdbcConnection);
        nese.setPassword(password);
        nese.setUser(username);
        return nese;
    }
}
