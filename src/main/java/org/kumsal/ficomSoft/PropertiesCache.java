package org.kumsal.ficomSoft;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache
{
    private final Properties configProp = new Properties();
    public boolean isBreak=false;
    private static PropertiesCache cache=null;
    private PropertiesCache(){
        //Private constructor to restrict new instances
        InputStream in = this.getClass().getResourceAsStream("src/main/resources/org/kumsal/ficomSoft/files/config.properties");
        URL url=null;
        try {
            File file=new File("src/main/resources/org/kumsal/ficomSoft/files/config.properties");
            if (!file.exists()){
                isBreak=true;
            }
            url = file.toURI().toURL();

        } catch (MalformedURLException e) {
            isBreak=true;
            e.printStackTrace();
        }
        try {
            InputStream test = url.openStream();
            configProp.load(test);
        } catch (IOException e) {
            isBreak=true;
        }
    }
    public static PropertiesCache isIsBreakDb(){
        if (cache==null){
        cache=new PropertiesCache();
        }
        return cache;
    }

    //Bill Pugh Solution for singleton pattern
    private static class LazyHolder
    {
        private static final PropertiesCache INSTANCE = new PropertiesCache();
    }

    public static PropertiesCache getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key){
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames(){
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key){
        return configProp.containsKey(key);
    }
}