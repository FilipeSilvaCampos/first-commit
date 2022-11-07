package com.example.conta.UI.Adapters;


import android.content.Context;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.RecyclerView;

import com.example.conta.UI.HabitsThisPartDayActivity;
import com.example.conta.Data.AppDataBase;
import com.example.conta.Domain.Habit;
import com.example.conta.UI.Fragments.TimePickerFragment;
import com.example.conta.R;
import com.example.conta.databinding.HabitsListBinding;

import java.util.List;


public class HabitsListAdapter extends RecyclerView.Adapter<HabitsListAdapter.ListViewHolder> {

    private final AppDataBase db;
    private List<Habit> list;
    private final Context context;

    public HabitsListAdapter(AppDataBase db, int part, Context context) {
        this.db = db;
        this.list = db.habitDao().getPartInOrder(part);
        this.context = context;

    }

    @NonNull
    @Override
    public HabitsListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HabitsListBinding binding = HabitsListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ListViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Habit habitAct = list.get(position);
        habitAct.setContext(holder.binding.getRoot().getContext());
        holder.bind(habitAct);

        holder.binding.tvHour.setOnClickListener((view -> {
            TimePickerFragment timePickerFragment = new TimePickerFragment(holder.binding.tvHour);
            FragmentManager manager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
            timePickerFragment.show(manager, "time");

            changeAlarmHour(habitAct, holder.binding.tvHour.getText().toString());

        }));

        holder.binding.itemContainer.setOnLongClickListener((view) -> {
            clearHabit(habitAct);

            return false;

        });

        holder.binding.btnListItemAlarmState.setOnClickListener((view -> disableAlarm(habitAct,holder)));

        holder.binding.tvTypeRingtone.setOnClickListener((view -> changeTypeAlarm(habitAct, holder)));

    }


    @Override
    public int getItemCount() {return list.size();}

    void clearHabit(Habit habit) {
        Context context = habit.getContext();
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.txt_confirming_command)
                .setTitle(habit.getTitle())
                .setPositiveButton(R.string.txt_confirmation,(dialog, id) -> {
                    db.habitDao().delete(habit);
                    list = db.habitDao().getPartInOrder(HabitsThisPartDayActivity.partToAdapter);
                    this.notifyItemRemoved(this.getItemCount());

                    habit.setStateAlarm(false);
                    habit.setAlarmManager();

                }).setNegativeButton(R.string.txt_Denial,(dialog, id) -> dialog.dismiss());

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    void disableAlarm(Habit habit, ListViewHolder holder) {
            if (habit.getStateAlarm()) {
                habit.setStateAlarm(false);
                habit.setContext(context);
                habit.setAlarmManager();

                holder.binding.tvTypeRingtone.setAlpha(0.5f);
                holder.binding.btnListItemAlarmState.setAlpha(0.5f);
                holder.binding.tvHour.setAlpha(0.5f);

                db.habitDao().update(habit);

            } else {
                habit.setStateAlarm(true);
                habit.setContext(context);
                habit.setAlarmManager();

                holder.binding.tvTypeRingtone.setAlpha(1f);
                holder.binding.btnListItemAlarmState.setAlpha(1f);
                holder.binding.tvHour.setAlpha(1f);

                db.habitDao().update(habit);
            }

    }

    void changeAlarmHour(Habit habit, String string) {
        if (habit.getStateAlarm()) {
            habit.setHour(string);
            habit.setAlarmManager();
            db.habitDao().update(habit);

        } else {
            habit.setHour(string);
            db.habitDao().update(habit);

        }
    }

    void changeTypeAlarm(Habit habit, ListViewHolder holder) {
        if (habit.getIsSpotify()) {
            habit.setIsSpotify(false);
            holder.binding.tvTypeRingtone.setText(R.string.txt_default_ringtone);
            holder.binding.tvTypeRingtone.setTextColor(Color.rgb(41, 102, 195));
            holder.binding.btnListItemAlarmState.setColorFilter(Color.rgb(41,102,195));
            holder.binding.tvHour.setTextColor(Color.rgb(41,102,195));
            db.habitDao().update(habit);

        } else {
            habit.setIsSpotify(true);
            holder.binding.tvTypeRingtone.setText(R.string.txt_spotify_ringtone);
            holder.binding.tvTypeRingtone.setTextColor(Color.rgb(50, 205, 50));
            holder.binding.btnListItemAlarmState.setColorFilter(Color.rgb(50,205,50));
            holder.binding.tvHour.setTextColor(Color.rgb(50,205,50));
            db.habitDao().update(habit);

        }

    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final HabitsListBinding  binding;

        public ListViewHolder(HabitsListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(Habit habit) {
            binding.ivImageItem.setImageResource(habit.getImageResource());
            binding.tvTitle.setText(habit.getTitle());
            binding.tvHour.setText(habit.getHour());
            binding.tvDescription.setText(habit.getDescription());

            if (habit.getIsSpotify()) {
                binding.tvTypeRingtone.setText(habit.getPlaylistName());
            } else {
                binding.tvTypeRingtone.setText(R.string.txt_default_ringtone);
            }

        }

    }

}
