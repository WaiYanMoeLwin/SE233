package se233.chapter2.view;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import se233.chapter2.model.Currency;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyParentPane extends ScrollPane {
    public CurrencyParentPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        this.setPadding(new Insets(0));
        this.setMaxHeight(500);
        refreshPane(currencyList);
    }

    public void refreshPane(List<Currency> currencyList) throws ExecutionException, InterruptedException {
        FlowPane pane = new FlowPane();
        for (int i = 0; i < currencyList.size(); i++) {
            CurrencyPane cp = new CurrencyPane(currencyList.get(i));
            pane.getChildren().add(cp);
        }
        this.setContent(pane);
    }
}
