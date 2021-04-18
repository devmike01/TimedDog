package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 4/18/21.


import android.os.Parcel;
import android.os.Parcelable;

public class BeginCountObject implements Parcelable {
    private String logoutActivityClassName;
    private long timeOutInMillis;

    protected BeginCountObject(){}

    private BeginCountObject(Parcel in) {
        logoutActivityClassName = in.readString();
        timeOutInMillis = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(logoutActivityClassName);
        dest.writeLong(timeOutInMillis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeginCountObject> CREATOR = new Creator<BeginCountObject>() {
        @Override
        public BeginCountObject createFromParcel(Parcel in) {
            return new BeginCountObject(in);
        }

        @Override
        public BeginCountObject[] newArray(int size) {
            return new BeginCountObject[size];
        }
    };

    public long getTimeOutInMillis() {
        return timeOutInMillis;
    }

    public String getLogoutActivityClassName() {
        return logoutActivityClassName;
    }

    public void setLogoutActivityClassName(String logoutActivityClassName) {
        this.logoutActivityClassName = logoutActivityClassName;
    }

    public void setTimeOutInMillis(long timeOutInMillis) {
        this.timeOutInMillis = timeOutInMillis;
    }
}
