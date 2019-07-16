package com.example.xville_v1.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xville_v1.R;

public class GuideRoleActivity extends AppCompatActivity {

    private Button AsUser;
    private Button AsClub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        AsUser = findViewById(R.id.asUser);
        AsClub = findViewById(R.id.asClub);

        AsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideRoleActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AsClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideRoleActivity.this, LoginAsClub.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
