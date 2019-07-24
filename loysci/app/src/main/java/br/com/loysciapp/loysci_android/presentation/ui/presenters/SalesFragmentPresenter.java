package br.com.loysciapp.loysci_android.presentation.ui.presenters;

import br.com.loysciapp.loysci_android.databinding.FragmentSalesBinding;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.SalesFragment;

public class SalesFragmentPresenter {
    private SalesFragment salesFragment;
    private FragmentSalesBinding binding;

    public SalesFragmentPresenter(SalesFragment salesFragment, FragmentSalesBinding binding) {
        this.salesFragment = salesFragment;
        this.binding = binding;
    }
}
