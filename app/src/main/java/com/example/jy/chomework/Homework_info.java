package com.example.jy.chomework;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Homework_info implements Parcelable, Comparable<Homework_info>{
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(Homework_info other) {
        long calDate =0 ,calDate1=0;
        Date FirstDate,FirstDate1;
        SimpleDateFormat format;

        try {
            format = new SimpleDateFormat("yyyy.MM.dd a K:mm", Locale.KOREA);
            FirstDate = format.parse(this.d_day);
            FirstDate1 = format.parse(other.d_day);
            calDate = FirstDate.getTime();
            calDate1 = FirstDate1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.compare(calDate1, calDate);
    }
}
