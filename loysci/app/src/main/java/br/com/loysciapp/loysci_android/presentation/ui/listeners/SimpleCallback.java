package br.com.loysciapp.loysci_android.presentation.ui.listeners;

public interface SimpleCallback<T> {
    void onResponse(T response);
    void onError(Throwable t);
}
