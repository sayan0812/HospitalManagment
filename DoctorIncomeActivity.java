package com.example.hospital;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DoctorIncomeActivity extends AppCompatActivity {

    private TextView totalIncomeTextView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_income);

        totalIncomeTextView = findViewById(R.id.totalIncomeTextView);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadDoctorIncome();
    }

    private void loadDoctorIncome() {
        String doctorId = auth.getCurrentUser().getUid();

        db.collection("appointments")
                .whereEqualTo("doctorId", doctorId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalIncome = 0;
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String feesStr = document.getString("fees");
                        try {
                            int fees = Integer.parseInt(feesStr);
                            totalIncome += fees;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    totalIncomeTextView.setText("Total Income: ₹" + totalIncome);
                })
                .addOnFailureListener(e -> Toast.makeText(DoctorIncomeActivity.this, "Failed to load income", Toast.LENGTH_SHORT).show());
    }
}
