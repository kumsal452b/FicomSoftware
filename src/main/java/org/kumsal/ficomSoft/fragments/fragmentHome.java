package org.kumsal.ficomSoft.fragments;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.kairos.core.Fragment;

import java.io.IOException;

public class fragmentHome extends Fragment {
    @Override
    public void onCreateView(FXMLLoader fxmlLoader) {
        try {
            fxmlLoader.load(getClass().getResource("home.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}
