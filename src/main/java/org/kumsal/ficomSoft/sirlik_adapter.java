package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
        fxmlLoader.setLocation(getClass().getResource("sirtlikModel.fxml"));
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {
        sirtlikModel2 theModel = (sirtlikModel2) o;
        testHolder.birimadi.setText(theModel.getBirimad());
        testHolder.imhatarihi.setText(theModel.getImhaTarihi());
        testHolder.klasorno.setText(theModel.getKlasno());
        testHolder.klasoryili.setText(theModel.getKtarihi());
        testHolder.destisno.setText(theModel.getDestisno());
        testHolder.spdkarsilik.setText(theModel.getSpdkarsilik());
        testHolder.spdkod.setText(theModel.getSpdkod());
        testHolder.ozelkod.setText(theModel.getOzelkod());
    }
}
