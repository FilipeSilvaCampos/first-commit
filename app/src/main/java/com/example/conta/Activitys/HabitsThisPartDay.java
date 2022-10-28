package com.example.conta.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.conta.Adapters.ListAdapter;
import com.example.conta.DataBase.AppDataBase;
import com.example.conta.R;
import com.example.conta.databinding.ActivityHabitsThisPartDayBinding;

public class HabitsThisPartDay extends AppCompatActivity {

    public static int partToAdapter;
    private ActivityHabitsThisPartDayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHabitsThisPartDayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        setActvityTitle(partToAdapter);

        AppDataBase db = AppDataBase.retrieveDatabaseInstance(this);
        ListAdapter listAdapter = new ListAdapter(this, db,partToAdapter);
        binding.rvHabitsPart.setAdapter(listAdapter);

    }

    void setActvityTitle (int partToAdapter) {
        if (partToAdapter == 0) {
            binding.tvThisPart.setText(R.string.txt_partday_morning);
            binding.tvThisPart.setTextColor(Color.rgb(255,255,226));

        } else if (partToAdapter == 1) {
            binding.tvThisPart.setText(R.string.txt_partday_afternoon);
            binding.tvThisPart.setTextColor(Color.rgb(169,166,1));

        } else {
            binding.tvThisPart.setText(R.string.txt_partday_night);
            binding.tvThisPart.setTextColor(Color.rgb(41,140,16));

        }
    }
}