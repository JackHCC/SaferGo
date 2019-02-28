/**
 * 修改密码
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

public class modify_pwd implements Callable {
    private static final String url = GlobalVariable.ip + ":3001/";      //服务器地址
    String email, password1, password2;

    /**
     * 构造函数
     *
     * @param email     邮箱地址
     * @param password1 密码
     * @param password2 确认密码
     * @throws NoSuchAlgorithmException
     */
    public modify_pwd(String email, String password1, String password2) throws NoSuchAlgorithmException {
        this.email = email;
        this.password1 = sha1(password1);
        this.password2 = sha1(password2);
    }

    public returnMsg sendModify_pwd() throws IOException, JSONException {
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        int status;
        String msg;
        JSONObject jsonObject;
        try {
            httpUrl = new URL(url + "findpwd/modify_pwd"
                    + "?email=" + this.email
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
        return this.sendModify_pwd();
    }
}
