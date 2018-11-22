package com.example.jy.chomework;

import android.os.Parcel;
import android.os.Parcelable;

public class Homework_info implements Parcelable {
    String homework_name;
    String d_day;
    public Homework_info(String name, String day){
        this.homework_name = name;
        this.d_day = day;
    }

    protected Homework_info(Parcel in) {
        homework_name = in.readString();
        d_day = in.readString();
    }

    public static final Creator<Homework_info> CREATOR = new Creator<Homework_info>() {
        @Override
        public Homework_info createFromParcel(Parcel in) {
            return new Homework_info(in);
        }

        @Override
        public Homework_info[] newArray(int size) {
            return new Homework_info[size];
        }
    };

    public String getHomework_name() {
        return homework_name;
    }

    public String getD_day() {
        return d_day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(homework_name);
        dest.writeString(d_day);
    }


}
