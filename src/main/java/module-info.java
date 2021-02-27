module org.kumsal.ficomSoft {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.kumsal.ficomSoft to javafx.fxml;
    exports org.kumsal.ficomSoft;
}