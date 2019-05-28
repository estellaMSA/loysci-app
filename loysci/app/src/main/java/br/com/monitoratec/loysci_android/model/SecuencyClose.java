package br.com.monitoratec.loysci_android.model;


import java.util.List;

public class SecuencyClose {
    private String titulo;
    private String idWorkflow;
    private List<GeneralChallenges> misiones;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdWorkflow() {
        return idWorkflow;
    }

    public void setIdWorkflow(String idWorkflow) {
        this.idWorkflow = idWorkflow;
    }

    public List<GeneralChallenges> getMisiones() {
        return misiones;
    }

    public void setMisiones(List<GeneralChallenges> misiones) {
        this.misiones = misiones;
    }
}