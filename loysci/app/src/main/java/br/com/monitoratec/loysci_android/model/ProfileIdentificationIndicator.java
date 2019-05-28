package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ProfileIdentificationIndicator implements Parcelable {
    private String idIdentificacion;
    private String nombre;

    public String getIdIdentificacion() {
        return idIdentificacion;
    }

    public void setIdIdentificacion(String idIdentificacion) {
        this.idIdentificacion = idIdentificacion;
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
        dest.writeString(this.idIdentificacion);
        dest.writeString(this.nombre);
    }

    public ProfileIdentificationIndicator() {
    }

    protected ProfileIdentificationIndicator(Parcel in) {
        this.idIdentificacion = in.readString();
        this.nombre = in.readString();
    }

    public static final Parcelable.Creator<ProfileIdentificationIndicator> CREATOR = new Parcelable.Creator<ProfileIdentificationIndicator>() {
        @Override
        public ProfileIdentificationIndicator createFromParcel(Parcel source) {
            return new ProfileIdentificationIndicator(source);
        }

        @Override
        public ProfileIdentificationIndicator[] newArray(int size) {
            return new ProfileIdentificationIndicator[size];
        }
    };
}
