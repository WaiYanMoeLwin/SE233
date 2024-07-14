module se233.chapter1casestudy {
    requires javafx.controls;
    requires javafx.fxml;


    opens se233.chapter1casestudy to javafx.fxml;
    exports se233.chapter1casestudy;
}