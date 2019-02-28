/**
 * sendLocation(Location location)
 * Location getLocation()
 * Message getMessage()
 * sendMessage(String ToEmail,String message)
 * sendPeople(String email)
 * sendMessage(String ToEmail,String message)
 */
package xin.skingorz.isafety;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.socket.emitter.Emitter;

public class Handle {
    Gson gson = new Gson();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * 接受位置
     * @return 接收到的位置Location location
     */
    public Location getLocation(){
        final Location[] retLocation = new Location[1];
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    GlobalVariable.mSocket.on("location", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Location location = gson.fromJson(String.valueOf(args), Location.class);
                            retLocation[0] = location;
                        }
                    });
                }
            }
        };
        return retLocation[0];
    }

    /**
     * 向服务器发送位置
     * @param location 当前获取到的位置
     */
    public void sendLocation(Location location) {
        GlobalVariable.mSocket.emit("location", gson.toJson(location));
    }

    /**
     * 接受服务器发送的聊天消息,并将消息添加到messageList中
     * @return 接收到的消息
     */
    public Message getMessage(){
        final Message[] message = new Message[1];
        GlobalVariable.mSocket.on("private_chat", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                GlobalVariable.messagesArray.add(new Message((String)args[2], (String) args[1]));
                message[0] = gson.fromJson(String.valueOf(args),Message.class);
            }
        });
        return message[0];
    }


    /**
     * 向服务器发送消息
     * @param ToEmail 消息要发送的人
     * @param message 消息内容
     */
    public void sendMessage(String ToEmail,String message){
        GlobalVariable.mSocket.emit("private_chat",GlobalVariable.email,ToEmail,message);
    }

    /**
     * 将请求保护自己的人的邮箱发送到服务器
     * @param email 请求保护自己的人的邮箱
     */
    public void sendPeople(String email){
        GlobalVariable.mSocket.emit("protected_people",email);
    }



}
