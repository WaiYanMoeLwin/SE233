module se233.ch1example {
    requires javafx.controls;
    requires javafx.fxml;


    opens se233.ch1example to javafx.fxml;
    exports se233.ch1example;
}