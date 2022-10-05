package com.example.conta.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.conta.Adapters.ListAdapter;
import com.example.conta.R;

public class HabitsThisPartDay extends AppCompatActivity {

    public static int partToAdapter;
    public static RecyclerView recyclerView;
    TextView thisPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habits_this_part_day);
        recyclerView = findViewById(R.id.viewFullHabits);
        thisPart = findViewById(R.id.thisPart);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setActvityTitle(partToAdapter);
        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
    }

    void setActvityTitle (int partToAdapter) {
        if (partToAdapter == 0) {
            thisPart.setText("Manhã");
        } else if (partToAdapter == 1) {
            thisPart.setText("Tarde");
        } else {
            thisPart.setText("Noite");
        }
    }
}