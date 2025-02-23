package com.example.hospital;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AppointmentProofActivity extends AppCompatActivity {
    private TextView patientNameText, doctorNameText, phoneText, emailText, appointmentTypeText, feesText, dateText;
    private Button downloadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_appointment_proof);


        patientNameText = findViewById(R.id.patientNameText);
        doctorNameText = findViewById(R.id.doctorNameText);
        phoneText = findViewById(R.id.phoneText);
        emailText = findViewById(R.id.emailText);
        appointmentTypeText = findViewById(R.id.appointmentTypeText);
        feesText = findViewById(R.id.feesText);
        dateText = findViewById(R.id.dateText);
        downloadBtn = findViewById(R.id.downloadBtn);

        Intent intent = getIntent();

        patientNameText.setText(intent.getStringExtra("PATIENT_NAME"));
        doctorNameText.setText(intent.getStringExtra("DOCTOR_NAME"));
        phoneText.setText(intent.getStringExtra("PHONE"));
        emailText.setText(intent.getStringExtra("EMAIL"));
        appointmentTypeText.setText(intent.getStringExtra("APPOINTMENT_TYPE"));
        feesText.setText(intent.getStringExtra("FEES"));
        dateText.setText(intent.getStringExtra("APPOINTMENT_DATE"));

        downloadBtn.setOnClickListener(v -> downloadProof());

    }

    private void downloadProof() {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setTextSize(14);
        canvas.drawText("Appointment Proof", 10, 25, paint);
        canvas.drawText("Patient Name: " + patientNameText.getText().toString(), 10, 50, paint);
        canvas.drawText("Doctor Name: " + doctorNameText.getText().toString(), 10, 75, paint);
        canvas.drawText("Phone: " + phoneText.getText().toString(), 10, 100, paint);
        canvas.drawText("Email: " + emailText.getText().toString(), 10, 125, paint);
        canvas.drawText("Appointment Type: " + appointmentTypeText.getText().toString(), 10, 150, paint);
        canvas.drawText("Fees: " + feesText.getText().toString(), 10, 175, paint);
        canvas.drawText("Date: " + dateText.getText().toString(), 10, 200, paint);

        document.finishPage(page);

        // Save using MediaStore API for Android 10 and above
        String fileName = "AppointmentProof_" + System.currentTimeMillis() + ".pdf";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

            Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

            try {
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                document.writeTo(outputStream);
                outputStream.close();
                Toast.makeText(this, "Downloaded to Downloads folder: " + fileName, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // For Android 9 and below (<= API 28)
            String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(directoryPath, fileName);

            try {
                FileOutputStream fos = new FileOutputStream(file);
                document.writeTo(fos);
                fos.close();
                Toast.makeText(this, "Downloaded to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        document.close();
    }
}