package com.example.xville_v1.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xville_v1.BaseActivity;
import com.example.xville_v1.Model.User;
import com.example.xville_v1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends BaseActivity {

    //Tag for Log
    private static final String TAG = "EmailPassword";

    //UI view
    private EditText et_username, et_id, et_email, et_password;
    private Button btn_register;

    //Data model
    private User mUser;

    //Firebase
    //private FirebaseDatabase userDB;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        //get user's input
        final String nickname = et_username.getText().toString().trim();
        final String id = et_id.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(nickname,id,email,password);
            }
        });
    }

    private void registerUser(String nickname, String id, String email, String password) {

        //validate if the input is the correct type
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        //Authentication part
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

        //Email verification
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        //findViewById(R.id.verifyEmailButton).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]


        //Realtime database part
        mUser = new User();
        //fill in the information to the User class use setter in the  User class
        mUser.setUsername(nickname);
        mUser.setID(id);
        mUser.setEmail(email);
        mUser.setPassword(password);

        //Push value by using database reference
        userRef.child("Users").child(id).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    private boolean validateForm() {
        boolean valid = true;

        String email = et_email.getText().toString();
            if (TextUtils.isEmpty(email)) {
                et_email.setError("Required.");
            valid = false;
        } else {
            et_email.setError(null);
        }

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_password.setError("Required.");
            valid = false;
        } else {
            et_password.setError(null);
        }

        String id = et_id.getText().toString();
        if (TextUtils.isEmpty(id)) {
            et_id.setError("Required.");
            valid = false;
        } else {
            et_id.setError(null);
        }

        String nickname = et_username.getText().toString();
        if (TextUtils.isEmpty(nickname)) {
            et_username.setError("Required.");
            valid = false;
        } else {
            et_username.setError(null);
        }
        return valid;
    }
}
