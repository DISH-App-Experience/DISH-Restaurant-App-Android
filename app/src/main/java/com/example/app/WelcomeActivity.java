package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Navigation
        getSupportActionBar().hide();

        ConstraintLayout mainView = findViewById(R.id.mainView);
        mainView.setBackgroundColor(Restaurant.backgroundColor);

        TextView mainLabel = findViewById(R.id.welcomeLabel);
        mainLabel.setTextColor(Restaurant.textColor);

        TextView descLabel = findViewById(R.id.welcomeDesc);
        descLabel.setTextColor(Restaurant.textColor);

        Button googleButton = findViewById(R.id.googleButton);
        googleButton.setBackgroundColor(Restaurant.secondaryBackground);
        googleButton.setText("Continue with Google");
        googleButton.setTextColor(Restaurant.textColor);
        String googleText = "Continue with Google";
        SpannableString googleSS = new SpannableString(googleText);
        StyleSpan googleBoldSpan = new StyleSpan(Typeface.BOLD);
        googleSS.setSpan(googleBoldSpan, 14, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        googleButton.setText(googleSS);

        Button emailButton = findViewById(R.id.emailButton);
        emailButton.setBackgroundColor(Restaurant.themeColor);
        emailButton.setTextColor(Restaurant.textOnButtonColor);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        String text = "Sign in with Email";
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 13, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        emailButton.setText(ss);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setBackgroundColor(Restaurant.backgroundColor);
        signUpButton.setTextColor(Restaurant.themeColor);
        signUpButton.setText("Sign Up");
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}