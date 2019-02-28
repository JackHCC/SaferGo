package com.jack.isafety;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.link.isafe.JellyInterpolator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import xin.skingorz.isafety.GlobalVariable;
import xin.skingorz.isafety.Login;
import xin.skingorz.isafety.User;
import xin.skingorz.isafety.returnMsg;
import xin.skingorz.isafety.webSocketService;


public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView mBtnLogin;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private EditText mUsername, mPassword;

    private TextView mForgetCode;

    private CheckBox rem_pw, auto_login;

    private SharedPreferences sp;

    private String userNameValue,passwordValue;

    //SharedPreferences sprfMain;
    //SharedPreferences.Editor editorMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        /*  //在加载布局文件前判断是否登陆过
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        //.getBoolean("main",false)；当找不到"main"所对应的键值是默认返回false
        if(sprfMain.getBoolean("main",false)){
        Intent intent=new Intent(LoginActivity.this,MainTabActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();        }
        */

        //跳转注册页面
        TextView mLogin_Register = findViewById(R.id.mlogin_register);
        mLogin_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //返回注册
        ImageView mLogin_Back = findViewById(R.id.mlogin_back);
        mLogin_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });

        //忘记密码
        mForgetCode=findViewById(R.id.main_btn_forget);
        mForgetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,Forget1Activity.class);
                startActivity(intent);
            }
        });

        initView();
        stopAnim();

        //记住密码，自动登录
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        rem_pw = (CheckBox) findViewById(R.id.cb_mima);
        auto_login = (CheckBox) findViewById(R.id.cb_auto);

        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            mUsername.setText(sp.getString("USER_NAME", ""));
            mPassword.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(false);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();

            }
        }


        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });




    }


    /*登录动画相关*/
    void startAnim() {
        AVLoadingIndicatorView mavi = findViewById(R.id.avi);
        mavi.show();
        // or avi.smoothToShow();
    }

    void stopAnim() {
        AVLoadingIndicatorView mavi = findViewById(R.id.avi);
        mavi.hide();
        // or avi.smoothToHide();
    }

    /*初始化相关部件*/
    private void initView() {
        mBtnLogin = findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        mName =  findViewById(R.id.input_layout_name);
        mPsw =  findViewById(R.id.input_layout_psw);

        //mBtnLogin.setOnClickListener(this);
        //获取
        mUsername = findViewById(R.id.input_layout_name_edit);
        mPassword = findViewById(R.id.input_layout_psw_edit);

        //登陆点击
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            //String username=mUsername.getText().toString();
            //String password=mPassword.getText().toString();


            @Override
            public void onClick(View v) {

                //Log.i("usertext",mUsername.getText().toString());
                Callable<returnMsg> callable = new Login(mUsername.getText().toString(), mPassword.getText().toString());
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

                    // 计算出控件的高与宽
                    mWidth = mBtnLogin.getMeasuredWidth();
                    mHeight = mBtnLogin.getMeasuredHeight();

                    // 隐藏输入框
                    mName.setVisibility(View.INVISIBLE);
                    mPsw.setVisibility(View.INVISIBLE);

                    //记住密码
                    userNameValue =mUsername.getText().toString();
                    passwordValue =mPassword.getText().toString();

                    Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();

                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(rem_pw.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD",passwordValue);
                        editor.commit();
                    }

                    //跳转界面
                    inputAnimator(mInputLayout, mWidth, mHeight);

                    Callable<User> callable1 = new User(mUsername.getText().toString());
                    FutureTask<User> futureTask1 = new FutureTask<User>(callable1);
                    new Thread(futureTask1).start();
                    while (!futureTask.isDone()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        User user = futureTask1.get();
                        GlobalVariable.cache.addDataMemoryCache("user",user);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //重写点击事件
    @Override
    public void onClick(View v) {

        /*// 计算出控件的高与宽
        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();
        // 隐藏输入框
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);*/

    }

    /**
     * 输入框的动画效果
     *
     * @param view 控件
     * @param w    宽
     * @param h    高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /**
                         *要执行的操作
                         */

                        //跳转到主界面
                        Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);

                        //下次登录不跳转
                        /*editorMain.putBoolean("main",true);
                        editorMain.commit();*/
                        startActivity(intent);

                        /*//监听点击
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();*/
                    }
                };

                new Thread(){
                    @Override
                    public void run(){
                        Intent start = new Intent(LoginActivity.this, webSocketService.class);
                        startService(start);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 2000);//3秒后执行TimeTask的run方法

                //销毁页面
                TimerTask task1 = new TimerTask() {

                    @Override

                    public void run() {
                        LoginActivity.this.finish();
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(task1, 2000);//3秒后执行TimeTask的run方法

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });

    }

    /**
     * 出现进度动画
     *
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

        startAnim();

    }
}
