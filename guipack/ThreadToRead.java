package guipack;

/**
 * Created by Yunicoed on 10.05.2017.
 */
public class ThreadToRead extends Thread {
    ClientToServer client;

    public ThreadToRead(ClientToServer client){
        this.client = client;
    }

    @Override
    public void run(){
        client.listenToServer();
    }
}
