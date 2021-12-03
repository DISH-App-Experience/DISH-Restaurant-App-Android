package com.example.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Navigation
        getSupportActionBar().hide();

        ConstraintLayout mainView = findViewById(R.id.signUpPage);
        mainView.setBackgroundColor(Restaurant.backgroundColor);

        TextView mainLabel = findViewById(R.id.welcomeLabel2);
        mainLabel.setTextColor(Restaurant.textColor);

        EditText signUpEmailTF = findViewById(R.id.signUpEmailTF);
        signUpEmailTF.setBackgroundColor(Restaurant.backgroundColor);
        signUpEmailTF.setTextColor(Restaurant.textColor);
        signUpEmailTF.setHintTextColor(Restaurant.textColor);

        EditText signUpPasswordTF = findViewById(R.id.signUpPasswordTF);
        signUpPasswordTF.setBackgroundColor(Restaurant.backgroundColor);
        signUpPasswordTF.setTextColor(Restaurant.textColor);
        signUpPasswordTF.setHintTextColor(Restaurant.textColor);

        Button mainButton = findViewById(R.id.mainButtonSignUp);
        mainButton.setBackgroundColor(Restaurant.themeColor);
        mainButton.setTextColor(Restaurant.textOnButtonColor);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signUpEmailTF.getText().toString();
                String password = signUpPasswordTF.getText().toString();
                if ((TextUtils.isEmpty(email)) || (TextUtils.isEmpty(password))) {
                    alert("Error", "Please Fill In All Fields");
                } else {
                    System.out.println(email);
                    System.out.println(password);
                }
            }
        });

        Button signUpButton = findViewById(R.id.signInButonFromSignUp);
        signUpButton.setBackgroundColor(Restaurant.backgroundColor);
        signUpButton.setTextColor(Restaurant.themeColor);
        signUpButton.setText("Sign In");
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void alert(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        System.out.println("seen");
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}