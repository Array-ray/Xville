package com.example.xville_v1.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xville_v1.Model.User;
import com.example.xville_v1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //Tag for Log
    private static final String TAG = "EmailPassword";

    //UI view
    private EditText et_username, et_id, et_email, et_password;
    private Button mSend;
    private Button btn_register;

    //Data model
    private User mUser;

    //Firebase
    //private FirebaseDatabase userDB;
    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean emailVerified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        //click the send email verification button
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //authentication of user and send verification email
                registerUser(et_email.getText().toString().trim(),
                        et_password.getText().toString().trim());
            }
        });

        //click the sign up button
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if the user is sign up, if not sign up return null,if not send
                //verification email, return false
                //manually reload
                mAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        currentUser = mAuth.getCurrentUser();
                        emailVerified = currentUser.isEmailVerified();
                    }
                });

                //write the user information into database
                if(currentUser != null && emailVerified == true){
                    writeTodatabase();
                }else{
                    mSend.setTextColor(getApplication().getResources().getColor(R.color.red));
                    Toast.makeText(RegisterActivity.this, "Please send the verification email",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void writeTodatabase() {
        //Realtime database part
        mUser = new User();
        //fill in the information to the User class use setter in the  User class
        mUser.setUsername(et_username.getText().toString().trim());
        mUser.setID(et_id.getText().toString().trim());
        mUser.setEmail(et_email.getText().toString().trim());
        mUser.setPassword(et_password.getText().toString().trim());

        //Push value by using database reference
        userRef.child("Users").child(et_id.getText().toString().trim()).setValue(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    @Override
    protected void onStart() {
        super.onStart();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void registerUser(String email, String password) {
        //validate if the input is the correct type
        if (!validateForm()) {
            return;
        }
        //showProgressDialog();

        //Authentication part
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            sendEmailVerification();
                            Toast.makeText(RegisterActivity.this, "Authentication successed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
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
        et_email = findViewById(R.id.et_email_user);
        et_password = findViewById(R.id.et_pwd_user);
        btn_register = findViewById(R.id.btn_signup_user);
        mSend = findViewById(R.id.btn_send);

        //userDB = FirebaseDatabase.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

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

    private void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser veriUser = mAuth.getCurrentUser();
        veriUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Verification email sent to " + veriUser.getEmail(),
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
    }
}
