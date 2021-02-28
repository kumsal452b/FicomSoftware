module org.kumsal.ficomSoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens org.kumsal.ficomSoft to javafx.fxml, com.jfoenix;
    exports org.kumsal.ficomSoft;

}