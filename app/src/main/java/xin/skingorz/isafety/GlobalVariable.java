/**
 * webSocketService
 *
 */
package xin.skingorz.isafety;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;

public class GlobalVariable {

    public static Socket mSocket;
    public static final String ip = "http://188.131.133.135";
    public static String email;
    public static final Cache cache = new Cache((int) (Runtime.getRuntime().maxMemory() / 1024)/8);
    public static ArrayList<Message> messagesArray = new ArrayList<Message>();
    public static final Handle handle = new Handle();

    public GlobalVariable(){
        try {
            mSocket = IO.socket("http://188.131.133.135:3002");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        while (true){
            new MessageToServer().getMessage();
        }
    }
}
