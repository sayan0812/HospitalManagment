package com.example.hospital;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorPatientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PatientAdapter patientAdapter;
    private List<Patient> patientList;
    private FirebaseFirestore db;
    private String doctorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_patients);


        recyclerView = findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        patientList = new ArrayList<>();
        patientAdapter = new PatientAdapter(patientList);
        recyclerView.setAdapter(patientAdapter);

        db = FirebaseFirestore.getInstance();

        // Assume doctorId is passed via Intent
        doctorId = getIntent().getStringExtra("DOCTOR_ID");

        loadPatientData();
    }

    private void loadPatientData() {
        db.collection("appointments")
                .whereEqualTo("doctorId", doctorId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        patientList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String patientName = document.getString("patientName");
                            String date = document.getString("date");
                            String fees = document.getString("fees");
                            patientList.add(new Patient(patientName, date, fees));
                        }
                        patientAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load patients", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore Error", "Error getting patients", task.getException());
                    }
                });
    }
}