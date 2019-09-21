package com.achs.voilence_protector.util;

//import com.google.gson.annotations.SerializedName;

public class Contact {
//    @SerializedName("id")
    private String name;
//    @SerializedName("title")
    private String phone;

    public Contact() {

    }

    public Contact(String phone) {
        this.phone = phone;
    }
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }



    public String getPhone() {
        return phone;
    }
}