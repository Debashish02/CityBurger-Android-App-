package com.example.foodorderingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    private EditText username;
    private Button login;

    ActivitySigninBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        username = findViewById(R.id.goTouserName);
        login = findViewById(R.id.goTologin);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        binding.goTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.goToemailAddress.getText().toString().trim();
                String password = binding.goToPassword.getText().toString().trim();

                String username = binding.goTouserName.getText().toString().trim();
                if (!validateemails() | !validatepasswords() | !validateusername()) {
                    return;
                }


                /*startActivity(new Intent(SignInActivity.this, MainActivity.class));*/
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.putExtra("keyname", username);
                startActivity(intent);
                finish();
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.goToemailAddress.getText().toString();
                progressDialog.setTitle("Sending Mail");
                progressDialog.show();
                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
                finish();
            }
        });

    }

    private Boolean validateusername() {
        String val = binding.goTouserName.getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            binding.goTouserName.setError("Full Name Required");
            return false;
        } else if (val.length() >= 15) {
            binding.goTouserName.setError("Name too long");
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            binding.goTouserName.setError("White spaces not allowed");
            return false;
        } else {
            binding.goTouserName.setError(null);
            return true;
        }
    }

    private Boolean validateemails() {
        String val = binding.goToemailAddress.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            binding.goToemailAddress.setError("Email Required");
            return false;
        } else if (!val.matches(emailPattern)) {
            binding.goToemailAddress.setError("Invalid email address");
            return false;
        } else {
            binding.goToemailAddress.setError(null);
            return true;
        }
    }

    private Boolean validatepasswords() {
        String val = binding.goToPassword.getText().toString();
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
            binding.goToPassword.setError("Password Required");
            return false;
        } else if (!val.matches(passwordVal)) {
            binding.goToPassword.setError("Password should contain atleast one special character,uppercase,lowercase,number");
            return false;
        } else {
            binding.goToPassword.setError(null);
            return true;
        }
    }
}