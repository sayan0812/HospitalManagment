package com.example.hospital;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class AddDoctorActivity extends AppCompatActivity {

    private EditText nameInput, aboutInput, ageInput, imgUrlInput, specInput;
    private Button addDoctorBtn;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        nameInput = findViewById(R.id.doctor_name);
        aboutInput = findViewById(R.id.doctor_about);
        ageInput = findViewById(R.id.doctor_age);
        imgUrlInput = findViewById(R.id.doctor_img_url);
        specInput = findViewById(R.id.doctor_spec); // Enter specialties comma-separated

        addDoctorBtn = findViewById(R.id.btn_add_doctor);

        db = FirebaseFirestore.getInstance();

        addDoctorBtn.setOnClickListener(v -> addDoctor());
    }

    private void addDoctor() {
        String name = nameInput.getText().toString().trim();
        String about = aboutInput.getText().toString().trim();
        String age = ageInput.getText().toString().trim();
        String imgUrl = imgUrlInput.getText().toString().trim();
        String specText = specInput.getText().toString().trim();

        if (name.isEmpty() || about.isEmpty() || age.isEmpty() || imgUrl.isEmpty() || specText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert specialties string to List
        String[] specArray = specText.split(",");
        Doctor doctor = new Doctor(name, about, age, Arrays.asList(specArray), imgUrl);

        db.collection("doctors")
                .add(doctor)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Doctor Added", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to DoctorActivity
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to Add Doctor", Toast.LENGTH_SHORT).show());
    }
}
