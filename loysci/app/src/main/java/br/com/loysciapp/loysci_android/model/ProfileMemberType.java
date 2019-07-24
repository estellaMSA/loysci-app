package br.com.loysciapp.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ProfileMemberType implements Parcelable {
    private String idTipo;
    private String nombre;

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idTipo);
        dest.writeString(this.nombre);
    }

    public ProfileMemberType() {
    }

    protected ProfileMemberType(Parcel in) {
        this.idTipo = in.readString();
        this.nombre = in.readString();
    }

    public static final Parcelable.Creator<ProfileMemberType> CREATOR = new Parcelable.Creator<ProfileMemberType>() {
        @Override
        public ProfileMemberType createFromParcel(Parcel source) {
            return new ProfileMemberType(source);
        }

        @Override
        public ProfileMemberType[] newArray(int size) {
            return new ProfileMemberType[size];
        }
    };
}
