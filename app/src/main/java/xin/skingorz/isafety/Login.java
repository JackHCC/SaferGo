/**
 * 向服务器发送登录请求
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

public class Login implements Callable {
    private static final String url = GlobalVariable.ip + ":3001/login";      //服务器地址
    private String username, passwordToHash;

    /**
     * 构造函数
     *
     * @param username 用户名
     * @param password 密  码
     */
    public Login(String username, String password) {
        this.username = username;
        try {
            this.passwordToHash = sha1(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 向服务器发送登录请求
     * @return 服务器返回的消息
     * @throws IOException
     */
    public returnMsg loginAccount() throws IOException, JSONException {
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        JSONObject jsonObject = null;
        int status;
        String msg;
        try {
            httpUrl = new URL(url + "?loginname=" + this.username + "&password=" + this.passwordToHash);
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
        try {
            jsonObject = new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("result", sb.toString());
        status = jsonObject.getInt("status");
        msg = jsonObject.getString("msg");
        if (status == 200){}
        Log.i("loginMsg",msg);
        return new returnMsg(status, msg);
    }


    @Override
    public returnMsg call() throws Exception {
        return loginAccount();
    }
}