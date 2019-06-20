package br.com.monitoratec.loysci_android.mvp.share;

public interface ShareView {


    void displayUserId(String idUser);
    void showSuccessToast(String msg);

    void showErrorMessage(String s);
}
