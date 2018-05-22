import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements  Serializable {
    private Stage stage ;
    private Group root = new Group();
    private Scene menuScene = new Scene(root , 1100 , 600);
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();
    Menu(Stage stage , int server){
        this.stage = stage;
        menuScene.setFill(Color.valueOf("#95CCDD"));
        newGame();
        leaderBoardButton();
        quitButton();
        startLabel();
        line();
        multiPlayerButton(server);
        newPlayerButton();

    }

    private void multiPlayerButton(int server) {
        Button multiPlayer = new Button("multiplayer");
        multiPlayer.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        multiPlayer.setStyle(" -fx-pref-width: 120; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #dd661c,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        multiPlayer.relocate(menuScene.getWidth()/2 - 50 , menuScene.getHeight()/2 +100);
        root.getChildren().add(multiPlayer);
        multiPlayer.setOnMouseClicked(event ->{
            if(server == 0){
                Server.getInstance().setMenu(this);
                Server.getInstance().setStage(this.stage);
                Server.getInstance().start();
            }
            else{
                Client.getInstance().setMenu(this);
                Client.getInstance().setStage(this.stage);
                Client.getInstance().start();
            }
        });
    }

    public Scene getMenuScene() {
        return menuScene;
    }

    private void createPlayer(){
        TextField nameInput = new TextField();
        nameInput.setLayoutX(menuScene.getWidth()/2 - nameInput.getScaleX() - 70);
        nameInput.setLayoutY(menuScene.getHeight()-30 - nameInput.getScaleX()/2);
        nameInput.setStyle(" -fx-background-color: #f5f5dc ;");
        root.getChildren().add(nameInput);
        menuScene.setOnKeyPressed(event -> {
            if(event.getCode().toString().matches("ENTER"))
                if(existingName(nameInput.getText()))
                    usedName ();
                else
                    addedPlayer(nameInput);
        });

    }
    private void usedName (){
        Label text2 = new Label("name already exists ; choose another one");
        text2.relocate(menuScene.getWidth()/2 + 180 , 40 );
        text2.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        text2.setStyle("-fx-alignment: center; -fx-pref-height:30 ;-fx-pref-width: 350;  -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 2;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");

        root.getChildren().add(text2);
    }

    private void addedPlayer (TextField nameInput){
        players.add(new Player(nameInput.getText()));
        Label text = new Label("player with name : "+"\""+nameInput.getText()+"\""+" has been added");
        text.relocate(menuScene.getWidth()/2 + 180 , 40 );
        text.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        text.setStyle(" -fx-alignment: center;-fx-pref-height:30 ;-fx-pref-width: 350;  -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 2;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");

        root.getChildren().add(text);
    }

    private boolean existingName (String name){
        for(Player player : players)
            if(player.getName().matches(name))
                return true;
        return false;
    }

    private void newGame(){
        Button newGame = new Button("new game");
        newGame.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        newGame.setStyle(" -fx-pref-width: 120; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        newGame.relocate(menuScene.getWidth()/2 - 50 , menuScene.getHeight()/2-50);
        root.getChildren().add(newGame);
        newGame.setOnMouseClicked(event -> createGamePanel());

    }
    private void leaderBoardButton (){
        Button leaderboard = new Button("leaderboard");
        leaderboard.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        leaderboard.setStyle(" -fx-pref-width: 120; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        leaderboard.relocate(menuScene.getWidth()/2 - 50 , menuScene.getHeight()/2+50 );
        root.getChildren().add(leaderboard);
        leaderboard.setOnMouseClicked(event -> showPlayers());
    }

    private void showPlayers (){
        sortPlayers();
        for(int index = 0 , x = 100 , y = 40 ; index < players.size() ; index++ , y+=40){
            Label number = new Label(Integer.toString(index+1)+"" );
            number.relocate(x , y);
            leaderboardlabel(number);
            Label leaderBoard = new Label(players.get(index).getName()+"" );
            leaderBoard.relocate(x +30 , y);
            leaderBoardName(leaderBoard);
            Label leaderBoardScore = new Label( " "+Integer.toString(players.get(index).getScore()));
            leaderBoardScore.relocate(x + 130 , y);
            leaderBoardScore(leaderBoardScore);
            root.getChildren().add(number);
            root.getChildren().add(leaderBoardScore);
            root.getChildren().add(leaderBoard);
        }
    }

    private void leaderboardlabel (Label number){
        number.setFont(Font.font("arial" ,  FontWeight.BOLD , FontPosture.REGULAR , 15));
        number.setStyle(" -fx-pref-height: 30 ;-fx-pref-width: 20; -fx-arc-height: 0; -fx-arc-width: 0;-fx-alignment: center;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 2;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
    }
    private void leaderBoardScore (Label leaderBoardScore){
        leaderBoardScore.setFont(Font.font("arial" ,  FontWeight.BOLD , FontPosture.REGULAR , 15));
        leaderBoardScore.setStyle(" -fx-pref-height: 30 ;-fx-pref-width: 20; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 2;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
    }
    private void leaderBoardName (Label leaderBoard){
        leaderBoard.setFont(Font.font("arial" ,  FontWeight.BOLD , FontPosture.REGULAR , 15));
        leaderBoard.setStyle(" -fx-pref-height: 30 ;-fx-pref-width: 100; -fx-arc-height: 0; -fx-arc-width: 0; -fx-alignment: center;-fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 2;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");

    }
    private void sortPlayers (){
        for (int index = 0 ; index < players.size() - 1 ; index++){
            if(players.get(index).getScore() <= players.get(index+1).getScore() ){
                if(players.get(index).getScore() < players.get(index+1).getScore()){
                    Player p = new Player(players.get(index).getName());
                    p.setGamesPlayed(players.get(index).getGamesPlayed());
                    p.setScore(players.get(index).getScore());
                    players.set(index , players.get(index+1)) ;
                    players.set(index+1 , p);
                    if(index != 0)
                    index-= 2;
                    continue;
                }
                if(players.get(index).getScore() == players.get(index+1).getScore()){
                    if(players.get(index).getGamesPlayed() < players.get(index+1).getGamesPlayed()){
                        if(index != 0)
                        index-=2;
                        Player p = new Player(players.get(index).getName());
                        p.setGamesPlayed(players.get(index).getGamesPlayed());
                        p.setScore(players.get(index).getScore());
                        players.set(index , players.get(index+1)) ;
                        players.set(index+1 , p);
                    }
                }
            }

        }
    }
    private void createGamePanel() {
        TextField nameInput = new TextField();
        nameInput.setLayoutX(menuScene.getWidth()/2 - nameInput.getScaleX() - 70);
        nameInput.setLayoutY(menuScene.getHeight()-30 - nameInput.getScaleX()/2);
        nameInput.setStyle(" -fx-background-color: #f5f5dc ;");
        root.getChildren().add(nameInput);
        menuScene.setOnKeyPressed(event -> {
            if(event.getCode().toString().matches("ENTER")){
                if(nameInput.getText().matches("(.*) (.*) (.*)")){
                        System.out.println("here");
                        if(createGame(nameInput.getText()))
                            goToGame(nameInput.getText().split(" ")[0]);
                     }
            }
        });
    }

    private boolean createGame(String text) {
        String gameName =text.split(" ")[0];
        String player1 =text.split(" ")[1];
        String player2 =text.split(" ")[2];
        if(existingName(player1) && existingName(player2) && !existingGame(gameName)){
            System.out.println("hello");
            Game game = new Game(findPlayer(player1),findPlayer(player2),gameName);
            games.add(game);
            return true;
        }
        return false;

    }
     Game findGame (String name){
        for(Game game : games)
            if(game.getName().matches(name))
                return game;
        return null;

    }
    private boolean existingGame (String gameName){
        for(Game game : games)
            if(game.getName().matches(gameName))
                return true;
        return false;
    }
    private Player findPlayer (String name){
        for(Player player : players)
            if(player.getName().matches(name))
                return player;
        return null;

    }
    private void goToGame(String gameName){
        stage.setScene(new GameScene(findGame(gameName),this , stage).scene);
    }

    private void quitButton (){
        Button quit = new Button("Quit");
        quit.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        quit.setStyle(" -fx-pref-width: 120; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #DD2B2B,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        quit.relocate(menuScene.getWidth()/2 - 50 , menuScene.getHeight()/2 +150);
        root.getChildren().add(quit);
        quit.setOnMouseClicked(event -> stage.close());
    }
    private void startLabel (){
        Label a = new Label("PONG");
        a.setStyle("-fx-font-size: 50; -fx-alignment: center;-fx-font-weight: BOLD ; -fx-font-family: Forte;-fx-text-fill: black ");
        Image image = new Image("file:pong.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        //a.setGraphic(imageView);
        a.relocate(menuScene.getWidth()/2 - 60 , 150);
        imageView.setX(menuScene.getWidth()/2 - 50);
        imageView.setY(10);
        root.getChildren().add(imageView);
        root.getChildren().add(a);

    }

    public ArrayList<Game> getGames() {
        return games;
    }

    private void line (){
        Line line = new Line();
        line.setEndX(menuScene.getWidth()/2 -120);
        line.setStartY(0);
        line.setStartX((menuScene.getWidth()/2 -140));
        line.setEndY(menuScene.getHeight());
        root.getChildren().add(line);

        Line line2 = new Line();
        line2.setEndX(menuScene.getWidth()/2 +120);
        line2.setStartY(0);
        line2.setStartX((menuScene.getWidth()/2 +150));
        line2.setEndY(menuScene.getHeight());
        root.getChildren().add(line2);
    }

    private void newPlayerButton (){
        Button newPlayer = new Button("new player");
        newPlayer.relocate(menuScene.getWidth()/2 - 50 , menuScene.getHeight()/2 );
        newPlayer.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        newPlayer.setStyle(" -fx-pref-width: 120; -fx-arc-height: 1; -fx-arc-width: 100;    -fx-background-color: \n" +
                "        #c3c4c4,\n" +
                "        linear-gradient(#d6d6d6 50%, white 100%),\n" +
                "        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\n" +
                "    -fx-background-radius: 30;\n" +
                "    -fx-background-insets: 0,1,1;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropShadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );\n");
        root.getChildren().add(newPlayer);
        newPlayer.setOnMouseClicked(event -> createPlayer());
    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Player implements Serializable{
    private String name;
    private int score = 0;
    private int gamesPlayed = 0;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addScore() {
        this.score++;
    }
    public void addGamesPlayed() {
        this.gamesPlayed++;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    Player(String name){
        this.name = name;

        }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Game implements Serializable{
    private String name ;
    private Player player1;
    private Player player2;

    public String getName() {
        return name;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    Game (Player player1 , Player player2 , String name) {
        this.player1 = player1;
        this.player2 = player2;
        this.name = name;

    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////