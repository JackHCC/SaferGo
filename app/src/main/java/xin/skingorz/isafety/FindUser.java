package xin.skingorz.isafety;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class FindUser implements Callable {

    private User user;
    private Gson gson = new Gson();
    URL httpUrl = null;
    HttpURLConnection conn = null;
    BufferedReader reader = null;
    JSONObject jsonObject = null;

    String email_username;

    public FindUser(String email_username){
        this.email_username = email_username;
    }

    @Override
    public User call() throws Exception {
        httpUrl = new URL(GlobalVariable.ip+":3001/addfriend/find_user?email_username="+this.email_username);
        conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        String str;
        StringBuffer sb = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        Log.i("searchLog", sb.toString());
        return gson.fromJson(sb.toString(),User.class);
    }
}
