package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RandomActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    TextView titleTV;

    TextView topMenuitemsTV;

    ImageButton profileButton;

    View eventView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        // Navigation
        getSupportActionBar().hide();

        // Firebase Configuration
        mDatabase = FirebaseDatabase.getInstance().getReference();

        titleTV = findViewById(R.id.textView4);
        titleTV.setTextColor(Restaurant.textColor);
        getFirstName();

        ConstraintLayout mainView = findViewById(R.id.homeController);
        mainView.setBackgroundColor(Restaurant.backgroundColor);

        profileButton = findViewById(R.id.profileButton);
        profileButton.setBackgroundColor(Restaurant.themeColor);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("profile btn pressed");
            }
        });

        eventView = findViewById(R.id.eventView);
        eventView.setBackgroundColor(Restaurant.themeColor);

        topMenuitemsTV = findViewById(R.id.topMenuitemsTV);
        topMenuitemsTV.setTextColor(Restaurant.textColor);

//        Button signOutButton = findViewById(R.id.signOutButton);
//        signOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try{
//                    mAuth.getInstance().signOut();
//                } catch (Exception error1) {
//                    System.out.println(error1.toString());
//                }
//                Intent intent = new Intent(RandomActivity.this, WelcomeActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void getFirstName() {
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        System.out.println("searching for user with the uid of " + user);
        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(user).child("firstName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    titleTV.setText("Hi, " + String.valueOf(task.getResult().getValue()));
                } else {
                    String.valueOf("Hi there!");
                }
            }
        });
    }
}