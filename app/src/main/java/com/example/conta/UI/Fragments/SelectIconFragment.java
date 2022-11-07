package com.example.conta.UI.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.conta.UI.CreatHabitActivity;
import com.example.conta.R;
import com.example.conta.databinding.SelecetImageHabitBinding;

import java.util.HashMap;

public class SelectIconFragment extends DialogFragment {

    private final HashMap<String, Integer> imageMap;
    private final ImageView imageView;

    public SelectIconFragment(ImageView imageView) {
        this.imageView = imageView;

        imageMap = new HashMap<>();
        imageMap.put("coffe", R.drawable.coffee);
        imageMap.put("balloons",R.drawable.balloons);

    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        com.example.conta.databinding.SelecetImageHabitBinding binding = SelecetImageHabitBinding.inflate(LayoutInflater.from(getContext()));
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

    void icnSelectd(Integer resource) {
        Glide.with(getContext()).load(resource).into(imageView);
        CreatHabitActivity.resourceImage = resource;
        dismiss();

    }

}
