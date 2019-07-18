package com.example.xville_v1.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xville_v1.R;


public class DeleteDialog extends Dialog{

    private Button yes;
    private Button no;
    private TextView titleTextView;
    private TextView messageTextView;
    private String titleString;
    private String messageString;

    private String yesString, noString;
    private Context context;
    private ViewGroup.LayoutParams params;

    private onNoOnclickListener noOnclickListener;
    private onYesOnclickListener yesOnclickListener;


    public DeleteDialog(Context context, ViewGroup.LayoutParams params) {
        super(context, R.style.DeleteDialog);
        this.context = context;
        this.params = params;

    }

    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noString = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesString = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view =  LayoutInflater.from(context).inflate(R.layout.dialog_delete_confirm, null);
        addContentView(view,params);


        setCanceledOnTouchOutside(false);


        initView();

        initData();

        initEvent();

    }


    private void initEvent() {

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });
    }

    private void initData() {

        if (titleString != null) {
            titleTextView.setText(titleString);
        }
        if (messageString != null) {
            messageTextView.setText(messageString);
        }

        if (yesString != null) {
            yes.setText(yesString);
        }
        if (noString != null) {
            no.setText(noString);
        }
    }

    private void initView() {
        yes = (Button) findViewById(R.id.delete_dialog_btn_yes);
        no = (Button) findViewById(R.id.delete_dialog_btn_no);
        titleTextView = (TextView) findViewById(R.id.delete_dialog_title);
        messageTextView = (TextView) findViewById(R.id.delete_dialog_message);
    }

    public void setTitle(String title) {
        titleString = title;
    }


    public void setMessage(String message) {
        messageString = message;
    }

    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
