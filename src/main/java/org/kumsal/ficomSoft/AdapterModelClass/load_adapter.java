package org.kumsal.ficomSoft.AdapterModelClass;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.kairos.layouts.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class load_adapter extends RecyclerView.Adapter<load_adapter.testHolder> {
    public load_adapter() {

    }

    public class testHolder extends RecyclerView.ViewHolder {

        @FXML
        private Label single_count;

        @FXML
        private JFXTextField single_sayi;

        @FXML
        private JFXDatePicker single_date;

        @FXML
        private JFXTextField single_konu;

        @FXML
        private JFXTextField single_adet;

        @FXML
        private JFXDatePicker single_evraktarihi;

        @FXML
        private JFXDatePicker single_imhaTarihi;


        public testHolder(FXMLLoader loader) {

            super(loader);
            single_konu.setOnMouseClicked(mouseEvent -> {
                System.out.println("tiklandi");
            });
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {
        URL url=null;
        try {
            url = new File("src/main/resources/org/kumsal/ficomSoft/upoad_single.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        fxmlLoader.setLocation(url);
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {
        load_model theModel = (load_model) o;
        testHolder.single_adet.setText(theModel.getAdet());
        testHolder.single_count.setText(theModel.getCount());
        testHolder.single_date.setValue(theModel.getTime());
        testHolder.single_evraktarihi.setValue(theModel.getEvrakTarihi());
        testHolder.single_imhaTarihi.setValue(theModel.getImhaTarihi());
        testHolder.single_konu.setText(theModel.getKonu());

    }
}
