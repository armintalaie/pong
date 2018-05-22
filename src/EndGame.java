import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class EndGame {
    private Menu menu = null;
    private Player won = null;
    private Player lost = null;
    private Game game = null;
    private Stage stage = null;
    private Group root = new Group();
    private Scene endGame = new Scene(root , 1100,600 , Color.color(0.7 , 0.2 , 0.9 , 1));

    EndGame (Stage stage , Game game , Player won , Player lost , Menu menu , Timeline timeline){
        timeline.stop();
        this.menu = menu;
        this.game = game;
        this.stage = stage;
        this.won = won;
        this.lost = lost;
        this.won.addGamesPlayed();
        this.won.addScore();
        this.lost.addGamesPlayed();
        this.createEndGame();

    }
    private void createEndGame (){
        endGame.setFill(Color.valueOf("#ff9966"));
        root.setStyle("-fx-background-color: #ff9966");
        Text outcome = new Text(won.getName().toUpperCase()+" WON\n");
        outcome.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 50));
        outcome.setY(endGame.getHeight()/2 );
        outcome.setX(endGame.getWidth()/2 - 50);
        root.getChildren().add(outcome);
/////////////////////////////////////////////////////////////
        Button playAgain = new Button("play again");
        playAgain.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        playAgain.setStyle("/*-fx-border-color: #000000;*/ -fx-background-color: #ffcc99 ;-fx-pref-width: 120;");
        playAgain.setLayoutX(endGame.getWidth()/2 - playAgain.getScaleX()/2 -30);
        playAgain.setLayoutY(endGame.getHeight()/2 - playAgain.getScaleY()/2+50);
      //  playAgain.setOnMouseClicked(event ->  this.stage.setScene(new GameScene( game ,menu, stage ,  ).scene));
        root.getChildren().add(playAgain);
////////////////////////////////////////////////////////////////
        Button goToMenu = new Button("menu");
        goToMenu.setFont(Font.font("arial" ,  FontWeight.EXTRA_BOLD , FontPosture.REGULAR , 15));
        goToMenu.setStyle("/*-fx-border-color: #000000;*/ -fx-background-color: #ffcc99 ;-fx-pref-width: 120;");
        goToMenu.setLayoutX(playAgain.getLayoutX() );
        goToMenu.setLayoutY(playAgain.getLayoutY()+50);
        goToMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                menu.getGames().remove(menu.findGame(game.getName())) ;
                stage.setScene(menu.getMenuScene());
            }
        });
        root.getChildren().add(goToMenu);
////////////////////////////////////////////////////////////////
    }
    public Scene getEndGame() {
        return endGame;
    }
}
