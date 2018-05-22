

import java.io.ObjectInputStream;

/**
 * Created by mahdihs76 on 5/21/18.
 */
public class GetDataRunnable implements Runnable {

    private int a = 0;
    private ObjectInputStream inputStream;
    private Server listener;
    private Client client;

    public GetDataRunnable(ObjectInputStream inputStream, Server listener) {
        a= 0;
        this.inputStream = inputStream;
        this.listener = listener;
    }
    public GetDataRunnable(ObjectInputStream inputStream, Client client) {
        a= 1;
        this.inputStream = inputStream;
        this.client = client;
    }

    @Override
    public void run() {
        while (true) {
            try {
                GameScene gameScene = (GameScene) inputStream.readObject();
                if(a==0)
                listener.receive(gameScene);
                else
                    client.receive(gameScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
