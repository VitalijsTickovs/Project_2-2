package org.group1.GUI.utils;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonFactory {
    private static final Font FONT_STYLE = Font.font("Impact",FontWeight.BOLD,30);
    private static final Color TEXT_FILL = Color.WHITE;
    public static Button createButton(String title, int xPosition, int yPosition){
        Button button = new Button();
        button.setText(title);
        button.setFont(FONT_STYLE);
        button.setStyle("-fx-background-color: transparent");
        button.setTextFill(TEXT_FILL);
        button.setLayoutX(xPosition);
        button.setLayoutY(yPosition);
        button.setCursor(Cursor.CLOSED_HAND);

        return button;
    }
    
    public static Button setDefaultActions(Button button){
        button.setOnMouseEntered(e ->{
            button.setTextFill(Color.rgb(42,97,117));
        });
        button.setOnMouseExited(e ->{
            button.setTextFill(Color.WHITE);
        });
        return button;
    }

}
