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
                System.out.println("pppppp");
                    SavedData savedData = (SavedData) objectInputStream.readObject();
                    System.out.println(savedData.awayBatY + " "+savedData.circleX + " " + savedData.homeBatY);
                    receive.receive(savedData);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
