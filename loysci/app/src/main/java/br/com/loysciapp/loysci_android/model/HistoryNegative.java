package br.com.loysciapp.loysci_android.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class HistoryNegative  {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            REINTEGRO,
            APUESTA_JUEGO_EXTERNO,
            APUESTA_JUEGO_INTERNO,
            PARTIDA,
            DESCONOCIDO,
            MISION,
            BIENVENIDA,
            REFERENCIA,
            TRANSACCION,
            REVERSION,
            REDENCION,
            BONIFICACION_EXTRA
    })
    public @interface HistoryType {
    }

    public static final String REINTEGRO = "I"; //positivo
    public static final String APUESTA_JUEGO_EXTERNO = "A"; //negativo
    public static final String APUESTA_JUEGO_INTERNO = "Z"; //negativo
    public static final String PARTIDA = "P"; //positiva
    public static final String DESCONOCIDO = "D"; //??
    public static final String MISION = "M"; //positivo
    public static final String BIENVENIDA = "B"; //positivo
    public static final String REFERENCIA = "R"; //positivo
    public static final String TRANSACCION = "T"; //positivo
    public static final String REVERSION = "V"; //positivo
    public static final String REDENCION = "X"; //negativo
    public static final String BONIFICACION_EXTRA = "E"; //positivo
    public static final String VENCIMIENTO_METRICA = "Y"; //positivo

    private String nombreMision;
    private String indFormaGanada;
    private Transaction transaccion;
    private long fecha;
    private float cantidadGanada;

    public String getNombreMision() {
        return nombreMision;
    }

    public void setNombreMision(String nombreMision) {
        this.nombreMision = nombreMision;
    }

    public String getIndFormaGanada() {
        return indFormaGanada;
    }

    public void setIndFormaGanada(String indFormaGanada) {
        this.indFormaGanada = indFormaGanada;
    }


    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public float getCantidadGanada() {
        return cantidadGanada;
    }

    public void setCantidadGanada(float cantidadGanada) {
        this.cantidadGanada = cantidadGanada;
    }

    public Transaction getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaction transaccion) {
        this.transaccion = transaccion;
    }
}

