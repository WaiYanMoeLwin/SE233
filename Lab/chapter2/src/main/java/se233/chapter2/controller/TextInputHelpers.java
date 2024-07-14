package se233.chapter2.controller;

import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class TextInputHelpers {
    private static TextInputDialog createTextInputDialog(String title, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        return dialog;
    }

    public static Optional<String> inputCurrencyCode(String title, String content) throws IOException {
        TextInputDialog dialog = createTextInputDialog(title, content);
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.isContentChange()) {
                change.setText(change.getText().toUpperCase());
                String newText = change.getControlNewText();
                if (newText.matches("[a-zA-Z]*") && newText.length() <= 3) {
                    return change;
                }
            }
            return null;
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        dialog.getEditor().setTextFormatter(formatter);
        Optional<String> shortCode = dialog.showAndWait();
        if (shortCode.isPresent()) {
            if (shortCode.get().isEmpty()) {
                throw new IOException("Currency code cannot be empty");
            }
        }
        return shortCode;
    }

    public static Optional<String> inputDouble(String title, String content) throws IOException {
        TextInputDialog dialog = createTextInputDialog(title, content);
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.isContentChange()) {
                change.setText(change.getText().toUpperCase());
                String newText = change.getControlNewText();
                if (newText.matches("[0-9]*\\.?[0-9]*")) {
                    return change;
                }
            }
            return null;
        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        dialog.getEditor().setTextFormatter(formatter);
        Optional<String> n = dialog.showAndWait();
        if (n.isPresent()) {
            if (n.get().isEmpty()) {
                throw new IOException("Input cannot be empty");
            }
        }
        return n;
    }
}
