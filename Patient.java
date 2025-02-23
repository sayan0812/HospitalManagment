package com.example.hospital;

public class Patient {

    private String name;
    private String date;
    private String fees;

    public Patient(String name, String date, String fees) {
        this.name = name;
        this.date = date;
        this.fees = fees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }
}
