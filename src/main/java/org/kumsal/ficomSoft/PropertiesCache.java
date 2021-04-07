package org.kumsal.ficomSoft;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.kumsal.ficomSoft.MySqlConector.ConnectorMysql;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache
{
    private final Properties configProp = new Properties();
    public boolean isBreak=false;
    private static PropertiesCache cache=null;
    private PropertiesCache(){

        String args1 = System.getProperty("pathConf");
//        if (args1.isEmpty()){
//            System.out.println("dosya yolu parametresi gecilmedi");
//            return;
//        }
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        s+=System.getProperty("pathConf");
        File file  = new File(s);
        System.out.println("dosya adı="+file.getName());
        InputStream is = null;
        try {
//            is = new FileInputStream(file);
            is=getClass().getResourceAsStream("config.properties");
            configProp.load(is);
        } catch (FileNotFoundException e) {
            System.out.println("io error="+e.getMessage());

        } catch (IOException e) {
            System.out.println("configProp=error="+e.getMessage());
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