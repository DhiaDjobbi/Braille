package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//voice library
import com.sun.speech.freetts.*;


public class Main extends Application {

    private static boolean HasSymbol(String password)
    {
        Pattern special = Pattern.compile ("[!@#$%&*()/_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(password);
        return  hasSpecial.find();
    }
    private boolean valid(String ch)
    {
        ch = ch.replace("\n", "").replace("\r", ""); //testing
        return (ch.matches(".*\\d.*"));
    }

    private static String converti(String ch){

        String base="⠁⠃⠉⠙⠑⠋⠛⠓⠊⠚⠅⠇⠍⠝⠕⠏⠟⠗⠎⠞⠥⠧⠺⠭⠽⠵";
        ch=ch.toLowerCase();
        ch=ch.trim();
        ch = ch.replace("\n", "            ").replace("\r", "            ");
        String  raja3="";
        int n =ch.length();
        int i;
        for (i=0;i<n;i++) {
            char c=ch.charAt(i);
            if (c==' ') {
                raja3 = raja3 + " ";
            } else {
                int ascii = c;
                int p = ascii - 97;
                raja3 = raja3 + base.charAt(p);
            }
        }           return raja3;
    }


    Stage window;
    public void TextToSpeech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(110);// Setting the rate of the voice
                voice.setPitch(90);// Setting the Pitch of the voice
                voice.setVolume(3);// Setting the volume of the voice
                voice.speak(words);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Braille Translator   |   Text Reader");

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25, 35, 25, 35));
        grid.setVgap(8);
        grid.setHgap(10);

        Label enter = new Label("Enter your text here :");
        GridPane.setConstraints(enter, 0, 0);
        Label name = new Label("Credits: Dhia Djobbi");
        GridPane.setConstraints(name, 1, 6);
        name.getStyleClass().add("essmi");


        TextArea textArea1 = new TextArea();
        GridPane.setConstraints(textArea1, 0, 1);

        TextArea textArea2 = new TextArea();
        GridPane.setConstraints(textArea2, 0, 5);


        Button convert = new Button("Convert");
        convert.setOnAction(e ->
        {
            String  ch=textArea1.getText();

            if(HasSymbol(ch))
            {AlertBox.display("Error ☹️", "You can't convert Symbols [!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]  ");}

            else  if (valid(ch))
            {AlertBox.display("Error ☹️", "You can't convert numbers [0,1,2,3,4,5,6,7,8,9] "); }
            else
            { textArea2.setText(converti(ch)); }

        });

        GridPane.setConstraints(convert, 0, 2);
        Button Speak = new Button("Listen !");
        Speak.getStyleClass().add("button-blue");
        Speak.setOnAction(e ->TextToSpeech(textArea1.getText()));


        GridPane.setConstraints(Speak, 1, 2);


        Label resulttext = new Label("Converted text  :");
        GridPane.setConstraints(resulttext, 0, 4);


        grid.getChildren().addAll(textArea1, textArea2,convert,enter,resulttext,Speak,name);

        Scene scene = new Scene(grid, 820, 650);
        scene.getStylesheets().add("sample/123.css");
        window.setScene(scene);
        window.show();
    }

}
