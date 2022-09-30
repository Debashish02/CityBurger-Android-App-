package com.example.foodorderingapp.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivityFeedbackBinding;
import com.example.foodorderingapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackActivity extends AppCompatActivity {

    @NonNull
    ActivityFeedbackBinding binding;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.fullNamef.getText().toString();
                String email = binding.emailf.getText().toString().trim();
                String password = binding.Passwordf.getText().toString();
                String review = binding.review.getText().toString();

                if (!validatenamef() | !validateemailf() | !validatepasswordf() | !validatereview()) {
                    return;
                }

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
                                progressDialog.cancel();

                                firebaseFirestore.collection("feedback")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(name, email, password, review));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {
                                                      Toast.makeText(FeedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                      progressDialog.cancel();
                                                  }
                                              }

                        );

            }
        });


        bottomNavigator();
    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void bottomNavigator() {

        LinearLayout homeBtn = findViewById(R.id.homebtn);
        LinearLayout feedback = findViewById(R.id.feedbackbtn);


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, MainActivity.class));
                finish();
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedbackActivity.this, FeedbackActivity.class));
                finish();
            }
        });
    }

    private Boolean validatenamef() {
        String val = binding.fullNamef.getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            binding.fullNamef.setError("Full Name Required");
            return false;
        } else if (val.length() >= 15) {
            binding.fullNamef.setError("Name too long");
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            binding.fullNamef.setError("White spaces not allowed");
            return false;
        } else {
            binding.fullNamef.setError(null);
            return true;
        }
    }

    private Boolean validateemailf() {
        String val = binding.emailf.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            binding.emailf.setError("Email Required");
            return false;
        } else if (!val.matches(emailPattern)) {
            binding.emailf.setError("Invalid email address");
            return false;
        } else {
            binding.emailf.setError(null);
            return true;
        }
    }

    private Boolean validatepasswordf() {
        String val = binding.Passwordf.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            binding.Passwordf.setError("Password Required");
            return false;
        } else if (!val.matches(passwordVal)) {
            binding.Passwordf.setError("Password is too weak");
            return false;
        } else {
            binding.Passwordf.setError(null);
            return true;
        }
    }

    private Boolean validatereview() {
        String val = binding.review.getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            binding.review.setError("Review Required");
            return false;
        } else {
            binding.review.setError(null);
            return true;
        }
    }

}