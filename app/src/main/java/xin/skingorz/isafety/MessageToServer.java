/**
 * 将聊天信息发送给服务器
 * new MessageToServer(Message).sendMessage();
 */
package xin.skingorz.isafety;

import com.google.gson.Gson;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MessageToServer {
    private Message message;

    private Socket mSocket;

    private Gson gson = new Gson();

    /**
     * 构造函数
     * @param message 聊天信息
     */
    public MessageToServer(Message message){
        this.message = message;
        this.mSocket = GlobalVariable.mSocket;
    }


    public MessageToServer(){

    }


    /**
     * 发送信息
     */
    public void sendMessage(){
        mSocket.emit("private_chat",message.getFromEmail(),message.getTo(),message.getMessage());
    }

    /**
     * 接收到信息
     * @return  接收到的信息
     */
    public Message getMessage(){
        final Message[] message = new Message[1];
        GlobalVariable.mSocket.on("private_chat", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                message[0] = gson.fromJson(String.valueOf(args),Message.class);
            }
        });
        return message[0];
    }


}