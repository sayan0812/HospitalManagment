package com.example.hospital;

import java.util.List;

public class Doctor {
    private String ImgUrl;
    private String name;
    private String about;
    private String age;
    private List<String> spec;

    public Doctor(){}

    public Doctor(String name, String about, String age, List<String> spec, String ImgUrl) {
        this.name = name;
        this.about = about;
        this.age = age;
        this.spec = spec;
        this.ImgUrl = ImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getSpec() {
        return spec;
    }

    public void setSpec(List<String> spec) {
        this.spec = spec;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
