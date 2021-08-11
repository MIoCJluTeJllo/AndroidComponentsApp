package com.example.stylist;

import java.util.Arrays;
import java.util.List;

//класс для записи
public class AppointmentItem {
    String datetime;
    String name;
    String phone;
    String duration;
    List<String> services;
    String hair_length;
    String comment;
    String cost;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = Arrays.asList(services);
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getHair_length() {
        return hair_length;
    }

    public void setHair_length(String hair_length) {
        this.hair_length = hair_length;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
