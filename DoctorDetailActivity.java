package com.example.hospital;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class DoctorDetailActivity extends AppCompatActivity {

    private TextView name,about,spec,age;
    private ImageView profilePic,back;
    private CalendarView calendarView;
    private TextView dateTextView;
    private Button appointmentBtn;
    
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_detail);

        name = findViewById(R.id.drName);
        about = findViewById(R.id.drAbout);
        spec = findViewById(R.id.spec);
        back = findViewById(R.id.back);
        age = findViewById(R.id.age);
        profilePic = findViewById(R.id.drPic);
        appointmentBtn = findViewById(R.id.bookAppointmentButton);
        


        String nam = getIntent().getStringExtra("NAME");
        String abo = getIntent().getStringExtra("ABOUT");
        String ag = getIntent().getStringExtra("AGE");
//        String sp = getIntent().getStringExtra("SPEC");
        String prpic = getIntent().getStringExtra("URL");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Appointment booking

        appointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingForm();
            }
        });



        name.setText(nam);
        about.setText(abo);
//        spec.setText(sp);
        age.setText(ag);
        Glide.with(this).load(prpic).into(profilePic);

    }

    private void showBookingForm(){
        Dialog bookingDialog = new Dialog(DoctorDetailActivity.this);
        bookingDialog.setContentView(R.layout.booking_form);

        EditText nameEdt = bookingDialog.findViewById(R.id.appointment_name);
        CalendarView calendar = bookingDialog.findViewById(R.id.calendar);
        CheckBox videoCallCheck = bookingDialog.findViewById(R.id.videoCheck);
        CheckBox clinicCheck = bookingDialog.findViewById(R.id.clinCheck);
        EditText contactEdt = bookingDialog.findViewById(R.id.appointment_number);
        EditText emailEdt = bookingDialog.findViewById(R.id.appointment_email);
        EditText messageEdt = bookingDialog.findViewById(R.id.appointment_message);
        Button submit = bookingDialog.findViewById(R.id.submit);
        WindowManager.LayoutParams params = bookingDialog.getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
        params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.85);
        bookingDialog.getWindow().setAttributes(params);

        calendar.setMinDate(System.currentTimeMillis() - 1000);

        final String[] selectedDate = {""};

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate[0] = dayOfMonth + "/" + (month + 1) + "/" + year;
        });

        Calendar today = Calendar.getInstance();
        selectedDate[0] = today.get(Calendar.DAY_OF_MONTH) + "/"+
                (today.get(Calendar.MONTH) + 1)+"/"+
                today.get(Calendar.YEAR);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdt.getText().toString();
                String contact = contactEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String appointmentType = videoCallCheck.isChecked() ? "Video Call" : "In-Clinic";
                String message = messageEdt.getText().toString();
//                String dateTextViewDialog = dateTextView.getText().toString();
                String fees = "Rs. 500";

                boolean isVideoCall = videoCallCheck.isChecked();
                boolean isClinic = clinicCheck.isChecked();
                
                if (name.isEmpty() || contact.isEmpty() || email.isEmpty() || (!isVideoCall && !isClinic)){
                    Toast.makeText(DoctorDetailActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(DoctorDetailActivity.this, AppointmentProofActivity.class);
                    intent.putExtra("PATIENT_NAME", name);
                    intent.putExtra("PHONE", contact);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("APPOINTMENT_TYPE", appointmentType);
//                    intent.putExtra("APPOINTMENT_DATE", appointmentDate);
                    intent.putExtra("FEES", fees);
//                    intent.putExtra("DOCTOR_NAME", name.getText().toString());

                    bookingDialog.dismiss();
                    startActivity(intent);
                }
            }
        });
        bookingDialog.show();

        
    }
}