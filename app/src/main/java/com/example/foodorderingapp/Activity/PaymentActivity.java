package com.example.foodorderingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodorderingapp.R;
import com.example.foodorderingapp.databinding.ActivityPaymentBinding;
import com.example.foodorderingapp.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.AuthResult;

public class PaymentActivity extends AppCompatActivity {

    private Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        payment = findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(PaymentActivity.this, "Your Payment is Successfull", Toast.LENGTH_LONG).show();
            }
        });

    }

}