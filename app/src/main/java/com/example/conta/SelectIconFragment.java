package com.example.conta;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.conta.Activitys.CreatHabitActivity;

import java.util.HashMap;

public class SelectIconFragment extends DialogFragment {

    public static HashMap imageMap;
    ImageView imageView;

    public SelectIconFragment(ImageView imageView) {

        this.imageView = imageView;

        imageMap = new HashMap<>();
        imageMap.put("coffe", R.drawable.coffee);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.selecet_image_habit);

        ImageView coffeIcon = dialog.findViewById(R.id.coffeIcon);

        coffeIcon.setOnClickListener((view -> {
            imageView.setImageResource((Integer) imageMap.get("coffe"));
            CreatHabitActivity.resourceIdImageCreat = (Integer) imageMap.get("coffe");
        }));

        return dialog;
    }
}
