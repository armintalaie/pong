
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client  implements Receive{
    private Stage stage;
    private static Client instance = new Client();
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private Menu menu ;
    private Client(){}


    public static Client getInstance() {
        return instance;
    }

    public void start(){
        try {
            connect2Server();
            initIOStreams();
            startThreads();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startThreads() {
        new Thread(new ExchangeData(inputStream, this)).start();
    }

    private void connect2Server() throws IOException {
        clientSocket = new Socket("localhost", 900);

    }
    public void receive (GameScene gameScene){
        stage.setScene(new GameScene(new Player(" ")  ,new Player(" ")  ,  menu ,  stage , gameScene.getHomeGoal(),gameScene.getAwayGoal() , gameScene.circleX ,gameScene.circleY ,gameScene.circleRadius, gameScene.getHome() ,gameScene.getAway()).scene);

    }

    public void sendData (GameScene gameScene){
        try {
            outputStream.writeObject(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
