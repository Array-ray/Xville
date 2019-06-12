package com.example.xville_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerWithAuth extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegister;
    private Button mToRegister;
    //private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    /*
    private FirebaseDatabase userDB;
    private DatabaseReference userRef;
    private User user;
    */
    public registerWithAuth() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);

        initView();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        mToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterAct();
            }
        });

    }

    private void toRegisterAct() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void initView() {
        mEmailField = findViewById(R.id.emailInput);
        mPasswordField = findViewById(R.id.passwordInput);
        mRegister = findViewById(R.id.registerButton);
        mToRegister = findViewById(R.id.toRegister);
        //progressBar = new ProgressBar(this);
        mAuth = FirebaseAuth.getInstance();
        /*
        userDB = FirebaseDatabase.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("com.example.xville_v1.User");
        */
    }

    private void registerUser() {
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();
        Toast.makeText(registerWithAuth.this, email, Toast.LENGTH_LONG).show();

        if(TextUtils.isEmpty(email)){
            /*Email field is empty*/
            Toast.makeText(registerWithAuth.this, "Please enter email", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }

        if(TextUtils.isEmpty(password)){
            /*Email field is empty*/
            Toast.makeText(registerWithAuth.this, "Please enter password", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(registerWithAuth.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registerWithAuth.this,"Sign up successfully", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(registerWithAuth.this,"Sign up failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
