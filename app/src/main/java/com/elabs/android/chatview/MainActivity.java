package com.elabs.android.chatview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jack.isafety.R;

import java.util.ArrayList;

import xin.skingorz.isafety.GlobalVariable;
import xin.skingorz.isafety.Message;
import xin.skingorz.isafety.MessageToServer;

public class MainActivity extends AppCompatActivity {

//    String messageText;
    EditText editText;
    Button okButton, koButton;
    ArrayList<Message> arrayList;
//    ArrayList<Message> messagesList;
//    CustomAdapter customAdapter;
    ListView listView;
//    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity_main);

        editText = (EditText)findViewById(R.id.Edit_text);
        okButton = (Button)findViewById(R.id.ok_button);
//        koButton  = (Button)findViewById(R.id.ko_button);
        listView = (ListView)findViewById(R.id.list_view);


        arrayList = new ArrayList<Message>();

        //messagesList = new ArrayList<Message>();

        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        final String rName=bundle.getString("name").toString();
        final String rEmail=bundle.getString("email");


//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageText = editText.getText().toString();
//
//                Message sendMessage = new Message(messageText,rEmail);
//                new MessageToServer().sendMessage(sendMessage);
//
//                arrayList.add(sendMessage);
//                customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
//                listView.setAdapter(customAdapter);
//                editText.setText("");
//            }
//        });

       /* koButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText = editText.getText().toString();
                arrayList.add(new IndividualMessage(messageText, Gravity.LEFT, R.drawable.message_bubble2));
                customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
                listView.setAdapter(customAdapter);
                editText.setText("");
            }
        });*/

    }
}
