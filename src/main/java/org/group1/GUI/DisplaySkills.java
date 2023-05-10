package org.group1.GUI;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.GUI.utils.ButtonFactory;
import org.group1.back_end.response.Response;

import java.sql.SQLException;
import java.util.ArrayList;

public class DisplaySkills extends StageManager implements ICustomStage {
    ArrayList<String> ruleNames =new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();

    private Stage chatStage;
    private Button submit,displaySkills,back,help,defineSkills;
    private final int skillSize;
    private final Response response;

    public DisplaySkills(Response response){
        this.skillSize = response.getSkillData().size();
        this.response = response;
        loadSkills();
        initStage();
        design();
    }

    /**
     * Generates rule names
     */
    public void loadSkills(){
        for (int i = 1; i < this.skillSize+1; i++) {
            ruleNames.add("Rule "+i);
        }
    }

    public void createButtons(){
        // displaySkills button
        displaySkills = ButtonFactory.createButton("DISPLAY SKILLS", 20, 130);
        displaySkills.setTextFill(Color.rgb(42,97,117));
        UIpane.getChildren().add(displaySkills);

        //defineSkills button
        defineSkills = ButtonFactory.createButton("DEFINE SKILLS", 20, 170);
        UIpane.getChildren().add(defineSkills);

        //help button
        help = ButtonFactory.createButton("HELP", 20, 210);
        UIpane.getChildren().add(help);

        //back button
        back = ButtonFactory.createButton("BACK", 20, 250);
        UIpane.getChildren().add(back);

        //submit button
        submit = ButtonFactory.createButton("LOAD", 386, 520);
        submit.setStyle("-fx-background-color: rgba(159,182,189,1)");
        submit.setPrefSize(400,50);
        UIpane.getChildren().add(submit);


    }
    public void setButtonsAction(){
        //define skills action
        ButtonFactory.setDefaultActions(defineSkills);
        defineSkills.setOnAction(e ->{
            SkillEditor skillEditor = new SkillEditor(response);
            skillEditor.setStage(UIstage,chatStage);
        });

        //help actions
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e ->{
            //TODO : SET HELP WINDOW
        });

        //back button actions
        ButtonFactory.setDefaultActions(back);

        back.setOnAction(lam ->{
            ChatWindow chatWindow = null;
                try {
                    response.reload();
                    chatWindow = new ChatWindow();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                chatWindow.setStage(UIstage,chatStage);
        });

        //submit button action
        submit.setOnMouseEntered(e ->{
            submit.setStyle("-fx-background-color: rgba(42,97,117,1)");
        });
        submit.setOnMouseExited(e ->{
            submit.setStyle("-fx-background-color: rgba(159,182,189,1)");
        });

    }


    public void createScrollPane(){
        //scrollpane with skills
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollChat.setStyle("-fx-background-color: transparent");

        scrollPane.setTranslateX(340);
        scrollPane.setTranslateY(50);
        scrollPane.setPrefSize(470,460);
        // TODO: IF YOU NEED THE RED BORDER add " -fx-border-color: red"
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-border-color: red" );
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIpane.getChildren().add(scrollPane);

        UIstage.setOnShown(e ->
                scrollPane.lookup(".viewport").setStyle("-fx-background-color: transparent;"));

        for (int i = 0, y=20; i < ruleNames.size(); i++) {
            int tempIndex = i;

            Button button = ButtonFactory.createButton(ruleNames.get(i), 180, y);
            button.setOnMouseEntered(e-> {
                button.setTextFill(Color.rgb(42,97,117));
            });
            button.setOnMouseExited(e -> {
                button.setTextFill(Color.WHITE);
            });
            button.setOnAction(e-> {
                try {
                    SkillDetails skillDetails= new SkillDetails(tempIndex ,response);
                    skillDetails.setStage(UIstage,chatStage);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            scrollChat.getChildren().add(button);
            buttons.add(button);
            y+=50;
        }
        scrollPane.setContent(scrollChat);

    }

    /**
     * Main method that combines all javafx components
     */
    @Override
    public void design() {
        createText("Choose which skill to access", 340, 40);

        createSideMenu();

        createButtons();
        setButtonsAction();
        createScrollPane();

    }
}
