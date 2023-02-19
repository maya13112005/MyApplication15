package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

public class ProfileActivity extends AppCompatActivity {
    private TextView nameTxtView;
    private TextView emailTxtView, phoneTxtView;
    private ImageView userImageView, emailImageView, phoneImageView;
    private final String TAG = this.getClass().getName().toUpperCase();
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private Map<String, String> userMap;
    private String email;
    private String userid;
    private static final String USERS = "users";
    private Button back;
    private androidx.constraintlayout.widget.ConstraintLayout layout;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //receive data from login screen
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child(USERS);
        Log.v("USERID", userRef.getKey());


        layout = findViewById(R.id.bd_id);
        changeBack();
        nameTxtView = findViewById(R.id.name_textview);
        emailTxtView = findViewById(R.id.email_textview);
        phoneTxtView = findViewById(R.id.phone_textview);
        back = findViewById(R.id.back);
        userImageView = findViewById(R.id.user_imageview);
        emailImageView = findViewById(R.id.email_imageview);
        phoneImageView = findViewById(R.id.phone_imageview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });


        // Read from the database
        userRef.addValueEventListener(new ValueEventListener() {
            String fname, mail, phone;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.child("email").getValue().equals(email)) {
                        fname = keyId.child("fullName").getValue(String.class);
                        phone = keyId.child("phone").getValue(String.class);
                        break;
                    }
                }
                nameTxtView.setText(fname);
                emailTxtView.setText(email);
                phoneTxtView.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        }


    private void changeBack() {
        switch (new Random().nextInt(5)) {
            case 0:
                layout.setBackground(getDrawable(R.drawable.proback2));
                break;
            case 1:
                layout.setBackground(getDrawable(R.drawable.proback2));
            case 2:
                layout.setBackground(getDrawable(R.drawable.proback3));
                break;
            case 3:
                layout.setBackground(getDrawable(R.drawable.proback4));
                break;
            case 4:
                layout.setBackground(getDrawable(R.drawable.proback5));
                break;


        }
    }
}

