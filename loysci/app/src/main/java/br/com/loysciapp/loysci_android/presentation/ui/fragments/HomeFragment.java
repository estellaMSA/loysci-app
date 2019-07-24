package br.com.loysciapp.loysci_android.presentation.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.FragmentHomeBinding;

import static br.com.loysciapp.loysci_android.util.Constants.HOME_BADGES;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_CARTAO_VIRTUAL;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_CHANGE_PASS_OPTION;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_GET_PRIZE;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_PARTICIPATE_WIN;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_PERSONAL_DATA_OPTION;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_REGULAMENTO;
import static br.com.loysciapp.loysci_android.util.Constants.HOME_SALES;


public class HomeFragment extends Fragment {

    private HomeOptionsListener mListener;

    FragmentHomeBinding binding;
    Boolean isClicked = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        isClicked = false;
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();

        binding.badgesLayout.setOnClickListener(v -> {

            if(!isClicked){

                mListener.onHomeOptionSelected(HOME_BADGES);
                isClicked = true;
            }

        });


        binding.participateLayout.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_PARTICIPATE_WIN);
                isClicked = true;
            }
        });

        binding.getPrizeLayout.setOnClickListener(v ->{
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_GET_PRIZE);
                isClicked = true;
            }
        });

        binding.userSalesLayout.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_SALES);
                isClicked = true;
            }
        });

        binding.userPersonalDataLayout.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_PERSONAL_DATA_OPTION);
                isClicked = true;
            }
        });

        /*binding.userAddressLayout.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_ADDRESS_OPTION);
                isClicked = true;
            }
        });*/

        binding.userPasswordLayout.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_CHANGE_PASS_OPTION);
                isClicked = true;
            }
        });

        /*binding.userDriversLicense.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_DRIVER_LICENSE_OPTION);
                isClicked = true;
            }
        });*/

        binding.homeRegulamento.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_REGULAMENTO);
                isClicked = true;
            }
        });

        binding.homeCartaovirtual.setOnClickListener(v -> {
            if(!isClicked) {
                mListener.onHomeOptionSelected(HOME_CARTAO_VIRTUAL);
                isClicked = true;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isClicked = false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeOptionsListener) {
            mListener = (HomeOptionsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeOptionsListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isClicked = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface HomeOptionsListener {
        void onHomeOptionSelected(int option);
    }
}
