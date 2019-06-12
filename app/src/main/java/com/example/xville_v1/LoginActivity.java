package com.example.xville_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mID;
    private EditText mPassword;
    private Button mLgButton;
    private TextView mGoRegisterTv;
    //Database reference
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mLgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        /**
        mGoRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterActivity();
            }
        });**/
    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginUser() {
        String id = mID.getText().toString();
        final String pwd = mPassword.getText().toString().trim();
        Log.d("show pwd1", pwd);
        //Let the reference take us to the table(collection of data in the database)
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        if(ref.child(id) != null){
            ref.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    Log.v("show pwd2", pwd);
                    Log.v("show user password", user.getPassword());
                    if (pwd.equals(user.getPassword())){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                        toHomeActivity();
                    } else {
                        //System.out.println("The read failed: " + databaseError.getCode());
                        Toast.makeText(LoginActivity.this, "The password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(LoginActivity.this, "User does not exist", Toast.LENGTH_LONG).show();
        }
    }

    private void toHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void initView() {
        mID = findViewById(R.id.lg_ID);
        mPassword = findViewById(R.id.lg_pwd);
        mLgButton = findViewById(R.id.btn_lg_asUser);
        mGoRegisterTv = findViewById(R.id.btn_lg_registerTv);
    }

    public void goRegister(View view) {
        toRegisterActivity();
    }
}
