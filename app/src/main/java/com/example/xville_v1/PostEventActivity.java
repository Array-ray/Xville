package com.example.xville_v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.xville_v1.Model.Event;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostEventActivity extends AppCompatActivity {

    public static final int GALLERY_REQUEST = 1;

    private ImageButton mSelectImageView;
  //  private Button mSelectImage;
    private Button mUplaodButton;
    private EditText mEventTime;
    private EditText mEventTitle;
    private EditText mEventLoacation;
    private EditText mEventDescription;
    private EditText mEventType;

    private String clubName;
    private String clubPassword;
    private Uri mImageUri;
    private String eventTime;
    private String eventTitle;
    private String eventLoacation;
    private String eventDescription;
    private String eventType;

    private Event event;

    private StorageReference mStorage;
    private DatabaseReference mDatabasePosts;
    private DatabaseReference mDatabaseEvents;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_create_myevent);

        setTitle("Publish Event");

        initView();
        initEvent();
    }
    private void initEvent() {

//        mSelectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent, GALLERY_REQUEST);
//
//            }
//        });

        mUplaodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPublish();
            }
        });

        mSelectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }

        });

    }

    private void initView() {
      //  mSelectImage = findViewById(R.id.button_uploadEvent_post);
        mSelectImageView = findViewById(R.id.imageView_uploadEvent_img);
        mUplaodButton = findViewById(R.id.button_uploadEvent_upload_file);
        mEventTime = findViewById(R.id.uploadEvent_time);
        mEventTitle = findViewById(R.id.uploadEvent_title);
        mEventLoacation = findViewById(R.id.uploadEvent_location);
        mEventDescription = findViewById(R.id.uploadEvent_post_description);
        mEventType = findViewById(R.id.uploadEvent_type);

        //1、get Preference
        // similar like local cache
        SharedPreferences clubInfoRefer =getSharedPreferences("CLUB", Context.MODE_PRIVATE);

        //2、get the club name and password
        clubName = clubInfoRefer.getString("CLUBNAME",null);
        clubPassword =clubInfoRefer.getString("CLUBPASSWORD", null);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabasePosts = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");
    }

    private void startPublish() {

        final String eventTime = mEventTime.getText().toString().trim();
        final String eventTitle = mEventTitle.getText().toString().trim();
        final String eventLoacation = mEventLoacation.getText().toString().trim();
        final String eventDescription = mEventDescription.getText().toString().trim();
        final String eventType = mEventType.getText().toString().trim();


        mDatabasePosts = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");

        if (!TextUtils.isEmpty(eventTime)
                && !TextUtils.isEmpty(eventTitle)
                && !TextUtils.isEmpty(eventLoacation)
                && mImageUri != null
                && !TextUtils.isEmpty(eventDescription)
                && !TextUtils.isEmpty(eventType))  {

            final StorageReference filePath = mStorage.child("ClubandEvents").child(mImageUri.getLastPathSegment());



            final UploadTask uploadTask = filePath.putFile(mImageUri);
            // filePath.putFile(mImageUri).addOnFailureListener(new OnFailureListener() {
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    // Handle unsuccessful uploads
                    Toast.makeText(PostEventActivity.this, "Publish Failed!", Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                event = new Event();
                                event.setTime(eventTime);
                                event.setTitle(eventTitle);
                                event.setLocation(eventLoacation);
                                event.setImg(downloadUri.toString());
                                event.setAbout(eventDescription);
                                event.setType(eventType);
                                event.setHeldbyClub(clubName);


                                mDatabaseEvents.child(eventTitle).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mDatabasePosts.child(clubName).child(eventTitle).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(PostEventActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();
                                                    toClubMainActivity();
                                                }
                                            });

                                        }else{
                                            Toast.makeText(PostEventActivity.this, "Publishfailed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });

//                    Toast.makeText(PostEventActivity.this, "Successfully publish!", Toast.LENGTH_LONG).show();
//
//                    startActivity(new Intent(PostEventActivity.this, ClubMainActivity.class));

                }
            });

        }

    }
    private void toClubMainActivity() {
        Intent intent = new Intent(this,ClubMainActivity.class);
        intent.putExtra("CLUBNAME", clubName);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST) {

            mImageUri = data.getData();
            mSelectImageView.setImageURI(mImageUri);

        }
    }


}
