import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Receive  {

    private Stage stage ;
    private static Server instance = new Server(900);
    private int port ;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private GameScene gameScene ;
    private Menu menu;
    public static Server getInstance() {
        return instance;
    }
    public void start (Menu menu , Stage stage){
        try {
            this.stage = stage;
            this.menu = menu;
            setup();
            waitForClient();
            initIOStreams();
            gameScene = new GameScene(new Game(new Player("1") , new Player("2")," ") ,  menu ,  stage , true);
            startThreads();
            stage.setScene(gameScene.scene);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setup() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void initIOStreams() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void startThreads() {
        new Thread(new ExchangeData(inputStream, this)).start();
    }

    private void waitForClient() throws IOException {
        System.out.println("waiting");
        clientSocket = serverSocket.accept();
    }
    private Server (int port){
        this.port = port;
    }

    public void sendData (SavedData savedData){
        try {
            outputStream.writeObject(savedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receive(SavedData savedData) {
        gameScene.set(savedData);
        //gameScene.updateServerMap(savedData);
    }
}

