package br.com.loysciapp.loysci_android.model;

import java.util.Collection;


public class ChallengeSubmitAnswers {
    private Collection<ChallengeSurveyAnswer> respuestaEncuesta;
    private ChallengeSeeContentAnswer respuestaVerContenido;
    private Collection<ChallengeUpdateProfileAnswer> respuestaActualizarPerfil;
    private ChallengeScratchAnswer respuestaJuego;
    private ChallengeUploadAnswer respuestaSubirContenido;
    private ChallengeSocialNetworkAnswer respuestaRedSocial;
    private Collection<ChallengePreferencesAnswer> respuestaPreferencias;

    public ChallengeUploadAnswer getRespuestaSubirContenido() {
        return respuestaSubirContenido;
    }

    public void setRespuestaSubirContenido(ChallengeUploadAnswer respuestaSubirContenido) {
        this.respuestaSubirContenido = respuestaSubirContenido;
    }

    public ChallengeSocialNetworkAnswer getRespuestaRedSocial() {
        return respuestaRedSocial;
    }

    public void setRespuestaRedSocial(ChallengeSocialNetworkAnswer respuestaRedSocial) {
        this.respuestaRedSocial = respuestaRedSocial;
    }

    public ChallengeScratchAnswer getRespuestaJuego() {
        return respuestaJuego;
    }

    public void setRespuestaJuego(ChallengeScratchAnswer respuestaJuego) {
        this.respuestaJuego = respuestaJuego;
    }

    public Collection<ChallengeSurveyAnswer> getRespuestaEncuesta() {
        return respuestaEncuesta;
    }

    public void setRespuestaEncuesta(Collection<ChallengeSurveyAnswer> respuestaEncuesta) {
        this.respuestaEncuesta = respuestaEncuesta;
    }

    public ChallengeSeeContentAnswer getRespuestaVerContenido() {
        return respuestaVerContenido;
    }

    public void setRespuestaVerContenido(ChallengeSeeContentAnswer respuestaVerContenido) {
        this.respuestaVerContenido = respuestaVerContenido;
    }

    public Collection<ChallengeUpdateProfileAnswer> getRespuestaActualizarPerfil() {
        return respuestaActualizarPerfil;
    }

    public void setRespuestaActualizarPerfil(Collection<ChallengeUpdateProfileAnswer> respuestaActualizarPerfil) {
        this.respuestaActualizarPerfil = respuestaActualizarPerfil;
    }

    public Collection<ChallengePreferencesAnswer> getRespuestaPreferencias() {
        return respuestaPreferencias;
    }

    public void setRespuestaPreferencias(Collection<ChallengePreferencesAnswer> respuestaPreferencias) {
        this.respuestaPreferencias = respuestaPreferencias;
    }
}
