package com.elabs.android.chatview;

public class IndividualMessage {

    String mMessage;
    int mLayoutGravity;
    int mLayoutBackground;

    public IndividualMessage(String message, int layoutGravity, int layoutBackground)
    {
        mMessage = message;
        mLayoutGravity = layoutGravity;
        mLayoutBackground = layoutBackground;
    }

    public String getmMessage() {
        return mMessage;
    }

    public int getmLayoutGravity() {
        return mLayoutGravity;
    }

    public int getmLayoutBackground() {
        return mLayoutBackground;
    }
}
