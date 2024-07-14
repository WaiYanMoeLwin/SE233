package se233.chapter2.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import org.json.JSONException;
import se233.chapter2.Launcher;
import se233.chapter2.model.Currency;
import se233.chapter2.model.CurrencyEntity;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    public static void onRefresh() {
        try {
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onAdd() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Currency");
            dialog.setContentText("Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent()) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                Currency c = new Currency(code.get().toUpperCase());
                if (currencyList.contains(c)) {
                    Alert alert = new Alert(Alert.AlertType.NONE, String.format("Currency already exists with this code: %s", c.getShortCode()), ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                List<CurrencyEntity> cList = FetchData.fetchRange(Launcher.getBase(), c.getShortCode(), Launcher.getN());
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));
                currencyList.add(c);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvalidCurrencyCodeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage(), new ButtonType("Try again"));
            alert.setTitle("Invalid Currency Code");
            alert.showAndWait();
            onAdd();
        }
    }

    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void onWatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Watch");
                dialog.setContentText("Rate:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                Optional<String> retrievedRate = dialog.showAndWait();
                if (retrievedRate.isPresent()) {
                    double rate = Double.parseDouble(retrievedRate.get());
                    currencyList.get(index).setWatch(true);
                    currencyList.get(index).setWatchRate(rate);
                    Launcher.setCurrencyList(currencyList);
                    Launcher.refreshPane();
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onUnwatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.get(index).setWatch(false);
                currencyList.get(index).setWatchRate(0.0);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void onChange() {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Change Base Currency");
            dialog.setContentText("Base Currency code:");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            Optional<String> code = dialog.showAndWait();
            if (code.isPresent()) {
                List<Currency> currencyList = Launcher.getCurrencyList();
                String base = code.get().toUpperCase();
                for (int i = 0; i < currencyList.size(); i++) {
                    if (currencyList.get(i).getShortCode().equals(base)) {
                        currencyList.set(i, new Currency(Launcher.getBase()));
                    }
                    List<CurrencyEntity> cList = FetchData.fetchRange(base, currencyList.get(i).getShortCode(), Launcher.getN());
                    currencyList.get(i).setHistorical(cList);
                    currencyList.get(i).setCurrent(cList.get(cList.size() - 1));
                    currencyList.get(i).setWatch(false);
                    currencyList.get(i).setWatchRate(0.0);
                }
                Launcher.setCurrencyList(currencyList);
                Launcher.setBase(base);
                Launcher.refreshPane();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvalidCurrencyCodeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage(), new ButtonType("Try again"));
            alert.setTitle("Invalid Currency Code");
            alert.showAndWait();
            onChange();
        }
    }
}
