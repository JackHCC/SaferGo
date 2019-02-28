/**
 * 检查修改密码的邮箱验证码
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

public class checkFindPwdCode implements Callable {

    private static final String url =  GlobalVariable.ip+":3001";      //服务器地址
    private String email, code;

    /**
     * 构造函数
     * @param email     邮箱地址
     * @param code      验证码
     */
    public checkFindPwdCode(String email, String code) {
        this.email = email;
        this.code = code;
    }

    /**
     * 将验证码和邮箱发送给服务器
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public returnMsg checkCode() throws JSONException, IOException {
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        int status;
        String msg;
        JSONObject jsonObject;
        try {
            httpUrl = new URL(url + "findpwd/check_code"
                    + "?email=" + this.email
                    + "&code=" + this.code);
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
        return checkCode();
    }
}
