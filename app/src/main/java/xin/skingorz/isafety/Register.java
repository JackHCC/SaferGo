/**
 * 向服务器发送注册信息
 */
package xin.skingorz.isafety;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import static xin.skingorz.isafety.StringToHash.sha1;

public class Register implements Callable {
    private static final String url = GlobalVariable.ip + ":3001/";      //服务器地址
    private String email, code, username, password1, password2;

    /**
     * 构造函数
     *
     * @param email     邮箱地址
     * @param code      验证码
     * @param username  用户名
     * @param password1 密码
     * @param password2 确认密码
     * @throws NoSuchAlgorithmException
     */
    public Register(String email, String code, String username, String password1, String password2) throws NoSuchAlgorithmException {
        this.email = email;
        this.code = code;
        this.username = username;
        this.password1 = sha1(password1);
        this.password2 = sha1(password2);
    }

    /**
     * 向服务器发送注册信息
     *
     * @return 服务器返回的消息
     * @throws IOException 状态码对应消息
     *                     201：未填写邮箱
     *                     202：邮箱非法
     *                     203：该邮箱已被注册
     *                     204：请先获取验证码再注册
     *                     205：验证码错误
     *                     206：用户名已被注册
     *                     207,：密码不一致
     *                     211：用户名或邮箱不存在
     *                     212：密码错误
     *                     213：该邮箱未注册
     */
    public returnMsg registerAccount() throws IOException, JSONException {
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        int status;
        String msg;
        JSONObject jsonObject;
        try {
            httpUrl = new URL(url + "register/add_user"
                    + "?email=" + this.email
                    + "&code=" + this.code
                    + "&username=" + this.username
                    + "&password1=" + this.password1
                    + "&password2=" + this.password2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            conn = (HttpURLConnection) httpUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setReadTimeout(5000);
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        jsonObject = new JSONObject(sb.toString());
        Log.i("result", sb.toString());
        status = jsonObject.getInt("status");
        msg = jsonObject.getString("msg");
        return new returnMsg(status, msg);
    }

    @Override
    public returnMsg call() throws Exception {
        return this.registerAccount();
    }
}
