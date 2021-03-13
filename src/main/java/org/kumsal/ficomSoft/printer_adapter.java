package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.kairos.layouts.RecyclerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class printer_adapter extends RecyclerView.Adapter<printer_adapter.testHolder> {
    public printer_adapter() {

    }

    public class testHolder extends RecyclerView.ViewHolder {



        @FXML
        private Label print_tariih;

        @FXML
        private TextField print_sira;

        @FXML
        private Label print_sayi;

        @FXML
        private Label print_konusu;

        @FXML
        private Label print_adet;

        @FXML
        private Label print_evrak;

        @FXML
        private Label print_imheTarihi;


        public testHolder(FXMLLoader loader) {
            super(loader);
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {
        URL url=null;
        try {
            url = new File("src/main/resources/org/kumsal/ficomSoft/printLayoutSingle.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        fxmlLoader.setLocation(url);
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {
        printer_model theModel = (printer_model) o;
        testHolder.print_adet.setText(theModel.getAdet());
        testHolder.print_sayi.setText(theModel.getSayi());
        testHolder.print_tariih.setText(theModel.getTime());
        testHolder.print_evrak.setText(theModel.getEvrakTarihi());
        testHolder.print_imheTarihi.setText(theModel.getImhaTarihi());
        testHolder.print_konusu.setText(theModel.getKonu());
        testHolder.print_sira.setText(theModel.getCount());
    }
}
