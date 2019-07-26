package com.example.xville_v1.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xville_v1.MainActivity;
import com.example.xville_v1.Model.User;
import com.example.xville_v1.R;
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

    }

    private void toRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginUser() {
        String id = mID.getText().toString().trim();
        final String pwd = mPassword.getText().toString().trim();

        //check if the user's input is empty
        if(TextUtils.isEmpty(id)){
            /*Email field is empty*/
            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }

        if(TextUtils.isEmpty(pwd)){
            /*Email field is empty*/
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
            /*Stop execution*/
            return;
        }


        //Log.d("show pwd1", pwd);
        //Let the reference take us to the table(collection of data in the database)
        ref = FirebaseDatabase.getInstance().getReference().child("Users");

        if(ref.child(id) != null){
            ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User luser = dataSnapshot.getValue(User.class);
//                    Log.v("show pwd2", pwd);
//
                    if (pwd.equals(luser.getPassword())){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                        //get user information for preference
                        String id = luser.getID();
                        String name = luser.getUsername();

                        toHomeFragment(id, name);
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
            Toast.makeText(LoginActivity.this, "User does not exist,Please sign up first", Toast.LENGTH_LONG).show();
        }
    }

    private void toHomeFragment(String id, String name) {

        //1、open the preferences with name club info reference, if not exist, create new preference
        SharedPreferences userInfoPref = getSharedPreferences("USER", Context.MODE_PRIVATE);

        //2、make it editable
        SharedPreferences.Editor usereditor=userInfoPref.edit();

        //3. get data from particular view widget
        usereditor.putString("USERID", id);
        usereditor.putString("USERNAME", name);

        //4、finish submission
        usereditor.commit();

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("USERID",mID.getText().toString().trim());
        startActivity(i);
        finish();

//        //using Bundle to send data
//        Bundle bundle = new Bundle();
//        bundle.putString("USERID", mID.getText().toString().trim());
//
//        //new one fragment and put data into fragment
//        //new one fragment manager
//        //new one fragment transaction
//        HomeFragment homeFragment = new HomeFragment();
//        homeFragment.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                homeFragment).commit();
    }

    private void initView() {
        mID = findViewById(R.id.lg_ID);
        mPassword = findViewById(R.id.lg_pwd);
        mLgButton = findViewById(R.id.btn_lg_asUser);
        mGoRegisterTv = findViewById(R.id.btn_lg_registerTv);
    }

    // onClick attribute to the textview
    public void goRegister(View view) {
        toRegisterActivity();
    }
}
