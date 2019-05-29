package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogPhotoPickerBinding;

public class CustomPhotoPickerDialog extends Dialog {
    private OnOptionPhotoSelected mOnOptionPhotoSelected;
    private DialogPhotoPickerBinding binding;

    public CustomPhotoPickerDialog(@NonNull Context context, OnOptionPhotoSelected onOptionPhotoSelected) {
        super(context);
        this.mOnOptionPhotoSelected = onOptionPhotoSelected;

    }

    public CustomPhotoPickerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomPhotoPickerDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_photo_picker, null, false);
        setContentView(binding.getRoot());

        //binding.imageViewCamera.setOnClickListener(v -> mOnOptionPhotoSelected.onCamera());

        binding.imageViewGallery.setOnClickListener(v -> mOnOptionPhotoSelected.onGallery());

        binding.avatarOptionLayout.setOnClickListener(v -> mOnOptionPhotoSelected.onAvatar());
    }


    public interface OnOptionPhotoSelected {
        void onGallery();

        void onCamera();

        void onAvatar();
    }
}
