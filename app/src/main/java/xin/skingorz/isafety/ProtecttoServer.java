/**
 * 将需要保护的信息发送给服务器
 */
package xin.skingorz.isafety;

import com.google.gson.Gson;

public class ProtecttoServer {
    private Protect protect;
    private Gson gson = new Gson();

    /**
     * 构造函数
     * @param protect 发起保护的信息
     */
    public ProtecttoServer(Protect protect) {
        this.protect = protect;
    }

    /**
     * 将位置信息发送给服务器
     */
    public void sendMessage(){
        GlobalVariable.mSocket.emit("protected_people",gson.toJson(protect));
    }
}
