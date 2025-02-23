package com.example.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDashboardActivity extends AppCompatActivity {

    private Button viewDoctorsBtn, myAppointmentsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        viewDoctorsBtn = findViewById(R.id.viewDoctorsBtn);
        myAppointmentsBtn = findViewById(R.id.myAppointmentsBtn);

        viewDoctorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, DoctorActivity.class));
            }
        });

        myAppointmentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientDashboardActivity.this, PatientAppointmentsActivity.class));
            }
        });
    }
}
