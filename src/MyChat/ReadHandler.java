package MyChat;

import java.io.DataInputStream;
import java.io.InputStream;
import static util.MyLogger.log;

public class ReadHandler implements Runnable {
    private final DataInputStream input;
    private final Client client;
    public boolean closed = false;

    public ReadHandler(InputStream input, Client client) {
        this.input = input;
        this.client = client;
    }


    @Override
    public void run() {
        try {
            while(true){
                String received = input.readUTF();
                System.out.println(received);
            }

        }catch (Exception e) {
            log(e);
        }
        finally {
            client.close();
        }
    }
    public synchronized void close(){
        if(closed){
            return;
        }
        closed = true;
    }
}
