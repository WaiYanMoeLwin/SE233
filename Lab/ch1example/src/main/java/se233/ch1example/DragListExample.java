package se233.ch1example;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DragListExample extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Drag List Example");
        Label lbl1 = new Label("Source List");
        Label lbl2 = new Label("Target List");
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Java", "Python", "Ruby", "R", "Go", "C");
        DragListModel source = new DragListModel();
        DragListModel target = new DragListModel();
        source.lV.getItems().addAll(list);
        GridPane pane = new GridPane();
        pane.addRow(1, lbl1, lbl2);
        pane.addRow(2, source.lV, target.lV);
        VBox root = new VBox(10);
        root.getChildren().add(pane);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
