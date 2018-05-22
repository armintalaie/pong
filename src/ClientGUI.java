import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application{
    @Override
    public void start(Stage primaryStage) {
        Menu menu = new Menu(primaryStage , 1);
        primaryStage.setScene(menu.getMenuScene() );
        primaryStage.show();
    }
}

