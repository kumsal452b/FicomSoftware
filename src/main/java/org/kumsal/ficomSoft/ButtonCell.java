package org.kumsal.ficomSoft;

import com.jfoenix.controls.JFXButton;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ButtonCell implements Callback<TableColumn<LoadedFileModel, JFXButton>, TableCell<LoadedFileModel, JFXButton>> {
    @Override
    public TableCell<LoadedFileModel, JFXButton> call(TableColumn<LoadedFileModel, JFXButton> loadedFileModelJFXButtonTableColumn) {
        TableCell<LoadedFileModel, JFXButton> cell=new TableCell<LoadedFileModel, JFXButton>(){
            private final Button button;

            {
                button = new Button();
                button.setOnAction(evt -> {
                    System.out.println(getIndex());
                });
            }

            @Override
            public Node getStyleableNode() {
                return button;
            }
        };
        return cell;
    }
}
