package com.example.foodorderingapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopulatList;
    private TextView name, mobile, email, password;

    ActivityRegisterBinding binding;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        progressDialog = new ProgressDialog(this);


        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.fullName.getText().toString();
                String mobile = binding.mobileNo.getText().toString();
                String email = binding.emailAddress.getText().toString().trim();
                String password = binding.Password.getText().toString();

                if (!validatename() | !validatemobile() | !validateemail() | !validatepassword()) {
                    return;
                }


                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                                progressDialog.cancel();

                                firebaseFirestore.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(name, mobile, email));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                                                  @Override
                                                  public void onFailure(@NonNull Exception e) {
                                                      Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                      progressDialog.cancel();
                                                  }
                                              }

                        );

            }
        });


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private Boolean validatename() {
        String val = binding.fullName.getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            binding.fullName.setError("Full Name Required");
            return false;
        } else if (val.length() >= 15) {
            binding.fullName.setError("Name too long");
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            binding.fullName.setError("White spaces not allowed");
            return false;
        } else {
            binding.fullName.setError(null);
            return true;
        }
    }

    private Boolean validatemobile() {
        String val = binding.mobileNo.getText().toString();
        /*String nostring = "[a-zA-Z._-]+@[a-z]+\\.+[a-z]+";*/
        if (val.isEmpty()) {
            binding.mobileNo.setError("Mobile No Required");
            return false;
        }/*else if (!val.matches(nostring)) {
            binding.mobileNo.setError("Invalid Mobile No");
            return false;*/ else {
            binding.mobileNo.setError(null);
            return true;
        }
    }

    private Boolean validateemail() {
        String val = binding.emailAddress.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            binding.emailAddress.setError("Email Required");
            return false;
        } else if (!val.matches(emailPattern)) {
            binding.emailAddress.setError("Invalid email address");
            return false;
        } else {
            binding.emailAddress.setError(null);
            return true;
        }
    }

    private Boolean validatepassword() {
        String val = binding.Password.getText().toString();
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
            binding.Password.setError("Password Required");
            return false;
        } else if (!val.matches(passwordVal)) {
            binding.Password.setError("Password should contain atleast one special character,uppercase,lowercase,number");
            return false;
        } else {
            binding.Password.setError(null);
            return true;
        }
    }


}