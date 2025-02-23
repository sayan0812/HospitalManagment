package com.example.hospital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class DoctorProfileActivity extends AppCompatActivity {

    private EditText name, specialization, age, fees, about;
    private ImageView profileImage;
    private Button saveButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference storageReference;
    private Uri imageUri;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portal_layout);

        name = findViewById(R.id.name_input);
        specialization = findViewById(R.id.spec_input);
        age = findViewById(R.id.age_input);
        about = findViewById(R.id.about_input);
        fees = findViewById(R.id.fees_input);
        profileImage = findViewById(R.id.profile);
        saveButton = findViewById(R.id.updateBtn);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadDoctorData();


        saveButton.setOnClickListener(v -> saveDoctorData());
    }

    private void loadDoctorData() {
        String userId = auth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(userId);
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                name.setText(documentSnapshot.getString("name"));
                specialization.setText(documentSnapshot.getString("specialization"));
                age.setText(documentSnapshot.getString("age"));
                fees.setText(documentSnapshot.getString("fees"));
                String imageUrl = documentSnapshot.getString("imgUrl");
                Glide.with(this).load(imageUrl).into(profileImage);
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show());
    }

    private void saveDoctorData() {
        String userId = auth.getCurrentUser().getUid();
        DocumentReference docRef = db.collection("users").document(userId);

        Map<String, Object> doctorData = new HashMap<>();
        doctorData.put("role", "doctor");
        doctorData.put("name", name.getText().toString());
        doctorData.put("specialization", specialization.getText().toString());
        doctorData.put("age", age.getText().toString());
        doctorData.put("fees", fees.getText().toString());
        doctorData.put("imgUrl", "https://cdn-icons-png.flaticon.com/512/9193/9193824.png");

        docRef.set(doctorData).addOnSuccessListener(aVoid ->
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
        ).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
        );
    }
}
