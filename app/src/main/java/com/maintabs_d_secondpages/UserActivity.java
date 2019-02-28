package com.maintabs_d_secondpages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import com.jack.isafety.R;
import com.jack.specialEffects.StringUtils;
import com.jack.sqlite.DBUtils;
import com.jack.sqlite.UserBean;


import xin.skingorz.isafety.GlobalVariable;
import xin.skingorz.isafety.User;

import static com.jack.sqlite.Constants.userGender;
import static com.jack.sqlite.Constants.userHome;
import static com.jack.sqlite.Constants.userPhone;
import static com.jack.sqlite.Constants.userLocation;
import static com.jack.sqlite.Constants.userTag;

public class UserActivity extends AppCompatActivity {

    protected Context mContext;

    private TextView mUsername,mUseremail,mTopName,mUser_gender,mUser_phone,mUser_home,mUser_location,mUser_tag;

    private LinearLayout User_gender,User_name,User_email,User_photo,User_home,User_loaction,User_tag,User_more;



    //设置昵称邮箱
    User user= (User) GlobalVariable.cache.getDataFromMemotyCache("user");

    String rUsername=user.getUsername();
    String rUseremail=user.getEmail();


    private String spUserName=rUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //初始化
        initView();
        setListeners();
        initDate();


        mContext = this;



    }

    //初始化数据
    private void initDate() {
        UserBean bean = null;
        //实例化DBUtils，同时调用其方法获取个人信息资料
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //如果第一次进入，数据库没有保留用户信息
        if (bean == null) {
            bean = new UserBean();
            bean.userName = rUsername;
            bean.phone = "***********";
            bean.sex = "男";
            bean.signature = "暂未设置";
            bean.home = "北京";
            bean.location = "北京";
            //保存到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    /**
     * 为界面空间设置值
     **/
    private void setValue(UserBean bean) {
        mUsername.setText(rUsername);
        mTopName.setText(rUsername);
        mUseremail.setText(rUseremail);
        mUser_tag.setText(bean.signature);
        mUser_phone.setText(bean.phone);
        mUser_home.setText(bean.home);
        mUser_gender.setText(bean.sex);
        mUser_location.setText(bean.location);
    }

    //初始化
    private void initView(){
        mUsername=findViewById(R.id.maintabs_d_user_header_title);
        mUseremail=findViewById(R.id.maintabs_d_user_header_number);
        mTopName=findViewById(R.id.maintabs_d_user_header_topname);
        mUser_gender=findViewById(R.id.maintabs_d_user_header_gender);
        mUser_phone=findViewById(R.id.maintabs_d_user_header_phone);
        mUser_home=findViewById(R.id.maintabs_d_user_header_home);
        mUser_location=findViewById(R.id.maintabs_d_user_header_location);
        mUser_tag=findViewById(R.id.maintabs_d_user_header_tag);


        //初始化个人信息
        mUser_gender.setText(userGender);
        mUser_home.setText(userHome);
        mUser_phone.setText(userPhone);
        mUser_location.setText(userLocation);
        mUser_tag.setText(userTag);

        User_gender=findViewById(R.id.maintabs_d_user_gender);
        User_name=findViewById(R.id.maintabs_d_user_name);
        User_email=findViewById(R.id.maintabs_d_user_email);
        User_photo=findViewById(R.id.maintabs_d_user_photo);
        User_home=findViewById(R.id.maintabs_d_user_home);
        User_loaction=findViewById(R.id.maintabs_d_user_location);
        User_tag=findViewById(R.id.maintabs_d_user_tag);
        User_more=findViewById(R.id.maintabs_d_user_more);

    }


    //点击跳转
    private void setListeners(){
        UserActivity.Onclick onclick=new UserActivity.Onclick();

        User_gender.setOnClickListener(onclick);
        User_name.setOnClickListener(onclick);
        User_email.setOnClickListener(onclick);
        User_photo.setOnClickListener(onclick);
        User_home.setOnClickListener(onclick);
        User_loaction.setOnClickListener(onclick);
        User_tag.setOnClickListener(onclick);
        User_more.setOnClickListener(onclick);


    }

    //点击接口类
    class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            //Intent intent=null;
            switch (v.getId()){
                case R.id.maintabs_d_user_name:
                    Toast.makeText(UserActivity.this, "暂不支持改昵称", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.maintabs_d_user_gender:
                    showGenderDialog();
                    break;
                case R.id.maintabs_d_user_email:
                    Toast.makeText(UserActivity.this, "暂不支持修改邮箱", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.maintabs_d_user_photo:
                    showPhoneDialog();
                    break;
                case R.id.maintabs_d_user_home:
                    showHomeDialog();
                    break;
                case R.id.maintabs_d_user_location:
                    showLocationDialog();
                    break;
                case R.id.maintabs_d_user_tag:
                    showTagDialog();
                    break;
                case R.id.maintabs_d_user_more:
                    Toast.makeText(UserActivity.this, "无更多信息", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    /**
     * 显示选择性别对话框
     */
    public void showGenderDialog() {

        new MaterialDialog.Builder(mContext)
                .title("选择性别")
                .titleGravity(GravityEnum.CENTER)
                .items(new String[]{"男", "女"})
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .positiveText("确定")
                .negativeText("取消")
                .itemsCallbackSingleChoice(0, (dialog, itemView, which, text) -> {

                    //mUser_gender.setText(text.toString());
                    //currentUser.setGender(text.toString());
                    //doUpdate();
                    /*上传数据库*/
                    userGender=text.toString();
                    //mUser_gender.setText(userGender);
                    setSex(userGender);
                    dialog.dismiss();
                    return false;
                }).show();
    }



    /**
     * 显示更换电话对话框
     */
    public void showPhoneDialog() {

        //从服务器获取电话
        //String phone = currentUser.getMobilePhoneNumber();
        //String phone=mUser_phone.getText().toString();
        new MaterialDialog.Builder(mContext)
                .title("电话")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 11, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入电话号码", userPhone, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(UserActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {
                        if (StringUtils.checkPhoneNumber(inputStr)) {
                            //currentUser.setMobilePhoneNumber(inputStr);
                            //phoneCL.setRightText(inputStr);
                            //doUpdate();
                            /*设置上传服务器*/
                            userPhone=inputStr;
                            //mUser_phone.setText(userPhone);
                            setPhone(userPhone);

                        } else {
                            Toast.makeText(UserActivity.this,
                                    "请输入正确的电话号码", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .positiveText("确定")
                .show();
    }


    /**
     * 显示更换籍贯对话框
     */
    public void showHomeDialog() {

        //从服务器获取地址
        //String phone = currentUser.getMobilePhoneNumber();
        //String home=mUser_home.getText().toString();
        new MaterialDialog.Builder(mContext)
                .title("籍贯")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 30, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入籍贯", userHome, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(UserActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {

                        //currentUser.setMobilePhoneNumber(inputStr);
                        //phoneCL.setRightText(inputStr);
                        //doUpdate();
                        /*设置上传服务器*/
                        userHome=inputStr;
                        //mUser_home.setText(userHome);
                        setHome(userHome);
                    }
                })
                .positiveText("确定")
                .show();
    }



    /**
     * 显示更换地址对话框
     */
    public void showLocationDialog() {

        //从服务器获取地址
        //String phone = currentUser.getMobilePhoneNumber();
        //String location=mUser_location.getText().toString();
        new MaterialDialog.Builder(mContext)
                .title("常驻地址")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 30, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入常驻地址", userLocation, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(UserActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {

                        //currentUser.setMobilePhoneNumber(inputStr);
                        //phoneCL.setRightText(inputStr);
                        //doUpdate();
                        /*设置上传服务器*/
                        userLocation=inputStr;
                        //mUser_location.setText(userLocation);
                        setLocaton(userLocation);
                    }
                })
                .positiveText("确定")
                .show();
    }


    /**
     * 显示更换个性签名对话框
     */
    public void showTagDialog() {

        //从服务器获取地址
        //String phone = currentUser.getMobilePhoneNumber();
        //String tag=mUser_tag.getText().toString();
        new MaterialDialog.Builder(mContext)
                .title("个性签名")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .inputRangeRes(0, 35, R.color.red)
                .positiveColor(getResources().getColor(R.color.white))
                .negativeColor(getResources().getColor(R.color.white))
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .titleColor(getResources().getColor(R.color.white))
                .contentColor(getResources().getColor(R.color.white))
                .input("请输入个性签名", userTag, (dialog, input) -> {
                    String inputStr=input.toString();
                    if (inputStr.equals("")) {
                        Toast.makeText(UserActivity.this,
                                "内容不能为空！" + input, Toast.LENGTH_LONG).show();
                    } else {

                        //currentUser.setMobilePhoneNumber(inputStr);
                        //phoneCL.setRightText(inputStr);
                        //doUpdate();
                        /*设置上传服务器*/
                        userTag=inputStr;
                        //mUser_tag.setText(userTag);
                        setTag(userTag);
                    }
                })
                .positiveText("确定")
                .show();
    }


    /**
     * 更新数据
     **/
    private void setSex(String sex) {
        mUser_gender.setText(sex);
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("sex", sex, spUserName);
    }


    private void setPhone(String phone) {
        mUser_phone.setText(phone);
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("phone", phone, spUserName);
    }

    private void setHome(String home) {
        mUser_home.setText(home);
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("home",home, spUserName);
    }

    private void setLocaton(String loc) {
        mUser_location.setText(loc);
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("location",loc, spUserName);
    }

    private void setTag(String tag) {
        mUser_tag.setText(tag);
        //更新数据库字段
        DBUtils.getInstance(this).updateUserInfo("signature",tag, spUserName);
    }

}
