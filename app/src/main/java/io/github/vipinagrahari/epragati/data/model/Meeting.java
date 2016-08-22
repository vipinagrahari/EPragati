package io.github.vipinagrahari.epragati.data.model;

/**
 * Created by vivek on 21/8/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Meeting implements Parcelable {

    public static final Parcelable.Creator<Meeting> CREATOR
            = new Parcelable.Creator<Meeting>() {
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("president")
    @Expose
    private String president;
    @SerializedName("attended_by")
    @Expose
    private String attendedBy;
    @SerializedName("points")
    @Expose
    private List<String> points = new ArrayList<String>();

    public Meeting() {

        // Empty Constructor

    }


    private Meeting(Parcel in) {
        date = in.readString();
        time = in.readString();
        location = in.readString();
        president = in.readString();
        attendedBy = in.readString();
        in.readStringList(points);
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return The president
     */
    public String getPresident() {
        return president;
    }

    /**
     * @param president The president
     */
    public void setPresident(String president) {
        this.president = president;
    }

    /**
     * @return The attendedBy
     */
    public String getAttendedBy() {
        return attendedBy;
    }

    /**
     * @param attendedBy The attended_by
     */
    public void setAttendedBy(String attendedBy) {
        this.attendedBy = attendedBy;
    }

    /**
     * @return The points
     */
    public List<String> getPoints() {
        return points;
    }

    /**
     * @param points The points
     */
    public void setPoints(List<String> points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(location);
        dest.writeString(president);
        dest.writeString(attendedBy);
        dest.writeStringList(points);

    }


}