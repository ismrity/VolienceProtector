package com.achs.voilence_protector.util;

import java.util.ArrayList;

public class Police {
    ArrayList<Data> data;
    String status;

    public ArrayList<Police.Data> getData() {

        return data;
    }

    public String getStatus()
    {
        return status;
    }

    public class Data {
        String police;
        String latitude;
        String longitude;
        String phone;

        public void setPolice(String police) {
            this.police = police;
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

        public String getPolice() {
            return police;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }

}




