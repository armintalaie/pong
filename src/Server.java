import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server  implements Receive {

    private Stage stage ;
    private static Server instance = new Server(900);
    private int port ;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private Menu menu;
    public static Server getInstance() {
        return instance;
    }
    public void start (){
        try {
            setup();
            waitForClient();
            initIOStreams();
            startThreads();
            stage.setScene(new GameScene (new Game(new Player("server") ,new Player("client") , " ")  ,  menu ,  stage ).scene);
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

    public void receive(GameScene gameScene) {
        stage.setScene(new GameScene ( new Player("server") ,new Player("client") ,  menu ,  stage , gameScene.getHomeGoal() , gameScene.getAwayGoal(),gameScene.getHome() , gameScene.getAway()).scene);
    }
    public void sendData (GameScene gameScene){
        try {
            outputStream.writeObject(gameScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

