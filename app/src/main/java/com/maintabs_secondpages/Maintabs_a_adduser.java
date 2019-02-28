package com.maintabs_secondpages;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jack.isafety.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import xin.skingorz.isafety.FindUser;
import xin.skingorz.isafety.GlobalVariable;
import xin.skingorz.isafety.User;
import xin.skingorz.isafety.addFriend;
import xin.skingorz.isafety.returnMsg;
import xin.skingorz.isafety.sendCode;

public class Maintabs_a_adduser extends AppCompatActivity {

    private EditText mSearchView;

    private User findUser;

    private TextView mSearchName;

    private  Button mAdduserBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintabs_a_adduser);


        mSearchView= findViewById(R.id.searchView);
        mSearchName=findViewById(R.id.adduser_name);

        mAdduserBtn=findViewById(R.id.adduser_btn);

        mAdduserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Callable<User> callable = new FindUser(mSearchView.getText().toString());

                FutureTask<User> futureTask = new FutureTask<User>(callable);
                new Thread(futureTask).start();
                while (!futureTask.isDone()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    findUser = futureTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(findUser.getUsername()==null&&findUser.getEmail()==null){
                    mSearchName.setText("无此用户");
                }
                else{

                    mSearchName.setText(findUser.getUsername());
                }

            }
        });


        mSearchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) GlobalVariable.cache.getDataFromMemotyCache("user");
                Log.i("emails", user.getEmail());
                Callable<returnMsg> callable = new addFriend(user.getEmail(), findUser.getEmail());
                FutureTask<returnMsg> futureTask = new FutureTask<returnMsg>(callable);
                new Thread(futureTask).start();
                while (!futureTask.isDone()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    returnMsg returnMsg = futureTask.get();
                    Log.i("returnMsg", returnMsg.getMsg());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                onBackPressed();
            }
        });

        //String mFindUsername=findUser.getUsername().toString();

        /*mSearchName.setText(mSearchContent);*/

      /*  lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Maintabs_a_adduser.this, "dianji", Toast.LENGTH_SHORT).show();
            }
        });*/

        //final String mSearchContent=mSearchView.getQuery().toString();

        // 设置搜索文本监听
       /* mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {


               // Callable<User> callable = new FindUser(mSearchContent);

                //mSearchName.setText(findUser.getUsername().toString());

                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {


                if (!TextUtils.isEmpty(newText)){
                    lListView.setFilterText(newText);
                }else{
                    lListView.clearTextFilter();
                }
                return false;
            }
        });
*/

    }
}
