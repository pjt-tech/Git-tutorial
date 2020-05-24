package com.kye.mycinema.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewItem implements Parcelable {

    //한줄평 정보를 담고 있는 객체 intent로 넘겨줘야하기때문에 parcelable을 구현하고있음

    String name,contents,time;

    public ReviewItem(String name, String contents,String time) {
        this.name = name;
        this.contents = contents;
        this.time = time;
    }

    protected ReviewItem(Parcel in) {
        name = in.readString();
        contents = in.readString();
        time = in.readString();
    }

    public static final Creator<ReviewItem> CREATOR = new Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(int size) {
            return new ReviewItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.contents);
        dest.writeString(this.time);
    }
}
