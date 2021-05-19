module org.kumsal.ficomSoft {

    requires javafx.fxml;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires mysql.connector.java;

    requires ojdbc10;
    requires java.sql;
    requires java.naming;
    requires javafx.graphics;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires javafx.controls;
    requires kairos.all;

    requires commons.net;
    opens org.kumsal.ficomSoft;
    opens org.kumsal.ficomSoft.AdapterModelClass to com.jfoenix, javafx.fxml, javafx.controls, javafx.base;


    exports org.kumsal.ficomSoft;
}