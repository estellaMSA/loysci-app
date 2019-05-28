package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Register implements Parcelable {
    private String docIdentificacion;
    private String avatar;
    private String nombre;
    private String apellido;
    private String apellido2;
    private String contrasena;
    private String correo;
    private String nombreUsuario;
    private String telefonoMovil;
    private boolean indContactoEmail;
    private boolean indContactoSms;
    private boolean indContactoNotificacion;

    public boolean isIndContactoEmail() {
        return indContactoEmail;
    }

    public void setIndContactoEmail(boolean indContactoEmail) {
        this.indContactoEmail = indContactoEmail;
    }

    public boolean isIndContactoSms() {
        return indContactoSms;
    }

    public void setIndContactoSms(boolean indContactoSms) {
        this.indContactoSms = indContactoSms;
    }

    public boolean isIndContactoNotificacion() {
        return indContactoNotificacion;
    }

    public void setIndContactoNotificacion(boolean indContactoNotificacion) {
        this.indContactoNotificacion = indContactoNotificacion;
    }

    public String getDocIdentificacion() {
        return docIdentificacion;
    }

    public void setDocIdentificacion(String docIdentificacion) {
        this.docIdentificacion = docIdentificacion;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Register() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docIdentificacion);
        dest.writeString(this.avatar);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.apellido2);
        dest.writeString(this.contrasena);
        dest.writeString(this.correo);
        dest.writeString(this.nombreUsuario);
        dest.writeString(this.telefonoMovil);
        dest.writeByte(this.indContactoEmail ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indContactoSms ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indContactoNotificacion ? (byte) 1 : (byte) 0);
    }

    protected Register(Parcel in) {
        this.docIdentificacion = in.readString();
        this.avatar = in.readString();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.apellido2 = in.readString();
        this.contrasena = in.readString();
        this.correo = in.readString();
        this.nombreUsuario = in.readString();
        this.telefonoMovil = in.readString();
        this.indContactoEmail = in.readByte() != 0;
        this.indContactoSms = in.readByte() != 0;
        this.indContactoNotificacion = in.readByte() != 0;
    }

    public static final Creator<Register> CREATOR = new Creator<Register>() {
        @Override
        public Register createFromParcel(Parcel source) {
            return new Register(source);
        }

        @Override
        public Register[] newArray(int size) {
            return new Register[size];
        }
    };
}