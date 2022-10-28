package com.example.conta.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.conta.Activitys.CreatHabitActivity;
import com.example.conta.R;
import com.example.conta.databinding.SelecetImageHabitBinding;

import java.util.HashMap;

public class SelectIconFragment extends DialogFragment {

    private SelecetImageHabitBinding binding;
    private final HashMap imageMap;
    private ImageView imageView;

    public SelectIconFragment(ImageView imageView) {
        this.imageView = imageView;

        imageMap = new HashMap<>();
        imageMap.put("coffe", R.drawable.coffee);
        imageMap.put("balloons",R.drawable.balloons);

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        binding = SelecetImageHabitBinding.inflate(LayoutInflater.from(getContext()));
        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(binding.getRoot());

        binding.icnCoffe.setOnClickListener((view -> {
           icnSelectd(imageMap.get("coffe"));
        }));

        binding.icnBallons.setOnClickListener((view -> {
            icnSelectd(imageMap.get("balloons"));
        }));

        return dialog;
    }

    void icnSelectd(Object value) {
        int resource = (Integer) value;
        imageView.setImageResource(resource);
        CreatHabitActivity.resourceIdImageCreat = resource;

    }

}
