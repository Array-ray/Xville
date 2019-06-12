package com.example.xville_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_username, et_id, et_email, et_password;
    private Button btn_register;
    //private FirebaseDatabase userDB;
    private DatabaseReference userRef;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String nickname = et_username.getText().toString().trim();
        String id = et_id.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        user = new User();

        //fill in the information to the User class use setter in the  User class
        user.setUsername(nickname);
        user.setID(id);
        user.setEmail(email);
        user.setPassword(password);

        //Push value by using database reference
        userRef.child("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                    toLoginActivity();
                }else{
                    Toast.makeText(RegisterActivity.this, "User created failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        et_username = findViewById(R.id.nickName);
        et_id = findViewById(R.id.ID);
        et_email = findViewById(R.id.Email);
        et_password = findViewById(R.id.Password);
        btn_register = findViewById(R.id.signUpButton);

        //userDB = FirebaseDatabase.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();

    }
}
