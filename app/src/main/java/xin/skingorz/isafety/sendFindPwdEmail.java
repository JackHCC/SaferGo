/**
 * 向服务器发送找回密码的邮箱
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
import java.util.concurrent.Callable;

public class sendFindPwdEmail implements Callable {

    private static final String url = GlobalVariable.ip + ":3001/";      //服务器地址
    private String email;

    /**
     * 构造函数
     *
     * @param email 邮箱地址
     */
    public sendFindPwdEmail(String email) {
        this.email = email;
    }

    public returnMsg sendFindEmail() throws JSONException, IOException {
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        int status;
        String msg;
        JSONObject jsonObject;
        try {
            httpUrl = new URL(url + "findpwd/get_code" + "?email=" + this.email);
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
    public Object call() throws Exception {
        return this.sendFindEmail();
    }
}
