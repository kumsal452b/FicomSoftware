package org.kumsal.ficomSoft.AdapterModelClass;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.kairos.layouts.RecyclerView;

import java.io.IOException;

public class load_adapter extends RecyclerView.Adapter<load_adapter.testHolder> {
    class testHolder extends RecyclerView.ViewHolder{

        public Label count;
        public JFXDatePicker time;
        public JFXTextField sayi;
        public JFXTextField konu;
        public JFXTextField adet;
        public JFXDatePicker evrakTarihi;
        public JFXDatePicker imhaTarihi;

        public testHolder(FXMLLoader loader) {

            super(loader);
            AnchorPane pane= null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            count= (Label) pane.getChildren().get(0);
             time= (JFXDatePicker) pane.getChildren().get(1);
             sayi= (JFXTextField) pane.getChildren().get(2);
             konu= (JFXTextField) pane.getChildren().get(3);
             adet= (JFXTextField) pane.getChildren().get(4);
             evrakTarihi= (JFXDatePicker) pane.getChildren().get(5);
             imhaTarihi= (JFXDatePicker) pane.getChildren().get(6);
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {
        try {
            fxmlLoader.load(getClass().getResource("upload_single.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {

    }
}
