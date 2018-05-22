import javafx.application.Application;
import javafx.stage.Stage;

public class runc extends Application {
    @Override
    public void start(Stage stage )throws Exception  {
        Menu menu = new Menu(stage , 1);
        stage.setScene(menu.getMenuScene());
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
