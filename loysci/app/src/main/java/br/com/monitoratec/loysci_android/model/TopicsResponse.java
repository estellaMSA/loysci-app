package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicsResponse implements Parcelable {
    @SerializedName("retorno")
    List<Topic> topicList;

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.topicList);
    }

    public TopicsResponse() {
    }

    protected TopicsResponse(Parcel in) {
        this.topicList = in.createTypedArrayList(Topic.CREATOR);
    }

    public static final Parcelable.Creator<TopicsResponse> CREATOR = new Parcelable.Creator<TopicsResponse>() {
        @Override
        public TopicsResponse createFromParcel(Parcel source) {
            return new TopicsResponse(source);
        }

        @Override
        public TopicsResponse[] newArray(int size) {
            return new TopicsResponse[size];
        }
    };
}
