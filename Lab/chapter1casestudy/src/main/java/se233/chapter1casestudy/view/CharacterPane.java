package se233.chapter1casestudy.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.chapter1casestudy.Launcher;
import se233.chapter1casestudy.controller.AllCustomHandlers;
import se233.chapter1casestudy.model.character.BasedCharacter;

import se233.chapter1casestudy.controller.AllCustomHandlers.*;

public class CharacterPane extends ScrollPane {
    BasedCharacter character;

    public CharacterPane() {}
    private Pane getDetailsPane() {
        Pane characterInfoPane = new VBox(10);
        characterInfoPane.setBorder(null);
        characterInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label name, type, hp, atk, def, res;
        ImageView img = new ImageView();
        if (character != null) {
            name = new Label("Name: " + character.getName());
            img.setImage(new Image(Launcher.class.getResource(character.getImagepath()).toString()));
            type = new Label("Type: " + character.getType().toString());
            hp = new Label("HP: " + character.getHp() + "/" + character.getFullHp());
            atk = new Label("Power: " + character.getPower());
            def = new Label("Defense: " + character.getDefense());
            res = new Label("Resistance: " + character.getResistance());
        } else {
            name = new Label("Name: ");
            img.setImage(new Image(Launcher.class.getResource("assets/unknown.png").toString()));
            type = new Label("Type: ");
            hp = new Label("HP: ");
            atk = new Label("Power: ");
            def = new Label("Defense: ");
            res = new Label("Resistance: ");
        }

        Button genCharacter = new Button();
        genCharacter.setText("Generate Character");
        genCharacter.setOnAction(new AllCustomHandlers.GenCharacterHandler());

        characterInfoPane.getChildren().addAll(name, img, type, hp, atk, def, res, genCharacter);
        return characterInfoPane;
    }

    public void drawPane(BasedCharacter character) {
        this.character = character;
        Pane characterInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color: red;");
        this.setContent(characterInfoPane);
    }
}
