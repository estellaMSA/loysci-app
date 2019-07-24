package br.com.loysciapp.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MissionsActivityResponse implements Parcelable {
    @SerializedName("missao")
    Challenge challenge;

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.challenge, flags);
    }

    public MissionsActivityResponse() {
    }

    protected MissionsActivityResponse(Parcel in) {
        this.challenge = in.readParcelable(Challenge.class.getClassLoader());
    }

    public static final Creator<MissionsActivityResponse> CREATOR = new Creator<MissionsActivityResponse>() {
        @Override
        public MissionsActivityResponse createFromParcel(Parcel source) {
            return new MissionsActivityResponse(source);
        }

        @Override
        public MissionsActivityResponse[] newArray(int size) {
            return new MissionsActivityResponse[size];
        }
    };
}
