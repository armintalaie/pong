import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExchangeData implements Runnable {

    private ObjectInputStream objectInputStream;
    private Receive receive;
    ExchangeData(ObjectInputStream objectInputStream , Receive receive){
        this.objectInputStream = objectInputStream;
        this.receive = receive;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(objectInputStream != null){
                GameScene gameScene = (GameScene) objectInputStream.readObject();
                receive.receive(gameScene);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
