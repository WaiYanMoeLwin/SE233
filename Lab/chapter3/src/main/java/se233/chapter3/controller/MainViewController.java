package se233.chapter3.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import se233.chapter3.Launcher;
import se233.chapter3.model.FileFreq;
import se233.chapter3.model.PdfDocument;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MainViewController {
    LinkedHashMap<String, List<FileFreq>> uniqueSets;
    @FXML
    private ListView inputListView;
    @FXML
    private ListView listView;
    @FXML
    private Button startButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    public void initialize() {
        inputListView.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf");
            if (db.hasFiles() && isAccepted) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        inputListView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                String filePath, fileName;
                int totalFiles = db.getFiles().size();
                for (int i = 0; i < totalFiles; i++) {
                    File file = db.getFiles().get(i);
                    filePath = file.getAbsolutePath();
                    inputListView.getItems().add(filePath);
                }
                inputListView.setCellFactory(lv -> new ListCell<String>(){
                    @Override
                    protected void updateItem(String fname, boolean empty) {
                        super.updateItem(fname,empty);
                        if (empty) {
                            setText(null);
                        } else if (fname != null) {
                            File f = new File(fname);
                            setText(f.getName());
                        } else {
                            setText(null);
                        }
                    }
                });
            }
            event.setDropCompleted(success);
            event.consume();
        });
        startButton.setOnAction(event -> {
            Parent bgRoot = Launcher.primaryStage.getScene().getRoot();
            Task<Void> processTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    ProgressIndicator pi = new ProgressIndicator();
                    VBox box = new VBox(pi);
                    box.setAlignment(Pos.CENTER);
                    Launcher.primaryStage.getScene().setRoot(box);
                    ExecutorService executor = Executors.newFixedThreadPool(4);
                    ExecutorCompletionService<Map<String, FileFreq>> completionService = new ExecutorCompletionService<>(executor);
                    listView.getItems().clear();
                    List<String> inputListViewItems = inputListView.getItems();
                    int totalFiles = inputListViewItems.size();
                    Map<String, FileFreq>[] wordMap = new Map[totalFiles];
                    for (int i = 0; i < totalFiles; i++) {
                        try {
                            String filePath = inputListViewItems.get(i);
                            PdfDocument p = new PdfDocument(filePath);
                            completionService.submit(new WordCountMapTask(p));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < totalFiles; i++) {
                        try {
                            Future<Map<String, FileFreq>> future = completionService.take();
                            wordMap[i] = future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        WordCountReduceTask merger = new WordCountReduceTask(wordMap);
                        Future<LinkedHashMap<String, List<FileFreq>>> future = executor.submit(merger);
                        uniqueSets = future.get();
                        listView.getItems().addAll(uniqueSets.entrySet().stream()
                                .map(entry -> entry.getKey() + " (" + uniqueSets.get(entry.getKey()).stream().map(x -> x.getFreq()).toList().toString().replaceAll("[\\[\\]]", "") + ")")
                                .collect(Collectors.toList()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        executor.shutdown();
                    }
                    return null;
                }
            };
            processTask.setOnSucceeded(e -> {
                Launcher.primaryStage.getScene().setRoot(bgRoot);
            });
            Thread thread = new Thread(processTask);
            thread.setDaemon(true);
            thread.start();
        });
        listView.setOnMouseClicked(event -> {
            List<FileFreq> listOfLinks = uniqueSets.get(((String) (listView.getSelectionModel().getSelectedItem())).split(" ")[0]);
            ListView<FileFreq> popupListView = new ListView<>();
            LinkedHashMap<FileFreq, String> lookupTable = new LinkedHashMap<>();
            for (int i = 0; i < listOfLinks.size(); i++) {
                lookupTable.put(listOfLinks.get(i), listOfLinks.get(i).getPath());
                popupListView.getItems().add(listOfLinks.get(i));
            }
            popupListView.setPrefHeight(popupListView.getItems().size() * 28);
            popupListView.setOnMouseClicked(innerEvent -> {
                Launcher.hs.showDocument("file:///" + lookupTable.get(popupListView.getSelectionModel().getSelectedItem()));
                popupListView.getScene().getWindow().hide();
            });
            Popup popup = new Popup();
            popup.getContent().add(popupListView);
            popup.getContent().get(0).setOnKeyPressed((code) -> {
                if (code.getCode().equals(KeyCode.ESCAPE)) {
                    popup.hide();
                }
            });
            popup.show(Launcher.primaryStage);
        });
        menuBar.getMenus().getFirst().getItems().get(0).setOnAction(event -> {
            System.exit(0);
        });
        menuBar.getMenus().get(1).getItems().get(0).setOnAction(event -> {
            Popup popup = new Popup();
            popup.getContent().add(new ListView(inputListView.getItems()));
            ((ListView) popup.getContent().get(0)).setCellFactory(lv -> new ListCell<String>(){
                @Override
                protected void updateItem(String fname, boolean empty) {
                    super.updateItem(fname,empty);
                    if (empty) {
                        setText(null);
                    } else if (fname != null) {
                        File f = new File(fname);
                        setText(f.getName());
                    } else {
                        setText(null);
                    }
                }
            });
            popup.getContent().get(0).setOnMouseClicked(e -> {
                inputListView.getItems().remove(((ListView) (popup.getContent().get(0))).getSelectionModel().getSelectedItem());
                popup.hide();
            });
            popup.show(Launcher.primaryStage);
        });
    }
}
