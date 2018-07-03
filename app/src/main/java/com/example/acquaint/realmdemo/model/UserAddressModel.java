package com.example.acquaint.realmdemo.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserAddressModel extends RealmObject{

    @PrimaryKey
    private int id;

    private String address;

    private RealmList<String> phoneNumber = new RealmList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RealmList<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(RealmList<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
