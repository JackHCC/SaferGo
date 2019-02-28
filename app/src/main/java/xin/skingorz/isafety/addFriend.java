package xin.skingorz.isafety;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class addFriend implements Callable {

    private String selfemail;
    private String friendemail;
    private Gson gson = new Gson();
    URL httpUrl = null;
    HttpURLConnection conn = null;
    BufferedReader reader = null;
    JSONObject jsonObject = null;

    public addFriend(String selfemail, String friendemail){
        this.selfemail = selfemail;
        this.friendemail = friendemail;
    }

    @Override
    public returnMsg call() throws Exception {
        httpUrl = new URL(GlobalVariable.ip+":3001/addfriend/add_user?selfemail="+this.selfemail+"&friendmail="+this.friendemail);
        conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(5000);
        String str;
        StringBuffer sb = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        try {
            jsonObject = new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int status = jsonObject.getInt("status");
        String msg = jsonObject.getString("msg");

        return new returnMsg(status, msg);
    }
}
