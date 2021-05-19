package org.kumsal.ficomSoft;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class CheckDenied {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane mystackpane;

    @FXML
    void initialize() {
        JFXButton tamam=new JFXButton("Tamam");
        JFXDialog dialog=new JFXDialog(mystackpane,new Label("temel"), JFXDialog.DialogTransition.CENTER);
        JFXDialogLayout layout=new JFXDialogLayout();
        layout.setHeading(new Text("Dikkat"));
        layout.setBody(new Text("Yönetici tarafından engellendiniz. Oturumunuz kapatılacaktır."));
        tamam.setOnAction(event1 -> {
            Node node = (Node) event1.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            stage.close();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("primary.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setResizable(false);
            stage.setHeight(530);
            stage.setWidth(1000);
            stage.setMinHeight(530);
            stage.setMinWidth(1000);
            stage.setMaximized(false);
            stage.show();
            dialog.close();
        });
        layout.setActions(tamam);
        dialog.setContent(layout);
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent jfxDialogEvent) {

                dialog.show();
            }
        });
        dialog.show();

    }
}
