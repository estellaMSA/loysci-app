package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TopicsMissionsResponse implements Parcelable {
    @SerializedName("workflow")
    Mission mission;

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mission, flags);
    }

    public TopicsMissionsResponse() {
    }

    protected TopicsMissionsResponse(Parcel in) {
        this.mission = in.readParcelable(Mission.class.getClassLoader());
    }

    public static final Parcelable.Creator<TopicsMissionsResponse> CREATOR = new Parcelable.Creator<TopicsMissionsResponse>() {
        @Override
        public TopicsMissionsResponse createFromParcel(Parcel source) {
            return new TopicsMissionsResponse(source);
        }

        @Override
        public TopicsMissionsResponse[] newArray(int size) {
            return new TopicsMissionsResponse[size];
        }
    };
}
