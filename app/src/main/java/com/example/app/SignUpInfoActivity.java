package com.example.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignUpInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String gender = "Male";

    String date;

    FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_info);

        // Navigation
        getSupportActionBar().hide();

        initDatePicker();

        ConstraintLayout mainView = findViewById(R.id.signUpInfoPage);
        mainView.setBackgroundColor(Restaurant.backgroundColor);

        TextView mainLabel = findViewById(R.id.signUpInfoLabelMain);
        mainLabel.setTextColor(Restaurant.textColor);

        EditText signUpInfoFirstName = findViewById(R.id.signUpInfoFirstName);
        signUpInfoFirstName.setBackgroundColor(Restaurant.backgroundColor);
        signUpInfoFirstName.setTextColor(Restaurant.textColor);
        signUpInfoFirstName.setHintTextColor(Restaurant.textColor);

        EditText signUpInfoLastName = findViewById(R.id.signUpInfoLastName);
        signUpInfoLastName.setBackgroundColor(Restaurant.backgroundColor);
        signUpInfoLastName.setTextColor(Restaurant.textColor);
        signUpInfoLastName.setHintTextColor(Restaurant.textColor);

        Spinner spinner = findViewById(R.id.signUpInfoGender);
        spinner.setBackgroundColor(Restaurant.backgroundColor);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders, R.layout.color_spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        Button signUpInfoBirthday = findViewById(R.id.signUpInfoBirthday);
        signUpInfoBirthday.setBackgroundColor(Restaurant.backgroundColor);
        signUpInfoBirthday.setText(getTodaysDate());
        signUpInfoBirthday.setTextColor(Restaurant.textColor);
        signUpInfoBirthday.setHintTextColor(Restaurant.textColor);
        signUpInfoBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        Button mainButton = findViewById(R.id.mainButtonSignUpInfo);
        mainButton.setBackgroundColor(Restaurant.themeColor);
        mainButton.setTextColor(Restaurant.textOnButtonColor);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = signUpInfoFirstName.getText().toString();
                String lastName = signUpInfoLastName.getText().toString();
                if ((TextUtils.isEmpty(firstName)) || (TextUtils.isEmpty(lastName)) || (TextUtils.isEmpty(gender)) || (TextUtils.isEmpty(date))) {
                    alert("Error", "Please Fill In All Fields");
                } else {
                    addInformation(firstName, lastName, gender, date);
                }
            }
        });
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    String date = makeDateString(day, month, year);
                    Button birthdayButton = findViewById(R.id.signUpInfoBirthday);
                    birthdayButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        String dtStart = getMonthFormat(month) + " " + day + " " + year;
        SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy");

        try {
            Date date = format.parse(dtStart);
            dateToString(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dtStart;
    }

    public void dateToString(Date passedDate) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String datetime = dateformat.format(passedDate);
        System.out.println("Current Date Time : " + datetime);
        date = datetime;
    }

    private String getMonthFormat(int month) {
        if (month == 1) {
            return "January";
        } else if (month == 2) {
            return "February";
        } else if (month == 3) {
            return "March";
        } else if (month == 4) {
            return "April";
        } else if (month == 5) {
            return "May";
        } else if (month == 6) {
            return "June";
        } else if (month == 7) {
            return "July";
        } else if (month == 8) {
            return "August";
        } else if (month == 9) {
            return "September";
        } else if (month == 10) {
            return "October";
        } else if (month == 11) {
            return "November";
        } else if (month == 12) {
            return "December";
        } else {
            return "January";
        }
    }

    public void openDatePicker() {
        datePickerDialog.show();
    }

    public void addInformation(String firstName, String lastName, String gender, String birthday) {
        String uid = mAuth.getInstance().getCurrentUser().getUid().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        System.out.println(uid);

        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).child("firstName").setValue(firstName);
        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).child("lastName").setValue(lastName);
        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).child("gender").setValue(gender);
        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).child("birthday").setValue(birthday);
        mDatabase.child("Apps").child(Restaurant.id).child("Users").child(uid).child("uid").setValue(uid);

        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignUpInfoActivity.this, RandomActivity.class));
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        gender = choice;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String choice = "Male";
        gender = choice;
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