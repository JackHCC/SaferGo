package com.jack.isafety;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import xin.skingorz.isafety.Login;
import xin.skingorz.isafety.Register;
import xin.skingorz.isafety.returnMsg;
import xin.skingorz.isafety.sendCode;

public class RegisterActivity extends Activity {

    private EditText mEmail, mNumber, mUsername, mPassword, mPassagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        //获取
        mEmail = findViewById(R.id.register_register_email);
        mNumber = findViewById(R.id.register_register_number);
        mUsername = findViewById(R.id.register_register_username);
        mPassword = findViewById(R.id.register_register_password);
        mPassagain = findViewById(R.id.register_register_password_again);


        //返回登录页面
        ImageView mRegister_Back = findViewById(R.id.mregister_back);
        mRegister_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);*/
                onBackPressed();
            }
        });

        TextView mRegister_Login = findViewById(R.id.mregister_login);
        mRegister_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //注册按钮
        Button mRegister_prove = findViewById(R.id.register_email_prove);
        mRegister_prove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(RegisterActivity.this, "已点击", Toast.LENGTH_SHORT).show();

                Callable<returnMsg> callable = new sendCode(mEmail.getText().toString());
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
            }
        });

        //监听注册
        TextView mRegister_Register = findViewById(R.id.main_btn_register);
        mRegister_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Callable<returnMsg> callable = null;
                try {
                    callable = new Register(mEmail.getText().toString(), mNumber.getText().toString(), mUsername.getText().toString(), mPassword.getText().toString(), mPassagain.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
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

                if (returnMsg.getStatus() == 200) {

                    /*//数据传输
                    String bEmail=(findViewById(R.id.register_register_email)).getContext().toString();
                    String bUsername=(findViewById(R.id.register_register_username)).getContext().toString();
                    if(!"".equals(bEmail)&&!"".equals(bUsername))
                    {
                        Intent intent=new Intent(RegisterActivity.this,Maintabs_DActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putCharSequence("email",bEmail);
                        bundle.putCharSequence("username",bUsername);

                        intent.putExtras(bundle);


                    }else{
                        Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    }*/

                    //跳转到登录页面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(RegisterActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
