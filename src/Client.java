
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Receive  {
    private Stage stage;
    private static Client instance = new Client();
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private GameScene gameScene;
    private Menu menu ;
    private Client(){}


    public static Client getInstance() {
        return instance;
    }

    public void start(Menu menu , Stage stage){
        try {
            this.stage = stage;
            this.menu = menu;

            connect2Server();
            initIOStreams();
            gameScene = new GameScene(new Game(new Player("1") , new Player("2")," ") ,  menu ,  stage , false);
            startThreads();
            stage.setScene(gameScene.scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void connect2Server() throws IOException {
        clientSocket = new Socket("localhost", 900);

    }
    private void startThreads() {
        new Thread(new ExchangeData(inputStream, this)).start();
    }

    public void sendData (SavedData savedData){
        try {
            outputStream.writeObject(savedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void receive(SavedData savedData) {
        gameScene.set(savedData);
        //gameScene.updateClientMap(savedData);
    }
}
