package xin.skingorz.isafety;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class User implements Callable {
    private int id;
    private String username;
    private String password;
    private String age;
    private String sex;
    private String email;
    private String created_at;
    private String updated_at;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }


    public User getUserFromCache() {
        return (User) GlobalVariable.cache.getDataFromMemotyCache("user");
    }

    @Override
    public User call() throws Exception {
        User user;
        Gson gson = new Gson();
        URL httpUrl = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String url = GlobalVariable.ip + ":3001/user?email_username=" + this.email;
        httpUrl = new URL(url);
        conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        user = gson.fromJson(sb.toString(), User.class);
        return user;
    }
}
