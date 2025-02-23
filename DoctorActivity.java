package com.example.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private RecyclerView doctorRecyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private FirebaseFirestore db;
    private FloatingActionButton addDr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor);

        addDr = findViewById(R.id.addDoctorFab);
        doctorRecyclerView = findViewById(R.id.doctoListView);
        doctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        doctorList = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(doctorList, this);
        doctorRecyclerView.setAdapter(doctorAdapter);
        db = FirebaseFirestore.getInstance();

        addDr.setOnClickListener(v -> {
            startActivity(new Intent(DoctorActivity.this, AddDoctorActivity.class));
        });

        // Initial Data Load
        loadDoctorData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDoctorData(); // Load data every time you return to this activity
    }

    private void loadDoctorData() {
        CollectionReference doctorRef = db.collection("doctors");

        doctorRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                doctorList.clear(); // Clear list before adding new data
                for (DocumentSnapshot document : task.getResult()) {
                    Doctor doctor = document.toObject(Doctor.class);
                    if (doctor != null) {
                        doctorList.add(doctor);
                    }
                }
                doctorAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Failed to load data: " + task.getException(), Toast.LENGTH_SHORT).show();
                Log.e("Firestore Error", "Error getting documents", task.getException());
            }
        });
    }
}
