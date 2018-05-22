import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PauseGame {
    private long startTime ;
    private long currentTime ;
    private Group root = new Group();
    private Scene scene = new Scene(root , 1100 , 600);
    private Stage stage;
    private int awayGoal ;
    private int homeGoal ;
    private Game game ;
    private Menu menu;
    PauseGame (Game game , Menu menu , Stage stage , long startTime ,long currentTime, int homeGoal , int awayGoal , Timeline timeline){
        timeline.stop();
        this.game = game;
        this.homeGoal = homeGoal;
        this.awayGoal = awayGoal;
        this.stage = stage;
        this.menu = menu;
        this.startTime = startTime ;
        this.currentTime = currentTime;
        createScene();
        stage.setScene(scene);

    }

    private void createScene (){
        scene.setFill(Color.valueOf("#95CCDD"));
        circles();
        Label homeText = createScoreBoard(true);
        Label awayText =createScoreBoard(false);
        homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
        awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
        Label time = timelabel();
        time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));
        root.getChildren().add(time);
        quitButton();
        saveButton();
        resumeButton();


    }

    private Label createScoreBoard (boolean isHome){
        if(isHome){
            Label homeText = new Label();
            homeText.setText(this.game.getPlayer1().getName()+" : "+homeGoal);
            homeText.setTextFill(Color.WHITE);
            homeText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 40));
            homeText.setStyle("  ;-fx-pref-width: 300;-fx-alignment: center");
            homeText.relocate(scene.getWidth()/4 -  150 , 300);
            root.getChildren().add(homeText);
            return homeText;
        }
        else {
            Label awayText = new Label();
            awayText.setText(this.game.getPlayer2().getName()+" : "+awayGoal);
            awayText.setTextFill(Color.WHITE);
            awayText.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 40));
            awayText.setStyle(" ;-fx-pref-width: 300;-fx-alignment: center ; ");
            awayText.relocate(scene.getWidth()/4*3 -  150 , 300);
            root.getChildren().add(awayText);
            return awayText;
        }
    }

    public Scene getScene() {
        return scene;
    }
    private Label timelabel (){
        Label time = new Label();
        time.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 50));
        time.setTextFill(Color.WHITE);
        time.setStyle("-fx-pref-width: 300;-fx-alignment: center");
        time.relocate(scene.getWidth()/2 -150, 100);
        return time;

    }

    private void resumeButton (){
        Button resume = new Button("resume");
        resume.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 20));
        resume.setStyle(" -fx-pref-width: 160; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        resume.relocate(scene.getWidth()/2 - 80 , scene.getHeight()/2-40 );
        root.getChildren().add(resume);
        //resume.setOnMouseClicked(event -> stage.setScene(new GameScene( game ,  menu ,  stage ,  currentTime - startTime ,  homeGoal ,  awayGoal).scene));
    }

    private void saveButton (){
        Button save = new Button("save");
        save.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 20));
        save.setStyle(" -fx-pref-width: 160; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        save.relocate(scene.getWidth()/2 - 80 , scene.getHeight()/2+20 );
        root.getChildren().add(save);
        save.setOnMouseClicked(event -> {

        });
    }
    private void quitButton (){
        Button quit = new Button("Quit");
        quit.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 20));
        quit.setStyle(" -fx-pref-width: 160; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #DD2B2B,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        quit.relocate(scene.getWidth()/2 - 80 , scene.getHeight()/2 +80);
        root.getChildren().add(quit);
        quit.setOnMouseClicked(event -> stage.setScene(menu.getMenuScene()));
    }

    private void circles (){
        Rectangle one = new Rectangle();
        one.setX(3);
        one.setWidth(1096);
        one.setY(180);
        one.setHeight(300);
        one.setArcWidth(20);
        one.setArcHeight(20);
        one.setFill(Color.valueOf("#142A56"));
        root.getChildren().add(one);

        Rectangle two = new Rectangle();
        two.setX(scene.getWidth()/2 -150);
        two.setWidth(300);
        two.setY(100);
        two.setHeight(60);
        two.setArcWidth(20);
        two.setArcHeight(20);
        two.setFill(Color.valueOf("#142A56"));
        root.getChildren().add(two);


    }


}
