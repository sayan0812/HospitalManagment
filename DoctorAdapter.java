package com.example.hospital;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

   private List<Doctor> doctorList;
   private Context context;

    public DoctorAdapter(List<Doctor> doctorList, Context context) {
        this.doctorList = doctorList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {

        Doctor doctor = doctorList.get(position);
        holder.name.setText(doctor.getName());
        holder.about.setText(doctor.getAbout());
        holder.spec.setText(String.join(", ", doctor.getSpec()));
        Glide.with(context).load(doctor.getImgUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("NAME", doctor.getName());
                intent.putExtra("ABOUT", doctor.getAbout());
                intent.putExtra("AGE", doctor.getAge());
                intent.putExtra("URL",doctor.getImgUrl());
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, about, spec;

        DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.DrName);
            about = itemView.findViewById(R.id.DrAbout);
            spec = itemView.findViewById(R.id.DrSpec);
        }
    }
}
