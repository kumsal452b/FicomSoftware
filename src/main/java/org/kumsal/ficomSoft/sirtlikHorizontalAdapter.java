package org.kumsal.ficomSoft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.kairos.layouts.RecyclerView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class sirtlikHorizontalAdapter extends RecyclerView.Adapter<sirtlikHorizontalAdapter.testHolder> {
    public sirtlikHorizontalAdapter() {

    }

    public class testHolder extends RecyclerView.ViewHolder {

        @FXML
        private RecyclerView<RecyclerView<sirtlikModel2>> recyclerHorizontal;

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
        fxmlLoader.setLocation(getClass().getResource("sirtlikHorizontalSingle.fxml"));
        return new testHolder(fxmlLoader);
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {
        RecyclerView<sirtlikModel2> theModel = (RecyclerView<sirtlikModel2>) o;
        testHolder.recyclerHorizontal.getItems().add(theModel);
    }
}
