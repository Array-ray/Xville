package com.example.xville_v1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateEventActivity extends AppCompatActivity {

    private Button mSelectFile, mUploadFile;

    Uri fileUri; //uri are actually url for local storage
    UploadTask mUploadTask;
    private FirebaseStorage mStorage; // used for upoad files
    private FirebaseDatabase mDatabase; //used to store URLs of uploaded files
//    private ProgressDialog mProgressDialog;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_create_myevent);


        mStorage = FirebaseStorage.getInstance(); //return an object of Firebase Storage
        mDatabase = FirebaseDatabase.getInstance(); // return an object of Firebase Database

        //get the view of layout file
        mSelectFile = findViewById(R.id.select_file);
        mUploadFile = findViewById(R.id.upload_file);

        mSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check the permission
                if(ContextCompat.checkSelfPermission(CreateEventActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    selectFile();
                }else
                    ActivityCompat.requestPermissions(CreateEventActivity.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });

        mUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fileUri != null) // the user has selected the file
                    uploadFile(fileUri);
                else
                    Toast.makeText(CreateEventActivity.this, "Select a file", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setDialog(boolean show){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        builder.setView(R.layout.dialog_loading);
        dialog = builder.create();
        dialog.setTitle("Loading");
        if (show)dialog.show();
        else dialog.dismiss();
    }

    private void uploadFile(Uri fileUri) {

        //show the dialog
        setDialog(true);


//        mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setProgressStyle(ProgressDialog);
        String fileName = System.currentTimeMillis()+"";
        final StorageReference mstorageReference = mStorage.getReference().child("uploadsFile").child(fileName); // returns store path
//        mstorageReference.child("uploads").child(fileName).putFile(fileUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        String url = taskSnapshot.getDownloadUrl().toString
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//            }
//        });
        mUploadTask = mstorageReference.putFile(fileUri);
        // Register observers to listen for when the download is done or if it fails
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(CreateEventActivity.this, "File not successully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return mstorageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            //post the new event into database
                            DatabaseReference dbReference = mDatabase.getReference();//return the path to the root


                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                //track the progress of our upload..
                int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                //set the current progress
                //progressDialog.setProgress(currentProgress);
            }
        });

    }

    //In this function, we can check if the user is successfully granted the permission or not
    //check the request code is 9 or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            selectFile();
        }
        else
            Toast.makeText(CreateEventActivity.this, "Please provide permission", Toast.LENGTH_SHORT).show();
    }

    private void selectFile() {
        //to offer user to select a file using file manager
        //we will be using the intent
        Intent intent = new Intent();
        intent.setType("applicatioin/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); // to fetch files
        startActivityForResult(intent, 86);
    }

    //to check the user if successfully select file or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 86 && resultCode == RESULT_OK && data!=null)
        {
            fileUri = data.getData(); // return the uri of selected file..
        }
        else{
            Toast.makeText(CreateEventActivity.this, "Please select file", Toast.LENGTH_SHORT).show();
        }
    }
}
