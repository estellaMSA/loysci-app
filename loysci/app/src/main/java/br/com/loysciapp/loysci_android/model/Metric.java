package br.com.loysciapp.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Metric implements Parcelable {
    private String idMetrica;
    private String nombre;
    private String medida;

    public String getIdMetrica() {
        return idMetrica;
    }

    public void setIdMetrica(String idMetrica) {
        this.idMetrica = idMetrica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMetrica);
        dest.writeString(this.nombre);
        dest.writeString(this.medida);
    }

    public Metric() {
    }

    protected Metric(Parcel in) {
        this.idMetrica = in.readString();
        this.nombre = in.readString();
        this.medida = in.readString();
    }

    public static final Parcelable.Creator<Metric> CREATOR = new Parcelable.Creator<Metric>() {
        @Override
        public Metric createFromParcel(Parcel source) {
            return new Metric(source);
        }

        @Override
        public Metric[] newArray(int size) {
            return new Metric[size];
        }
    };
}
