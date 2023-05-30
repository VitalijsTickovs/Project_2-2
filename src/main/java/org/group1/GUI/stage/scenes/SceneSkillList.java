package org.group1.GUI.stage.scenes;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.group1.GUI.stage.StageManager;
import org.group1.GUI.stage.scenes.utils.ButtonFactory;

import java.util.ArrayList;

public class SceneSkillList extends SceneManager implements ICustomScene {
    ArrayList<String> ruleNames =new ArrayList<>();
    ArrayList<Button> buttons = new ArrayList<>();

    private Button submit,displaySkills,back,help,defineSkills, defineCFG;
    private final int skillSize;

    public SceneSkillList(){
        makeNewPane();
        this.skillSize = StageManager.getConnection().getSkillData().size();
        loadSkills();
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
        UIPane.getChildren().add(displaySkills);

        //defineSkills button
        defineSkills = ButtonFactory.createButton("DEFINE TEMPLATE", 20, 170);
        UIPane.getChildren().add(defineSkills);

        //define CFG button
        defineCFG = ButtonFactory.createButton("DEFINE CFG", 20, 210);
        UIPane.getChildren().add(defineCFG);

        //help button
        help = ButtonFactory.createButton("HELP", 20, 250);
        UIPane.getChildren().add(help);

        //back button
        back = ButtonFactory.createButton("BACK", 20, 290);
        UIPane.getChildren().add(back);

        //submit button
        submit = ButtonFactory.createButton("LOAD", 386, 520);
        submit.setStyle("-fx-background-color: rgba(159,182,189,1)");
        submit.setPrefSize(400,50);
        UIPane.getChildren().add(submit);


    }
    public void setButtonsAction(){
        //define skills action
        ButtonFactory.setDefaultActions(defineSkills);
        defineSkills.setOnAction(e ->{
            SceneDefineTemplate sceneDefineTemplate = new SceneDefineTemplate();
            StageManager.setScene(sceneDefineTemplate);
        });

        //define CFG action
        ButtonFactory.setDefaultActions(defineCFG);
        defineCFG.setOnAction(e -> {
            SceneDefineCFG chatWindow = new SceneDefineCFG();
            StageManager.setScene(chatWindow);
        });

        //help actions
        ButtonFactory.setDefaultActions(help);
        help.setOnAction(e ->{
            SceneHelp sceneHelp = new SceneHelp();
            StageManager.setScene(sceneHelp);
        });

        //back button actions
        ButtonFactory.setDefaultActions(back);

        back.setOnAction(lam ->{
            SceneChat chatWindow = null;
            try {
                StageManager.getConnection().reload();
                chatWindow = new SceneChat();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            StageManager.setScene(chatWindow);
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
        scrollPane.setStyle("-fx-background-color: transparent;"+"-fx-border-color: red;" + "-fx-background: transparent;" );
        scrollPane.setContent(scrollChat);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        UIPane.getChildren().add(scrollPane);

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
                SceneSkillEditor sceneSkillEditor = new SceneSkillEditor(tempIndex);
                StageManager.setScene(sceneSkillEditor);
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
