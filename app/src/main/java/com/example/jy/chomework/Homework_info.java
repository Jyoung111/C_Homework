package com.example.jy.chomework;

import android.os.Parcel;
import android.os.Parcelable;

public class Homework_info implements Parcelable {
    String homework_name;
    String d_day;
    String class_name;
    String now_progress;

    public Homework_info(String homework_name, String d_day, String class_name, String now_progress) {
        this.homework_name = homework_name;
        this.d_day = d_day;
        this.class_name = class_name;
        this.now_progress = now_progress;
    }

    protected Homework_info(Parcel in) {
        homework_name = in.readString();
        d_day = in.readString();
        class_name = in.readString();
        now_progress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(homework_name);
        dest.writeString(d_day);
        dest.writeString(class_name);
        dest.writeString(now_progress);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getClass_name() {
        return class_name;
    }

    public String getNow_progress() {
        return now_progress;
    }
}
