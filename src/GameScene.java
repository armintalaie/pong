import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.Serializable;
import java.util.Random;

public class GameScene {
    int armin  = 0;
    private boolean a = false;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    private Game game ;
     int homeGoal ;
     int awayGoal ;
    private boolean down = false;
    private boolean right = false;
    private Menu menu ;
    private long startTime;
    private Stage stage ;
     Rectangle away;
     Rectangle home;
    private Timeline timeline = new Timeline();
    private Group root = new Group();
     Circle circle ;
    private SavedData savedData ;
    Scene scene = new Scene(root , 1100 , 600);

    GameScene (Game game , Menu menu , Stage stage , boolean isServer){
        homeGoal=0;
        awayGoal=0;
        root.setStyle("-fx-background-color: #95CCDD");
        scene.setFill(Color.valueOf("#95CCDD"));
        this.startTime = System.currentTimeMillis();
        this.menu = menu;
        this.game = game;
        this.stage = stage;
        createMiddleLine();
         away = createRectangle(false);
         home = createRectangle(true);
        root.getChildren().add(away);
        root.getChildren().add(home);
        createCircle();
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        noName (  time ,  homeText ,  awayText , isServer , savedData);
    }

    GameScene (Game game , Menu menu , Stage stage , long startTime , int homeGoal , int awayGoal, boolean isServer ){
        this.homeGoal= homeGoal;
        this.awayGoal = awayGoal;
        root.setStyle("-fx-background-color: #95CCDD");
        scene.setFill(Color.valueOf("#95CCDD"));
        System.out.println(game);
        this.startTime = System.currentTimeMillis() - startTime;
        this.menu = menu;
        this.game = game;
        this.stage = stage;
        createMiddleLine();
        Rectangle away = createRectangle(false);
        Rectangle home = createRectangle(true);
        root.getChildren().add(away);
        root.getChildren().add(home);
        createCircle();
        savedData = new SavedData(homeGoal , awayGoal  ,circle.getCenterX(),circle.getCenterY()  , home.getX() , home.getY() , away.getX() , away.getY());
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        noName (  time ,  homeText ,  awayText , isServer , savedData);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void createCircle (){
        circle = new Circle();
        circle.setCenterX(scene.getWidth()/2);
        circle.setCenterY(scene.getHeight()/2);
        circle.setRadius(12);
        circle.setFill(Color.BROWN);
        circle.setStrokeWidth(20);
        root.getChildren().add(circle);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void noName (  Label time , Text homeText , Text awayText , boolean isServer, SavedData savedData) {

        Random random = new Random();
        if( random.nextInt(200) % 2 == 1)
        down = true;
        else down =false;

        if( random.nextInt(200) % 2 == 1)
            right = true;
        else right =false;

        homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
        awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
        time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(5), event -> {
            {
                scene.setOnKeyPressed(event1 -> {
                    if(event1.getCode().toString().equals("ESCAPE"))
                        stage.setScene(menu.getMenuScene());
                    if(isServer){
                        if(event1.getCode().toString().equals("W"))
                            a=true;
                        if(event1.getCode().toString().equals("S"))
                            b=true;}
                    else{
                        if(event1.getCode().toString().equals("I"))
                            c=true;
                        if(event1.getCode().toString().equals("K"))
                            d=true;}
                    if(event1.getCode().toString().matches("SPACE"))
                        stage.setScene(new PauseGame(game ,  menu ,  stage ,  startTime ,System.currentTimeMillis(),  homeGoal ,  awayGoal, timeline).getScene() );
                });
                scene.setOnKeyReleased(event1 -> {
                    if(isServer){
                    if(event1.getCode().toString().equals("W"))
                        a=false;
                    if(event1.getCode().toString().equals("S"))
                        b=false;}
                        else{
                    if(event1.getCode().toString().equals("I"))
                        c=false;
                    if(event1.getCode().toString().equals("K"))
                        d=false;}
                });


                homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
                awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
                time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));
                if(isServer){
                if(circle.getCenterY() == 0  ){
                    down = true;
                }
                if(circle.getCenterY() == scene.getHeight() -1){
                    down = false ;
                }
                if(circle.getCenterX() == 0  ){
                    awayGoal++;
                    if(awayGoal == 500)
                        stage.setScene(new EndGame(stage , game , game.getPlayer2(), game.getPlayer1()  ,menu , timeline).getEndGame());
                    circle.setCenterX(scene.getWidth()/2);
                    circle.setCenterY(scene.getHeight()/2);
                    if( random.nextInt(200) % 2 == 1)
                        down = true;
                    else down =false;

                    if( random.nextInt(200) % 2 == 1)
                        right = true;
                    else right =false;
                }
                if(circle.getCenterX() == scene.getWidth() -1){
                    homeGoal++;
                    if(homeGoal == 500)
                        stage.setScene(new EndGame(stage , game , game.getPlayer1() , game.getPlayer2() ,menu,timeline).getEndGame());
                    circle.setCenterX(scene.getWidth()/2);
                    circle.setCenterY(scene.getHeight()/2);
                    if( random.nextInt(200) % 2 == 1)
                        down = true;
                    else down =false;

                    if( random.nextInt(200) % 2 == 1)
                        right = true;
                    else right =false;
                }
                if(circle.getCenterY()  >= home.getY() && circle.getCenterY()  <= home.getY()+home.getHeight()){
                    if(circle.getCenterX() - circle.getRadius()  <= home.getX()+home.getWidth() && circle.getCenterX() - circle.getRadius() >= home.getX()+home.getWidth() -3) {
                        down = random.nextInt(2) != 1;
                        right = true ;
                    }
                }
                if(circle.getCenterY()  >= away.getY() && circle.getCenterY()  <= away.getY()+away.getHeight()){
                    if(circle.getCenterX() + circle.getRadius()  >=  away.getX() && circle.getCenterX() + circle.getRadius()  <= away.getX()+3) {
                        down = random.nextInt(2) != 1;
                        right = false ;
                    }

                }
            }}
            if(isServer)
                Server.getInstance().sendData(new SavedData(homeGoal , awayGoal  ,circle.getCenterX(),circle.getCenterY()  , home.getX() , home.getY() ,away.getX() , away.getY()));
            else
                Client.getInstance().sendData(new SavedData(homeGoal , awayGoal  ,circle.getCenterX(),circle.getCenterY()  , home.getX() , home.getY() ,away.getX() , away.getY()));

        });
        timeline.getKeyFrames().add(keyFrame);
        KeyFrame move = new KeyFrame(Duration.millis(1) , event -> {

            if(down && right){
                circle.setCenterX(circle.getCenterX()+1+(System.currentTimeMillis() - startTime) / 100000);
                circle.setCenterY(circle.getCenterY()+1+(System.currentTimeMillis() - startTime) / 100000);
            }
            if(down && !right){
                circle.setCenterX(circle.getCenterX()-1-(System.currentTimeMillis() - startTime) / 100000);
                circle.setCenterY(circle.getCenterY()+1+(System.currentTimeMillis() - startTime) / 100000);

            }
            if(!down && right){
                circle.setCenterX(circle.getCenterX()+1+(System.currentTimeMillis() - startTime) / 100000);
                circle.setCenterY(circle.getCenterY()-1-(System.currentTimeMillis() - startTime) / 100000);
            }
            if(!down && !right){
                circle.setCenterX(circle.getCenterX()-1-(System.currentTimeMillis() - startTime) / 100000);
                circle.setCenterY(circle.getCenterY()-1-(System.currentTimeMillis() - startTime) / 100000);
            }

});
        timeline.getKeyFrames().add(move);
        KeyFrame bats = new KeyFrame(Duration.millis(1), event -> {


            if(a){

                if(!d && !c)
                    moveUP(home , scene);
                if(c){
                    moveUP(home , scene);
                    moveUP(away,scene); }
                if(d){
                    moveUP(home , scene);
                    moveDown(away , scene); }
            }
            if(b){

                if(d){
                    moveDown(home , scene);
                    moveDown(away , scene); }
                if(c){
                    moveDown(home , scene);
                    moveUP(away , scene); }

                if(!d && !c)
                    moveDown(home , scene);
            }
            if(c && !b && !a && !d)
                moveUP(away , scene);
            if(d && !b && !a && !c)
                moveDown(away , scene);
            if(isServer)
                updateServerMap(this.savedData);
            else
                updateClientMap(this.savedData);
        });
        timeline.getKeyFrames().add(bats);
        timeline.play();
    }

    public void updateServerMap(SavedData savedData ) {
        away.setX(savedData.awayBatX);
        away.setY(savedData.awayBatY);
    }
    public void updateClientMap(SavedData savedData ) {

        System.out.println(home.getY()+"\\\\\\\\"+ armin);
        System.out.println(savedData.homeBatY+"\\\\\\\\");
        home.setY(savedData.homeBatX);
        System.out.println(home.getY()+"::::::::::::"+armin);
        System.out.println(savedData.homeBatY+"::::::::::::");
        circle.setCenterX(savedData.circleX);
        circle.setCenterY(savedData.circleY);
        awayGoal = savedData.awayGoal;
        homeGoal = savedData.homeGoal;


    }
    public void set(SavedData savedData){
        this.savedData= savedData;

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void moveUP (Rectangle rectangle , Scene scene){
        PathTransition pathTransition1 = new PathTransition();
        Path path1 = new Path();
        MoveTo moveTo1 = new MoveTo();
        moveTo1.setX(rectangle.getX() + rectangle.getWidth()/2);
        moveTo1.setY(rectangle.getY() + rectangle.getHeight()/2);
        path1.getElements().add(moveTo1);
        VLineTo vLineTo= new VLineTo();
        path1.getElements().add(vLineTo);
        vLineTo.setY(moveTo1.getY() - 1);
        pathTransition1.setNode(rectangle);
        pathTransition1.setPath(path1);
        pathTransition1.play();
        rectangle.setY(rectangle.getY()-1);
        if(rectangle.getY() < -11)
            rectangle.setY(rectangle.getY() + scene.getHeight());
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void moveDown (Rectangle rectangle , Scene scene){
        PathTransition pathTransition1 = new PathTransition();
        Path path1 = new Path();
        MoveTo moveTo1 = new MoveTo();
        moveTo1.setX(rectangle.getX() + rectangle.getWidth()/2);
        moveTo1.setY(rectangle.getY() + rectangle.getHeight()/2);
        path1.getElements().add(moveTo1);
        VLineTo vLineTo= new VLineTo();
        path1.getElements().add(vLineTo);
        vLineTo.setY(moveTo1.getY() + 1);
        pathTransition1.setNode(rectangle);
        pathTransition1.setPath(path1);
        pathTransition1.play();
        rectangle.setY(rectangle.getY()+1);
        if(rectangle.getY()+rectangle.getHeight()  > scene.getHeight()+11)
            rectangle.setY(rectangle.getY() - scene.getHeight());
    }

    private Rectangle createRectangle (boolean ishome){
        if(ishome){
            Rectangle home = new Rectangle(100 , 220 ,20,110 );
            home.setFill(Color.BLACK);
            home.setArcWidth(30);
            home.setArcHeight(20);
            return home;
        }
        else{
        Rectangle away = new Rectangle(900 , 220 ,20,110 );
        away.setFill(Color.BLACK);
        away.setArcWidth(30);
        away.setArcHeight(20);
        return away;
        }
    }

     private Text createScoreBoard (boolean isHome){
        if(isHome){
            Text homeText = new Text();
            homeText.setX(scene.getWidth()/4 - 10);
            homeText.setY(25);
            homeText.setText(this.game.getPlayer1().getName()+" : "+homeGoal);
            homeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
            homeText.setFill(Color.color(0.2 , 0.3 ,0.4));
            root.getChildren().add(homeText);
            return homeText;
        }
        else {
            Text awayText = new Text();
            awayText.setX(scene.getWidth()/4*3 + 10);
            awayText.setY(25);
            awayText.setText(this.game.getPlayer2().getName()+" : "+awayGoal);
            awayText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
            awayText.setFill(Color.color(0.2 , 0.3 ,0.4));
            root.getChildren().add(awayText);
            return awayText;
        }
    }

    private void createMiddleLine (){
        Line line = new Line();
        line.setStartX(scene.getWidth()/2 );
        line.setStartY(0);
        line.setEndX(scene.getWidth()/2 );
        line.setEndY(scene.getHeight());
        line.setFill(Color.BLACK);
        root.getChildren().add(line);
    }
    private Label timelabel (){
        Label time = new Label();
        time.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        time.setStyle("-fx-border-color: #000000; -fx-background-color: #F5F5DC ;-fx-pref-width: 120;-fx-alignment: center");
        time.relocate(scene.getWidth()/2 - 60 , 0);
        return time;

    }

}

class SavedData implements Serializable {
     int homeGoal;
     int awayGoal ;
     double circleX ;
     double circleY ;
     double homeBatX ;
     double homeBatY ;
     double awayBatX ;
     double awayBatY ;

    SavedData(int homeGoal,int awayGoal ,double circleX , double circleY  ,double homeBatX , double homeBatY ,double awayBatX , double awayBatY){
        update(homeGoal , awayGoal, circleX , circleY ,homeBatX,homeBatY,awayBatX,awayBatY);
    }

    private void update( int homeGoal,int awayGoal ,double circleX ,double circleY ,double homeBatX , double homeBatY ,double awayBatX , double awayBatY){
        this.awayBatX = awayBatX ;
        this.awayBatY = awayBatY;
        this.awayGoal = awayGoal;

        this.homeGoal = homeGoal;
        this.homeBatX = homeBatX;
        this.homeBatY = homeBatY;

        this.circleX = circleX;
        this.circleY = circleY;
    }
}
