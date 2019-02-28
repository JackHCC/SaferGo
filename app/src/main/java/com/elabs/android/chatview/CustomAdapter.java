//package com.elabs.android.chatview;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.jack.isafety.R;
//
//import java.util.ArrayList;
//import xin.skingorz.isafety.Message;
//
//public class CustomAdapter extends ArrayAdapter {
//
//    TextView messageText;
//    RelativeLayout messageRelativeLayout;
//    public CustomAdapter(Context context,ArrayList messages) {
//        super(context, 0, messages);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        View listItemView = convertView;
//        if(convertView ==null)
//        {
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.message_list_item_view, parent, false);
//        }
//        Message message = (Message) getItem(position);
//        messageRelativeLayout = (RelativeLayout)listItemView.findViewById(R.id.list_item_relative_layout);
//     //   messageRelativeLayout.setHorizontalGravity(message.getmLayoutGravity());
//        messageText = (TextView)listItemView.findViewById(R.id.display_view);
//       // messageText.setBackgroundResource(message.mLayoutBackground);
//        messageText.setText(message.getMessage());
//        messageText.setTextColor(Color.BLACK);
//
//        return listItemView;
//
//
//
//    }
//}
