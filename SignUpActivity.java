package com.example.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String selectedUserType = "";
    private EditText emailInput, passwordInput, nameInput;
    private ImageView doctorCheckmark, patientCheckmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ImageView doctorImage = findViewById(R.id.doctor_image);
        ImageView patientImage = findViewById(R.id.patient_image);
        emailInput = findViewById(R.id.email_input);
        nameInput = findViewById(R.id.user_input);
        passwordInput = findViewById(R.id.password_input);
        doctorCheckmark = findViewById(R.id.doctor_checkmark);
        patientCheckmark = findViewById(R.id.patient_checkmark);

        // Hide checkmarks initially
        doctorCheckmark.setVisibility(View.GONE);
        patientCheckmark.setVisibility(View.GONE);

        doctorImage.setOnClickListener(view -> {
            selectedUserType = "doctor";
            doctorCheckmark.setVisibility(View.VISIBLE);
            patientCheckmark.setVisibility(View.GONE);
            Toast.makeText(this, "Doctor Selected", Toast.LENGTH_SHORT).show();
        });

        patientImage.setOnClickListener(view -> {
            selectedUserType = "patient";
            patientCheckmark.setVisibility(View.VISIBLE);
            doctorCheckmark.setVisibility(View.GONE);
            Toast.makeText(this, "Patient Selected", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.signup_button).setOnClickListener(view -> signUp());
    }

    private void signUp() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String name = nameInput.getText().toString();

        if (selectedUserType.isEmpty()) {
            Toast.makeText(this, "Please select Doctor or Patient", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty() || name.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        saveUserToFirestore(user);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Signup Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("userType", selectedUserType);
        userMap.put("name", user.getDisplayName());

        db.collection("users").document(user.getUid())
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    navigateToPortal(selectedUserType);
                })
                .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to Save User Data", Toast.LENGTH_SHORT).show());
    }

    private void navigateToPortal(String userType) {
        if (userType.equals("doctor")) {
            startActivity(new Intent(SignUpActivity.this, DoctorProfileActivity.class));
        } else {
            startActivity(new Intent(SignUpActivity.this, DoctorActivity.class));
        }
        finish();
    }
}
