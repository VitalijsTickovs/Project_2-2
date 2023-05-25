package org.group1.GUI;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.group1.GUI.utils.ButtonFactory;

public class HelpScreen extends StageManager implements ICustomStage {
    private Button exitButton;
    protected int screenWidth = 900;
    protected int screenHeight = 700;
    protected AnchorPane UIpane = new AnchorPane();
    protected Scene UIscene = new Scene(UIpane, screenWidth,screenHeight);
    protected Stage UIstage = new Stage();
    protected Stage chatStage = new Stage();

    public HelpScreen() {
        UIscene.setFill(Color.rgb(18,64,76));
        UIpane.setStyle("-fx-background-color: transparent");
        UIstage.setScene(UIscene);
        design();
    }

    @Override
    public void design() {
        createButtons();
        addBackground();
        textContentDisplay();
    }
    public void start(){
        UIstage.show();
    }
    public void addBackground(){

        Rectangle rectangle = new Rectangle(20,35,850,140);
        rectangle.setFill(Color.rgb(18,64,76));
        rectangle.setStroke(Color.rgb(159,182,189));
        rectangle.setStrokeWidth(5);
        UIpane.getChildren().add(rectangle);

        Rectangle rectangle2 = new Rectangle(20,235,850,210);
        rectangle2.setFill(Color.rgb(18,64,76));
        rectangle2.setStroke(Color.rgb(159,182,189));
        rectangle2.setStrokeWidth(5);
        UIpane.getChildren().add(rectangle2);

        Rectangle rectangle3 = new Rectangle(20,505,850,150);
        rectangle3.setFill(Color.rgb(18,64,76));
        rectangle3.setStroke(Color.rgb(159,182,189));
        rectangle3.setStrokeWidth(5);
        UIpane.getChildren().add(rectangle3);
    }

public void createTextAreas(String text, int x, int y, int width, int height, Text textArea,int fontsize,Color color) {
    //User input textField
    textArea = new Text();
    textArea.setTranslateX(x);
    textArea.setTranslateY(y);
    textArea.setFont(Font.font("Calibri",FontWeight.EXTRA_BOLD, fontsize));
    textArea.setFill(color);
    textArea.setText(text);

    UIpane.getChildren().add(textArea);
}

    public void createTextAreas(String text, int x, int y, int width, int height, Text textArea,int fontsize) {
        //User input textField
        textArea = new Text();
        textArea.setTranslateX(x);
        textArea.setTranslateY(y);
        textArea.setFont(Font.font("Calibri",FontWeight.BOLD, fontsize));
        textArea.setFill(Color.WHITE);
        textArea.setText(text);

        UIpane.getChildren().add(textArea);
    }
    public void createButtons() {
        //exit Button
        exitButton = ButtonFactory.createButton("CLOSE", 770, 650);
        UIpane.getChildren().add(exitButton);

        setActionForButtons();
    }
    public void setActionForButtons() {
        //exit button
        ButtonFactory.setDefaultActions(exitButton);
        exitButton.setOnAction(e -> {
            UIstage.close();
        });
    }
    public void textContentDisplay(){
        //Title
        createTextAreas("Interaction With Agent", 30, 30, 720, 155, new Text(),25,Color.rgb(159,182,189));
        //Content
        String agentPar1 = "In order to interact with our agent open the chat window. Here you can ask the agent any question you want and if the agent ";
        String agentPar11= "possesses the answer, it will provide a response in text format visible in scrollable pane. However, if it is unable to assist with ";
        String agentPar12="your request, it will indicate so. Once you have entered your text, simply click the submit button to receive the answer.";
        createTextAreas(agentPar1, 30, 55, 720, 155, new Text(),16);
        createTextAreas(agentPar11, 30, 70, 720, 155, new Text(),16);
        createTextAreas(agentPar12, 30, 85, 720, 155, new Text(),16);
        String agentPar2="This project incorporates multiple NLP techniques to generate responses based on user input. These techniques include";
        String agentPar21="Perfect Matching, Keywords, Vectors, and Vector Sequences.";
        createTextAreas(agentPar2, 30, 110, 720, 155, new Text(),16);
        createTextAreas(agentPar21, 30, 125, 720, 155, new Text(),16);
        String agentPar3= "For instance, a potential prompt could be, \"Which lecture is scheduled for Monday at 9 am?\" In response, the agent will";
        String agentPar31="provide an answer based on its knowledge and understanding.";
        createTextAreas(agentPar3, 30, 150, 720, 155, new Text(),16);
        createTextAreas(agentPar31, 30, 165, 720, 155, new Text(),16);

        //Title
        createTextAreas("Define New Skill", 30, 230, 720, 155, new Text(),25,Color.rgb(159,182,189));
        //Content
        agentPar1 = "To define a new skill in the Skill Editor window, you can follow these steps:";
        agentPar12 = "Navigate to the Skill Editor window and locate the option to define a new skill. Within the window, you will find the necessary ";
        agentPar11 = "fields to complete the skill definition process. Start by defining a question and submitting it. Then, proceed to define the";
        String agentPar13= "required slots and submit them. Finally, define the desired action associated with the skill and submit it. ";
        createTextAreas(agentPar1, 30, 255, 720, 155, new Text(),16);
        createTextAreas(agentPar12, 30, 285, 720, 155, new Text(),16);
        createTextAreas(agentPar11, 30, 300, 720, 155, new Text(),16);
        createTextAreas(agentPar13, 30, 315, 720, 155, new Text(),16);
        createTextAreas("Here is a short example, to define  a skill:  ", 30, 345, 720, 155, new Text(),16);
        createTextAreas("question section: Which lectures are there on <DAY> at <TIME>?", 70, 375, 720, 155, new Text(),16);
        createTextAreas("Slot section: question section: <DAY> Monday, <TIME> 9  ", 70, 390, 720, 155, new Text(),16);
        createTextAreas("Action section: <DAY> Monday <TIME> 9 There are no lectures on Saturday at 9", 70, 405, 720, 155, new Text(),16);
        createTextAreas("It is important to surround the keywords with brackets", 30, 435, 720, 155, new Text(),16);

        //Title
        createTextAreas("Access Existing Skills", 30, 500, 720, 155, new Text(),25,Color.rgb(159,182,189));
        //Content
        agentPar1 = "Navigate to the Skill Editor window and locate the \"Display Skills\" button. Clicking this button will take you to a new window";
        agentPar11="displaying a list of all available skills. From the list, choose the desired skill. Upon selecting the skill, a window will appear with ";
        agentPar12="a table. In this table, you can add new actions and define new slots according to your requirements. Remember to save your";
        agentPar2="changes by clicking the \"Save\" button.";
        agentPar21="By following these steps, you can easily access and work with existing skills in the Skill Editor window, allowing you to";
        agentPar3="add/delete actions, define new slots, and save your modifications effectively.";
        createTextAreas("Here is a short example, to define  a skill:  ", 30, 525, 720, 155, new Text(),16);
        createTextAreas(agentPar1, 30, 555, 720, 155, new Text(),16);
        createTextAreas(agentPar11, 30, 570, 720, 155, new Text(),16);
        createTextAreas(agentPar12, 30, 585, 720, 155, new Text(),16);
        createTextAreas(agentPar2, 30, 600, 720, 155, new Text(),16);
        createTextAreas(agentPar21, 30, 630, 720, 155, new Text(),16);
        createTextAreas(agentPar3, 30, 645, 720, 155, new Text(),16);
    }

}
