module org.kumsal.ficomSoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;


    opens org.kumsal.ficomSoft to javafx.fxml;
    exports org.kumsal.ficomSoft;
}