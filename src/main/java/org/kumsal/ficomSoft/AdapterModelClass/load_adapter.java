package org.kumsal.ficomSoft.AdapterModelClass;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import org.kairos.layouts.RecyclerView;

import java.io.IOException;

public class load_adapter extends RecyclerView.Adapter<load_adapter.testHolder> {
    class testHolder extends RecyclerView.ViewHolder{

        public testHolder(FXMLLoader loader) {
            Label count=loader.
            super(loader);
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
