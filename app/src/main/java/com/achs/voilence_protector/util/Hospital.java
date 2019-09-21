package com.achs.voilence_protector.util;


import java.util.ArrayList;

public class Hospital {

    ArrayList<Data> data;
    String status;

    public ArrayList<Data> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public class Data {
        String hospital;
        String latitude;
        String longitude;
        String phone;
        public String getHospital() {
            return hospital;
        }


        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }

}



