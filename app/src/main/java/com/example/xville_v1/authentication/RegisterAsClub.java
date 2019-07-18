package com.example.xville_v1.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xville_v1.Model.Club;
import com.example.xville_v1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAsClub extends AppCompatActivity {

    //UI view
    private EditText mClubname, mClubEmail, mPassword;
    //private Button mSend;
    private Button mRegister;

    //Data model
    private Club mClub;

    //Firebase
    private DatabaseReference clubRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_asclub);

        //Initialize the view
        mClubname = findViewById(R.id.et_club_name);
        mClubEmail = findViewById(R.id.et_email_club);
        mPassword = findViewById(R.id.et_pwd_club);
        mRegister = findViewById(R.id.btn_signup_club);

        //userDB = FirebaseDatabase.getInstance();
        clubRef = FirebaseDatabase.getInstance().getReference();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClub(mClubname.getText().toString().trim(),
                        mClubEmail.getText().toString().trim(),
                        mPassword.getText().toString().trim());
            }
        });

    }

    private void registerClub(String name, String email, String password) {
        //Realtime database part
        mClub = new Club();
        //fill in the information to the User class use setter in the  User class
        mClub.setName(name);
        mClub.setEmail(email);
        mClub.setPassword(password);

        //Push value by using database reference
        clubRef.child("Clubs").child(name).setValue(mClub).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterAsClub.this, "Club register successfully", Toast.LENGTH_LONG).show();
                    toLoginActivity();
                }else{
                    Toast.makeText(RegisterAsClub.this, "Club register failed", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginAsClub.class);
        startActivity(intent);
        finish();
    }
}
