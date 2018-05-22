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

public class GameScene implements Serializable {
    private static final long SerialVersionUID = 42L ;
    transient private boolean a = false;
    transient private boolean b = false;
    transient private boolean c = false;
    transient private boolean d = false;
    private Game game ;
    private int homeGoal ;
    private int awayGoal ;
    transient private boolean down = false;
    transient private boolean right = false;
    transient private Menu menu ;
    private long startTime;
    transient private Stage stage ;
    transient private boolean isServer = false;

    private Timeline timeline = new Timeline();
    private Group root = new Group();
    transient private Circle  circle ;
     double circleX ;
     double circleY;
     double circleRadius;
    private Rectangle home ;
    private Rectangle away ;
    Scene scene = new Scene(root , 1100 , 600);
    GameScene (Player player1 , Player player2 , Menu menu , Stage stage , int homeGoal , int awayGoal , double circleX ,double circleY ,double circleRadius, Rectangle home , Rectangle away){
        this.homeGoal=homeGoal;
        this.awayGoal=awayGoal;
        root.setStyle("-fx-background-color: #95CCDD");
        scene.setFill(Color.valueOf("#95CCDD"));
        this.startTime = System.currentTimeMillis();
        this.menu = menu;
        this.game = new Game( player1 , player2 , " ");
        this.stage = stage;
        createMiddleLine();
        this.home = home;
        this.away = away;
        createCircle();
        this.circle.setRadius(circleRadius);
        this.circle.setCenterX(circleX);
        this.circle.setCenterY(circleY);
        root.getChildren().add(this.away);
        root.getChildren().add(this.home);
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        noName2 ( home ,  away ,  time ,  homeText ,  awayText);
    }


    GameScene (Player player1 , Player player2 , Menu menu , Stage stage , int homeGoal , int awayGoal , Rectangle home , Rectangle away){
        this.homeGoal=homeGoal;
        this.awayGoal=awayGoal;
        root.setStyle("-fx-background-color: #95CCDD");
        scene.setFill(Color.valueOf("#95CCDD"));
        this.startTime = System.currentTimeMillis();
        this.menu = menu;
        this.game = new Game( player1 , player2 , " ");
        this.stage = stage;
        createMiddleLine();
        this.home = home;
        this.away = away;
        root.getChildren().add(this.away);
        root.getChildren().add(this.home);
        createCircle();
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        isServer = true;
        noName ( home ,  away ,  time ,  homeText ,  awayText);
    }



    GameScene (Game game , Menu menu , Stage stage){
        homeGoal=0;
        awayGoal=0;
        root.setStyle("-fx-background-color: #95CCDD");
        scene.setFill(Color.valueOf("#95CCDD"));
        this.startTime = System.currentTimeMillis();
        this.menu = menu;
        this.game = game;
        this.stage = stage;
        createMiddleLine();
        Rectangle away = createRectangle(false);
        Rectangle home = createRectangle(true);
        root.getChildren().add(away);
        root.getChildren().add(home);
        createCircle();
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        noName ( home ,  away ,  time ,  homeText ,  awayText);
    }

    GameScene (Game game , Menu menu , Stage stage , long startTime , int homeGoal , int awayGoal){
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
        Label time = timelabel();
        root.getChildren().add(time);
        Text homeText = createScoreBoard(true);
        Text awayText =createScoreBoard(false);
        noName ( home ,  away ,  time ,  homeText ,  awayText);
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

    private void noName (Rectangle home , Rectangle away , Label time , Text homeText , Text awayText ) {


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
        KeyFrame keyFrame = new KeyFrame(Duration.millis(0), event -> {
            {
                scene.setOnKeyPressed(event1 -> {
                    if(event1.getCode().toString().equals("ESCAPE"))
                        stage.setScene(menu.getMenuScene());
                    if(event1.getCode().toString().equals("W"))
                        a=true;
                    if(event1.getCode().toString().equals("S"))
                        b=true;
                    if(event1.getCode().toString().equals("I"))
                        c=true;
                    if(event1.getCode().toString().equals("K"))
                        d=true;
                    if(event1.getCode().toString().matches("SPACE"))
                        stage.setScene(new PauseGame(game ,  menu ,  stage ,  startTime ,System.currentTimeMillis(),  homeGoal ,  awayGoal, timeline).getScene() );
                });
                scene.setOnKeyReleased(event1 -> {
                    if(event1.getCode().toString().equals("W"))
                        a=false;
                    if(event1.getCode().toString().equals("S"))
                        b=false;
                    if(event1.getCode().toString().equals("I"))
                        c=false;
                    if(event1.getCode().toString().equals("K"))
                        d=false;
                });




                homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
                awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
                time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));

                if(circle.getCenterY() == 0  ){
                    down = true;
                }
                if(circle.getCenterY() == scene.getHeight() -1){
                    down = false ;
                }
                if(circle.getCenterX() == 0  ){
                    awayGoal++;
                    if(awayGoal == 5)
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
                    if(homeGoal == 5)
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
                circleRadius = circle.getRadius();
                circleX = circle.getCenterX();
                circleY = circle.getCenterY();
                if(isServer)
                    Server.getInstance().sendData();

            }
        });
        timeline.getKeyFrames().add(keyFrame);
        KeyFrame move = new KeyFrame(Duration.millis(2) , event -> {

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
                System.out.println("batss");
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
                System.out.println("batss");
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
            Server.getInstance().sendData(this);
            timeline.stop();
        });
        timeline.getKeyFrames().add(bats);
        timeline.play();

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

    public int getHomeGoal() {
        return homeGoal;
    }

    public int getAwayGoal() {
        return awayGoal;
    }
    private  void noName2 (Rectangle home , Rectangle away , Label time , Text homeText , Text awayText){

        homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
        awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
        time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(0), event -> {
            {
                scene.setOnKeyPressed(event1 -> {
                    if(event1.getCode().toString().equals("ESCAPE"))
                        stage.setScene(menu.getMenuScene());
                    if(event1.getCode().toString().equals("W"))
                        a=true;
                    if(event1.getCode().toString().equals("S"))
                        b=true;
                    if(event1.getCode().toString().equals("K"))
                        d=true;
                    if(event1.getCode().toString().equals("I"))
                        c=true;

                    if(event1.getCode().toString().matches("SPACE"))
                        stage.setScene(new PauseGame(game ,  menu ,  stage ,  startTime ,System.currentTimeMillis(),  homeGoal ,  awayGoal, timeline).getScene() );
                });
                scene.setOnKeyReleased(event1 -> {
                    if(event1.getCode().toString().equals("S"))
                        b=false;
                    if(event1.getCode().toString().equals("W"))
                        a=false;

                    if(event1.getCode().toString().equals("I"))
                        c=false;
                    if(event1.getCode().toString().equals("K"))
                        d=false;
                });

                homeText.setText(game.getPlayer1().getName()+" : "+homeGoal);
                awayText.setText(game.getPlayer2().getName()+" : "+awayGoal);
                time.setText(Long.toString((System.currentTimeMillis() - startTime) / 1000));

            }
        });
        timeline.getKeyFrames().add(keyFrame);

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
            Client.getInstance().sendData(this);
        });
        timeline.getKeyFrames().add(bats);
        timeline.play();


    }

    public Circle getCircle() {
        return circle;
    }

    public Rectangle getHome() {
        return home;
    }

    public Rectangle getAway() {
        return away;
    }
}
