package guipack;

/**
 * Created by Yunicoed on 10.05.2017.
 */
public class ThreadToWrite extends Thread {

    String sms;
    ClientToServer client;

    public ThreadToWrite(ClientToServer client, String sms){
        this.client = client;
        this.sms = sms;
    }

    @Override
    public void run(){
        client.writeToServer(sms);
    }
}
