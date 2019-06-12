package com.example.xville_v1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xville_v1.LoginActivity;
import com.example.xville_v1.R;

public class MeFragment extends Fragment{

    protected Button mToLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

    }

    private void toLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    private void initView() {
        mToLogin = getView().findViewById(R.id.btn_toLogin);
    }
}
