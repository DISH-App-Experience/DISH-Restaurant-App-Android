package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView hello;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation
        getSupportActionBar().hide();

        // Firebase Configuration
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Functions Thrown
        backgroundColor();
    }

    // Backend
    public void backgroundColor() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("backgroundColor").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.backgroundColor = Color.parseColor(String.valueOf(task.getResult().getValue()));
                    textColor();
                }
            }
        });
    }

    public void textColor() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("textColor").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.textColor = Color.parseColor(String.valueOf(task.getResult().getValue()));
                    themeColor();
                }
            }
        });
    }

    public void themeColor() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("themeColor").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.themeColor = Color.parseColor(String.valueOf(task.getResult().getValue()));
                    appIcon();
                }
            }
        });
    }

    public void appIcon() {
        mDatabase.child("Apps").child(Restaurant.id).child("appIcon").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.logoImage = String.valueOf(task.getResult().getValue());
                    name();
                }
            }
        });
    }

    public void name() {
        mDatabase.child("Apps").child(Restaurant.id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.name = String.valueOf(task.getResult().getValue());
                    themeColorOnButton();
                }
            }
        });
    }

    public void themeColorOnButton() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("themeColorOnButton").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.textOnButtonColor = Color.parseColor(String.valueOf(task.getResult().getValue()));
                    secondaryBackground();
                }
            }
        });
    }

    public void secondaryBackground() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("secondaryBackground").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.secondaryBackground = Color.parseColor(String.valueOf(task.getResult().getValue()));
                    aboutUs();
                }
            }
        });
    }

    public void aboutUs() {
        mDatabase.child("Apps").child(Restaurant.id).child("theme").child("aboutUs").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Restaurant.description = String.valueOf(task.getResult().getValue());
                    System.out.println("done");
                    checkAuth();
                }
            }
        });
    }

    public void checkAuth() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            System.out.println(user.getUid().toString());
            Intent intent = new Intent(MainActivity.this, RandomActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    // Helpers
    public static int[] getRGB(final int hex) {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        return new int[] {r, g, b};
    }
}