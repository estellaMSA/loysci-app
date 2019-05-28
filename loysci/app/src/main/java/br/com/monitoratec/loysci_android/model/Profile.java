package br.com.monitoratec.loysci_android.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Profile implements Parcelable {
    private String idMiembro;
    private String docIdentificacion;
    private String avatar;
    private String nombre;
    private String apellido;
    private String apellido2;
    private String contrasena;
    private String correo;
    private String nombreUsuario;
    private String direccion;
    private String ciudadResidencia;
    private String estadoResidencia;
    private String paisResidencia;
    private String codigoPostal;
    private String telefonoMovil;
    private Long fechaNacimiento;
    private String indGenero;
    private boolean indContactoEmail;
    private boolean indContactoSms;
    private boolean indContactoNotificacion;
    private boolean indContactoEstado;
    private String indEstadoCivil;
    private String frecuenciaCompra;
    private String indEducacion;
    private float ingresoEconomico;
    private boolean indHijos;
    private String comentarios;
    private Boolean indPolitica;
    private long numVersion;
    private String fechaIngreso;
    private ProfileIdentificationIndicator indDocIdentificacion;
    private ProfileMemberType tipoMiembro;
    private List<ProfileDynamicAttribute> atributoDinamicos;
    private boolean indCambioPass;

    public String getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(String idMiembro) {
        this.idMiembro = idMiembro;
    }

    public String getDocIdentificacion() {
        if (docIdentificacion != null)
            return docIdentificacion;
        else
            return "";
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
        if (nombre != null)
            return nombre;
        else
            return "";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        if (apellido != null)
            return apellido;
        else
            return "";
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
        if (correo != null)
            return correo;
        else return "";
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getEstadoResidencia() {
        return estadoResidencia;
    }

    public void setEstadoResidencia(String estadoResidencia) {
        this.estadoResidencia = estadoResidencia;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefonoMovil() {
        if (telefonoMovil != null)
            return telefonoMovil;
        else
            return "";
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIndGenero() {
        if (indGenero != null)
            return indGenero;
        else
            return "";
    }

    public void setIndGenero(String indGenero) {
        this.indGenero = indGenero;
    }

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

    public boolean isIndContactoEstado() {
        return indContactoEstado;
    }

    public void setIndContactoEstado(boolean indContactoEstado) {
        this.indContactoEstado = indContactoEstado;
    }

    public String getIndEstadoCivil() {
        return indEstadoCivil;
    }

    public void setIndEstadoCivil(String indEstadoCivil) {
        this.indEstadoCivil = indEstadoCivil;
    }

    public String getFrecuenciaCompra() {
        return frecuenciaCompra;
    }

    public void setFrecuenciaCompra(String frecuenciaCompra) {
        this.frecuenciaCompra = frecuenciaCompra;
    }

    public String getIndEducacion() {
        return indEducacion;
    }

    public void setIndEducacion(String indEducacion) {
        this.indEducacion = indEducacion;
    }

    public float getIngresoEconomico() {
        return ingresoEconomico;
    }

    public void setIngresoEconomico(float ingresoEconomico) {
        this.ingresoEconomico = ingresoEconomico;
    }

    public boolean isIndHijos() {
        return indHijos;
    }

    public void setIndHijos(boolean indHijos) {
        this.indHijos = indHijos;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public long getNumVersion() {
        return numVersion;
    }

    public void setNumVersion(long numVersion) {
        this.numVersion = numVersion;
    }

    public ProfileIdentificationIndicator getIndDocIdentificacion() {
        return indDocIdentificacion;
    }

    public void setIndDocIdentificacion(ProfileIdentificationIndicator indDocIdentificacion) {
        this.indDocIdentificacion = indDocIdentificacion;
    }

    public ProfileMemberType getTipoMiembro() {
        return tipoMiembro;
    }

    public void setTipoMiembro(ProfileMemberType tipoMiembro) {
        this.tipoMiembro = tipoMiembro;
    }

    public List<ProfileDynamicAttribute> getAtributoDinamicos() {
        return atributoDinamicos;
    }

    public void setAtributoDinamicos(List<ProfileDynamicAttribute> atributoDinamicos) {
        this.atributoDinamicos = atributoDinamicos;
    }

    public Boolean getIndPolitica() {
        return indPolitica;
    }

    public void setIndPolitica(Boolean indPolitica) {
        this.indPolitica = indPolitica;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public boolean getIndCambioPass() {
        return indCambioPass;
    }

    public void setIndCambioPass(boolean indCambioPass) {
        this.indCambioPass = indCambioPass;
    }

    public String recoverCity(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("cidade")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverNeighborhood(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("bairro")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverComplement(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("complemento")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverState(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("uf")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverNumber(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("numero")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverThoroughfare(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("logradouro")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public String recoverZipcode(){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("cep")){
                return attribute.getValorAtributoMiembro() != null ? attribute.getValorAtributoMiembro() : "";
            }
        }
        return "";
    }

    public void defineZipcode(String zipcode){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("cep")){
                attribute.setValorAtributoMiembro(zipcode);
            }
        }
    }


    public void defineCity(String city){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("cidade")){
                attribute.setValorAtributoMiembro(city);
            }
        }
    }

    public void defineComplement(String complement){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("complemento")){
                attribute.setValorAtributoMiembro(complement);
            }
        }
    }

    public void defineNeighborhood(String neighborhood){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("bairro")){
                attribute.setValorAtributoMiembro(neighborhood);
            }
        }
    }

    public void defineState(String state){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("uf")){
                attribute.setValorAtributoMiembro(state);
            }
        }
    }

    public void defineNumber(String number){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("numero")){
                attribute.setValorAtributoMiembro(number);
            }
        }
    }

    public void defineThoroughfare(String thoroughfare){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("logradouro")){
                attribute.setValorAtributoMiembro(thoroughfare);
            }
        }
    }
    public void defineCardType(String cardType){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("tarjetaLealdat")){
                attribute.setValorAtributoMiembro(cardType);
            }
        }
    }
    public void defineEmailCommunications(boolean authorized){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("comunicacion")){
                attribute.setValorAtributoMiembro(String.valueOf(authorized));
            }
        }
    }
    public void defineAcceptContract(boolean authorized){
        for(ProfileDynamicAttribute attribute : atributoDinamicos){
            if(attribute.getNombre().equals("aceiteContrato")){
                attribute.setValorAtributoMiembro(String.valueOf(authorized));
            }
        }
    }

    public Profile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMiembro);
        dest.writeString(this.docIdentificacion);
        dest.writeString(this.avatar);
        dest.writeString(this.nombre);
        dest.writeString(this.apellido);
        dest.writeString(this.apellido2);
        dest.writeString(this.contrasena);
        dest.writeString(this.correo);
        dest.writeString(this.nombreUsuario);
        dest.writeString(this.direccion);
        dest.writeString(this.ciudadResidencia);
        dest.writeString(this.estadoResidencia);
        dest.writeString(this.paisResidencia);
        dest.writeString(this.codigoPostal);
        dest.writeString(this.telefonoMovil);
        dest.writeValue(this.fechaNacimiento);
        dest.writeString(this.indGenero);
        dest.writeByte(this.indContactoEmail ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indContactoSms ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indContactoNotificacion ? (byte) 1 : (byte) 0);
        dest.writeByte(this.indContactoEstado ? (byte) 1 : (byte) 0);
        dest.writeString(this.indEstadoCivil);
        dest.writeString(this.frecuenciaCompra);
        dest.writeString(this.indEducacion);
        dest.writeFloat(this.ingresoEconomico);
        dest.writeByte(this.indHijos ? (byte) 1 : (byte) 0);
        dest.writeString(this.comentarios);
        dest.writeValue(this.indPolitica);
        dest.writeLong(this.numVersion);
        dest.writeString(this.fechaIngreso);
        dest.writeParcelable(this.indDocIdentificacion, flags);
        dest.writeParcelable(this.tipoMiembro, flags);
        dest.writeTypedList(this.atributoDinamicos);
        dest.writeByte(this.indCambioPass ? (byte) 1 : (byte) 0);
    }

    protected Profile(Parcel in) {
        this.idMiembro = in.readString();
        this.docIdentificacion = in.readString();
        this.avatar = in.readString();
        this.nombre = in.readString();
        this.apellido = in.readString();
        this.apellido2 = in.readString();
        this.contrasena = in.readString();
        this.correo = in.readString();
        this.nombreUsuario = in.readString();
        this.direccion = in.readString();
        this.ciudadResidencia = in.readString();
        this.estadoResidencia = in.readString();
        this.paisResidencia = in.readString();
        this.codigoPostal = in.readString();
        this.telefonoMovil = in.readString();
        this.fechaNacimiento = (Long) in.readValue(Long.class.getClassLoader());
        this.indGenero = in.readString();
        this.indContactoEmail = in.readByte() != 0;
        this.indContactoSms = in.readByte() != 0;
        this.indContactoNotificacion = in.readByte() != 0;
        this.indContactoEstado = in.readByte() != 0;
        this.indEstadoCivil = in.readString();
        this.frecuenciaCompra = in.readString();
        this.indEducacion = in.readString();
        this.ingresoEconomico = in.readFloat();
        this.indHijos = in.readByte() != 0;
        this.comentarios = in.readString();
        this.indPolitica = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.numVersion = in.readLong();
        this.fechaIngreso = in.readString();
        this.indDocIdentificacion = in.readParcelable(ProfileIdentificationIndicator.class.getClassLoader());
        this.tipoMiembro = in.readParcelable(ProfileMemberType.class.getClassLoader());
        this.atributoDinamicos = in.createTypedArrayList(ProfileDynamicAttribute.CREATOR);
        this.indCambioPass = in.readByte() != 0;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}

