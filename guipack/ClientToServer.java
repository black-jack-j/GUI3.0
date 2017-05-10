package guipack;

import kcommands.KCommand;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientToServer {

    Socket socket = null;
    InputStream in = null;
    OutputStream out = null;
    DataInputStream dIn;
    DataOutputStream dOut;

    public void Connect(InetAddress IA, int port) {
        try {
            socket = new Socket(IA, port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            dIn = new DataInputStream(in);
            dOut = new DataOutputStream(out);
        } catch (UnknownHostException e) {
            System.out.println("Host not found");
        } catch (IOException e) {
            System.out.println("Something incorrect");
        }
    }

    synchronized public void writeToServer(String sms){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader keyboard = new BufferedReader(isr);

        String line = null;
        try {
            line = sms;
            System.out.println(line);
            dOut.writeUTF(line);
            System.out.println(",");
            dOut.flush();
        } catch (IOException e) {
            System.out.println("Something incorrect");
        }
    }

    public static String ObjectToString(Object o){
        String line = null;
        ByteArrayOutputStream bytearr = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bytearr);
            oos.writeObject(o);
            line = bytearr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void listenToServer(){
        String line = null;
        try {
            while(true){
                System.out.println(".");
                line = dIn.readUTF();
                System.out.println(".");
                System.out.println(line);
                if (line.endsWith("quit"))
                    break;
            }
        } catch (IOException e) {
            System.out.println("Something incorrect");
        }
    }
}
