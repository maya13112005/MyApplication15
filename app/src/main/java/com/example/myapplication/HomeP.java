package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

public class HomeP extends AppCompatActivity {
    private TextView nameTxtView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    private static final String USERS = "users";
    private androidx.constraintlayout.widget.ConstraintLayout layout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainp);

        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());

        nameTxtView = findViewById(R.id.name_textview);
        DrawerLayout drawerLayout=findViewById(R.id.mainp);
        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        break;
                    }
                }
                nameTxtView.setText(fname);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }




    }


