package com.kye.mycinema.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewItem implements Parcelable {
    String name,contents;

    public ReviewItem(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

    protected ReviewItem(Parcel in) {
        name = in.readString();
        contents = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.contents);

    }
}
