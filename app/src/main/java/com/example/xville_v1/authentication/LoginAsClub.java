package com.example.xville_v1.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xville_v1.ClubMainActivity;
import com.example.xville_v1.Model.Club;
import com.example.xville_v1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAsClub extends AppCompatActivity implements View.OnClickListener{

    //xml views
    private EditText clubName;
    private EditText clubpwd;
    private Button clubLogin;
    private TextView toSignUp;

    //firebase
    private DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_asclub);

        //Views
        clubName = findViewById(R.id.et_clubName);
        clubpwd = findViewById(R.id.et_clubPwd);

        //Buttons
        clubLogin = findViewById(R.id.btn_lgAsClub);
        toSignUp = findViewById(R.id.tv_SignUpClub);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_SignUpClub) {
            toClubSignUp();
        } else if (i == R.id.btn_lgAsClub) {
            loginAsClub(clubName.getText().toString().trim(), clubpwd.getText().toString().trim());
        }
    }

    private void loginAsClub(String cn, final String cpwd) {

        //check if the user's input is empty
        if(TextUtils.isEmpty(cn)){
            /*Email field is empty*/
            Toast.makeText(LoginAsClub.this, "Please enter club name", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }

        if(TextUtils.isEmpty(cpwd)){
            /*Email field is empty*/
            Toast.makeText(LoginAsClub.this, "Please enter password", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }

        //Firebase realtime database
        ref = FirebaseDatabase.getInstance().getReference().child("Clubs");


        //check if the club name is not null, then compare the password
        if(ref.child(cn) != null){
            ref.child(cn).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Club club = dataSnapshot.getValue(Club.class);
//                    Log.v("show pwd2", pwd);
//                    Log.v("show user password", user.getPassword());
                    if (cpwd.equals(club.getPassword())){
                        Toast.makeText(LoginAsClub.this, "Login successful", Toast.LENGTH_LONG).show();
                        toClubMainPage();
                    } else {
                        //System.out.println("The read failed: " + databaseError.getCode());
                        Toast.makeText(LoginAsClub.this, "The password is incorrect", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(LoginAsClub.this, "User does not exist", Toast.LENGTH_LONG).show();
        }


    }

    private void toClubMainPage() {
        Intent intent = new Intent(this, ClubMainActivity.class);
        startActivity(intent);
        finish();

    }


    private void toClubSignUp() {
        Intent intent = new Intent(this, RegisterAsClub.class);
        startActivity(intent);
        finish();
    }
}
