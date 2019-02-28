/**
 * 获取返回的状态码和对应的消息
 */
package xin.skingorz.isafety;

public class returnMsg {
    private static int status;
    private static String msg;

    public  returnMsg(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static int getStatus() {
        return status;
    }

    public static String getMsg() {
        return msg;
    }
}
