package br.com.monitoratec.loysci_android.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.LoadState;

public class LoadStateViewsHandler {

    private View loading;
    private View failed;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int toastErrorStringResId;

    public LoadStateViewsHandler(View loading, View failed, SwipeRefreshLayout swipeRefreshLayout) {
        this.loading = loading;
        this.failed = failed;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.toastErrorStringResId = R.string.failed_load;
    }

    public void setToastErrorStringResId(int toastErrorStringResId) {
        this.toastErrorStringResId = toastErrorStringResId;
    }

    public void handleLoadState(LoadState loadState, boolean onlyToastError) {
        if (loadState == LoadState.FAILED) {
            handleError(onlyToastError);
            return;
        }

        if (loadState == LoadState.LOADED) {
            swipeRefreshLayout.setRefreshing(false);
            loading.setVisibility(View.GONE);
            setFailedViewVisibility(false);
            swipeRefreshLayout.setEnabled(true);
        } else if (loadState == LoadState.LOADING) {
            if (!swipeRefreshLayout.isRefreshing()) {
                loading.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
            }
            setFailedViewVisibility(false);
        }
    }

    private void handleError(boolean onlyToastError) {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setRefreshing(false);
        loading.setVisibility(View.GONE);

        if (!onlyToastError) {
            setFailedViewVisibility(true);
        }
        Toast.makeText(loading.getContext(), loading.getContext().getText(toastErrorStringResId), Toast.LENGTH_SHORT).show();
    }

    private void setFailedViewVisibility(boolean visible){
        if (failed != null) {
            if (visible) {
                failed.setVisibility(View.VISIBLE);
            } else {
                failed.setVisibility(View.GONE);
            }
        }
    }
}
