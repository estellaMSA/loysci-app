package br.com.loysciapp.loysci_android.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


public class Feed {
    private String idFeed;
    private String detalle;
    private String imagen;
    private Bitmap bitmap;
    private int cantidad;
    private int cantComentarios;
    private boolean favorito;
    private List<Comments> commentsList = new ArrayList<>();

    public String getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(String idFeed) {
        this.idFeed = idFeed;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public int getCantComentarios() {
        return cantComentarios;
    }

    public void setCantComentarios(int cantComentarios) {
        this.cantComentarios = cantComentarios;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
