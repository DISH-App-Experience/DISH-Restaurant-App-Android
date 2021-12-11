package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Navigation
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        createRequest();

        ConstraintLayout mainView = findViewById(R.id.mainView);
        mainView.setBackgroundColor(Restaurant.backgroundColor);

        TextView mainLabel = findViewById(R.id.welcomeLabel);
        mainLabel.setTextColor(Restaurant.textColor);

        TextView descLabel = findViewById(R.id.welcomeDesc);
        descLabel.setTextColor(Restaurant.textColor);

//        Button googleButton = findViewById(R.id.googleButton);
//        googleButton.setBackgroundColor(Restaurant.secondaryBackground);
//        googleButton.setText("Continue with Google");
//        googleButton.setTextColor(Restaurant.textColor);
//        String googleText = "Continue with Google";
//        SpannableString googleSS = new SpannableString(googleText);
//        StyleSpan googleBoldSpan = new StyleSpan(Typeface.BOLD);
//        googleSS.setSpan(googleBoldSpan, 14, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        googleButton.setText(googleSS);
//        googleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });

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

    public void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("668635512839-dnvjknvf5hoktutts3s9va4dhq8ef187.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                alert("Error", e.getMessage().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Success");
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                            mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("Success, found existing user");
                                    } else {
                                        System.out.println("Success, did not find existing user");
                                    }
                                }
                            });
                        } else {
                            alert("Error", "Please Try Again");
                        }
                    }
                });
    }

    private void alert(String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("seen");
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}