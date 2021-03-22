package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.kairos.layouts.RecyclerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class sirlik_adapter extends RecyclerView.Adapter<sirlik_adapter.testHolder> {
    public sirlik_adapter() {

    }

    public class testHolder extends RecyclerView.ViewHolder {



        @FXML
        private Label destisno;

        @FXML
        private Label birimadi;

        @FXML
        private Label spdkod;

        @FXML
        private Label spdkarsilik;

        @FXML
        private Label ozelkod;

        @FXML
        private Label klasorno;

        @FXML
        private Label klasoryili;

        @FXML
        private Label imhatarihi;

        public testHolder(FXMLLoader loader) {
            super(loader);
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {
        URL url=null;
        try {
            url = new File("src/main/resources/org/kumsal/ficomSoft/sirtlikModel.fxml").toURI().toURL();
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
