package com.example.xville_v1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.xville_v1.Model.Event;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EventDetailActivity extends AppCompatActivity {

    //View
    protected Toolbar toolbar;
    private ImageView poster;
    private TextView title;
    private TextView time;
    private TextView location;
    private PDFView pdfViewer;

    //Bundle and intent
    private Bundle extras;
    private String eventName;
    private String userID;

    //Firebase
    //this databasereference for loading the event
    private DatabaseReference mEventRef;
    //this databasereference for add the schedule
    private DatabaseReference mScheduleAddRef;

    //pdf file
//    private static final String FILENAME = "about_waterpolo_competition_pdf.pdf";
//    private PdfRenderer pdfRenderer;
//    private PdfRenderer.Page currentPage;
//    private ParcelFileDescriptor parcelFileDescriptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_event_detail);

        //get the data from intent
        extras = getIntent().getExtras();
        eventName = extras.getString("EventName");
        userID = extras.getString("UserId");

        //Tool bar
        toolbar = findViewById(R.id.toobar_eventDetail);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            //set the back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);}

        //View
        poster = findViewById(R.id.imageEventPoster);
        title = findViewById(R.id.event_title);
        time = findViewById(R.id.event_time);
        location = findViewById(R.id.event_location);
        pdfViewer = findViewById(R.id.pdf_viewer);

        //Firebase
        mEventRef = FirebaseDatabase.getInstance().getReference().child("Events");
        mScheduleAddRef = FirebaseDatabase.getInstance().getReference();

        //Floating Action Button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mScheduleAddRef.child("Schedule").child(userID).child(eventName).setValue(eventName).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(view, "Add into your schedule", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }else{
                            Toast.makeText(EventDetailActivity.this, "Fail to add into schedule", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        //load event
        loadEvent();

    }

    private void loadEvent() {
        mEventRef.child(eventName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event post = dataSnapshot.getValue(Event.class);
                Glide.with(getApplicationContext()).load(post.getImg()).placeholder(R.drawable.common_full_open_on_phone).into(poster);
                title.setText(post.getTitle());
                time.setText(post.getTime());
                location.setText(post.getLocation());
                toolbar.setTitle(post.getTitle());

                //get pdf url
                //String url = post.getAbout();
                new RetrievePdfStream().execute("https://firebasestorage.googleapis.com/v0/b/exfirebase-052919.appspot.com/o/about_waterpolo_competition_pdf.pdf?alt=media&token=7574b459-f1d7-428d-a12f-38d491671a2c");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EventDetailActivity.this, "loading error",
                        Toast.LENGTH_LONG).show();

            }
        });
    }

    class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch(IOException e){
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfViewer.fromStream(inputStream).load();
        }
    }

    //set the title for toolbar
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //tool bar set title
        if (toolbar != null) {
            toolbar.setTitle(eventName);
            toolbar.setSubtitle("Sub title");
        }
    }
}
