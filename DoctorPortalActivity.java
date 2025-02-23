package com.example.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class  DoctorPortalActivity extends AppCompatActivity {

    private Button viewProfile, viewPatients, viewIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_portal);

        viewProfile = findViewById(R.id.btnViewProfile);
        viewPatients = findViewById(R.id.btnViewPatients);
        viewIncome = findViewById(R.id.btnViewIncome);

        viewProfile.setOnClickListener(v -> {
            startActivity(new Intent(DoctorPortalActivity.this, DoctorProfileActivity.class));
        });

        viewPatients.setOnClickListener(v -> {
            startActivity(new Intent(DoctorPortalActivity.this, DoctorPatientsActivity.class));
        });

        viewIncome.setOnClickListener(v -> {
            startActivity(new Intent(DoctorPortalActivity.this, DoctorIncomeActivity.class));
        });
    }
}