package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import org.kairos.layouts.RecyclerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class sirtlikHorizontalAdapter extends RecyclerView.Adapter<sirtlikHorizontalAdapter.testHolder> {
    public sirtlikHorizontalAdapter() {

    }

    public class testHolder extends RecyclerView.ViewHolder {

        @FXML
        private RecyclerView<?> recyclerHorizontal;

        public testHolder(FXMLLoader loader) {
            super(loader);
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {
        URL url=null;
        try {
            url = new File("src/main/resources/org/kumsal/ficomSoft/sirtlikHorizontalSingle.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        fxmlLoader.setLocation(url);
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {
        sirtlikModel theModel = (sirtlikModel) o;
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
