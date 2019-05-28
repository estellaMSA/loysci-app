package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Notification implements Parcelable {
    private String idNotificacion;
    private String imagen;
    private String encabezado;
    private String texto;
    private String indObjetivo;
    private Challenge mision;
    private Reward premio;
    private Promotion promocion;
    private boolean visto;


    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getIndObjetivo() {
        return indObjetivo;
    }

    public void setIndObjetivo(String indObjetivo) {
        this.indObjetivo = indObjetivo;
    }


    public Challenge getMision() {
        return mision;
    }

    public void setMision(Challenge mision) {
        this.mision = mision;
    }

    public Reward getPremio() {
        return premio;
    }

    public void setPremio(Reward premio) {
        this.premio = premio;
    }

    public Promotion getPromocion() {
        return promocion;
    }

    public void setPromocion(Promotion promocion) {
        this.promocion = promocion;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idNotificacion);
        dest.writeString(this.imagen);
        dest.writeString(this.encabezado);
        dest.writeString(this.texto);
        dest.writeString(this.indObjetivo);
        dest.writeByte(this.visto ? (byte) 1 : (byte) 0);
    }

    public Notification() {
    }

    protected Notification(Parcel in) {
        this.idNotificacion = in.readString();
        this.imagen = in.readString();
        this.encabezado = in.readString();
        this.texto = in.readString();
        this.indObjetivo = in.readString();
        this.visto = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
