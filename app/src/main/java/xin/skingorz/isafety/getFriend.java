package xin.skingorz.isafety;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class getFriend implements Callable {
    private String email;
    private Gson gson = new Gson();
    URL httpUrl = null;
    HttpURLConnection conn = null;
    BufferedReader reader = null;
    JSONObject jsonObject = null;

    public getFriend(){
        User user = (User) GlobalVariable.cache.getDataFromMemotyCache("user");
        this.email = user.getEmail();
    }

    @Override
    public User[] call() throws Exception {
        httpUrl = new URL(GlobalVariable.ip+":3001/addfriend/get_friends?email="+this.email);
        conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        String str;
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer sb = new StringBuffer();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        return gson.fromJson(sb.toString(),User[].class);
    }
}
