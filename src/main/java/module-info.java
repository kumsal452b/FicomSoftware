module org.kumsal.ficomSoft {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;

    opens org.kumsal.ficomSoft to javafx.fxml;
    exports org.kumsal.ficomSoft;

}