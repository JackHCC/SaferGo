/**
 * 打开地图页面的onCreate()调用getLocation()
 * 将定位信息发送到服务器
 * new LocationToServer(Location location);
 * sendLocation();
 */
package xin.skingorz.isafety;

import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.emitter.Emitter;

public class LocationToServer {
    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    private Gson gson = new Gson();

    /**
     * 构造函数
     * @param location 定位信息
     */
    public LocationToServer(Location location) {
        this.location = location;
    }


    /**
     * 将定位信息发送到服务器
     *
     * @throws URISyntaxException
     */
    public void sendLocation() {
        GlobalVariable.mSocket.emit("location", gson.toJson(this.location));
    }

    /**
     * 获取定位信息
     *
     * @return 获取到的地址信息
     */
    public Location getLocation() {
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
}
