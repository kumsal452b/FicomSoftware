package org.kumsal.ficomSoft.AdapterModelClass;

import javafx.fxml.FXMLLoader;
import org.kairos.layouts.RecyclerView;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.testHolder> {
    class testHolder extends RecyclerView.ViewHolder{

        public testHolder(FXMLLoader loader) {

            super(loader);
        }
    }

    @Override
    public testHolder onCreateViewHolder(FXMLLoader fxmlLoader) {

        return null;
    }

    @Override
    public void onBindViewHolder(testHolder testHolder, Object o) {

    }
}
