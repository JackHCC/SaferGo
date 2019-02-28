/**
 * 登录进页面时调用GlobalVariable.mSocket.emit("email", email);
 * 注销账号之后调用stopService();
 */
package xin.skingorz.isafety;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class webSocketService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new GlobalVariable();
        while (true){
            GlobalVariable.handle.getLocation();
            GlobalVariable.handle.getMessage();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("Service onDestroy");
        xin.skingorz.isafety.GlobalVariable.mSocket.disconnect();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}