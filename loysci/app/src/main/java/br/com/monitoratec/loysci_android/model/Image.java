package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String urlImagen;

    public String getImagen() {
        return urlImagen;
    }

    public void setImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.urlImagen);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.urlImagen = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
