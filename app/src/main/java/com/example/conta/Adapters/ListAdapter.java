package com.example.conta.Adapters;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conta.Activitys.HabitsThisPartDay;
import com.example.conta.DataBase.AppDataBase;
import com.example.conta.Objects.Habit;
import com.example.conta.Pickers.TimePicker;
import com.example.conta.R;
import com.example.conta.Recieves.recieve;

import java.util.Calendar;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final AppDataBase db;
    private List<Habit> list;
    private final Context context;

    public ListAdapter(Context context, AppDataBase db, int part) {
        this.db = db;
        this.context = context;
        this.list = db.habitDao().getPartInOrder(part);

    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habitsonlist,parent,false);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Habit habitAct = list.get(position);
        holder.bind(habitAct);

        holder.tvHourItem.setOnClickListener((view -> {
            TimePicker timePicker = new TimePicker(holder.tvHourItem);
            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            timePicker.show(manager, "time");

            changeAlarmHour(habitAct, holder.tvHourItem);

        }));

        holder.itemContainer.setOnLongClickListener((view) -> {
            clearHabit(habitAct, view.getContext());

            return false;

        });

        holder.alarmState.setOnClickListener((view -> disableAlarm(habitAct, view.getContext(), holder)));

        holder.tvTypeRingtone.setOnClickListener((view -> changeTypeAlarm(habitAct, holder)));

    }


    @Override
    public int getItemCount() {return list.size();}

    void clearHabit(Habit habit, Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(R.string.txt_confirming_command)
                .setTitle(habit.getName())
                .setPositiveButton(R.string.txt_confirmation,(dialog, id) -> {
                    db.habitDao().delete(habit);
                    list = db.habitDao().getPartInOrder(HabitsThisPartDay.partToAdapter);
                    this.notifyItemRemoved(this.getItemCount());

                    Intent toCancel = new Intent(context,recieve.class);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(PendingIntent.getBroadcast(context,habit.getId(),toCancel,0));

                }).setNegativeButton(R.string.txt_Denial,(dialog, id) -> dialog.dismiss());

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    void disableAlarm(Habit habit, Context context, ListViewHolder holder) {
            if (habit.getStateAlarm()) {
                habit.setStateAlarm(false);
                holder.tvTypeRingtone.setAlpha(0.5f);
                holder.btnAlarmState.setAlpha(0.5f);
                holder.tvHourItem.setAlpha(0.5f);

                db.habitDao().update(habit);

                Intent toCancel = new Intent(context,recieve.class);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(PendingIntent.getBroadcast(context,habit.getId(),toCancel,0));

                Toast toast = Toast.makeText(context, R.string.txt_off_alarm,Toast.LENGTH_LONG);
                toast.show();

            } else {
                Toast toast = Toast.makeText(context, R.string.txt_on_alarm,Toast.LENGTH_LONG);
                toast.show();

                habit.setStateAlarm(true);
                holder.tvTypeRingtone.setAlpha(1f);
                holder.btnAlarmState.setAlpha(1f);
                holder.tvHourItem.setAlpha(1f);

                db.habitDao().update(habit);

                int[] hourBreakApart = habit.getIntegerHour();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,
                        hourBreakApart[0],
                        hourBreakApart[1],
                        0);

                Intent habitIntent = new Intent(context, recieve.class);
                habitIntent.putExtra("habitID",habit.getId());

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(context, habit.getId(), habitIntent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

            }

    }

    void changeAlarmHour(Habit habit, TextView textView) {
        if (habit.getStateAlarm()) {
            habit.setHour((String) textView.getText());
            db.habitDao().update(habit);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,
                    habit.getIntegerHour()[0],
                    habit.getIntegerHour()[1],
                    0);

            Intent habitIntent = new Intent(context, recieve.class);
            habitIntent.putExtra("habitID",habit.getId());

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(context, habit.getId(), habitIntent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        } else {
            habit.setHour((String) textView.getText());
            db.habitDao().update(habit);

        }
    }

    void changeTypeAlarm(Habit habit, ListViewHolder holder) {
        if (habit.getTypeSound()) {
            habit.setTypeSound(false);
            holder.tvTypeRingtone.setText(R.string.txt_default_ringtone);
            holder.tvTypeRingtone.setTextColor(Color.rgb(41, 102, 195));
            holder.btnAlarmState.setColorFilter(Color.rgb(41,102,195));
            holder.tvHourItem.setTextColor(Color.rgb(41,102,195));
            db.habitDao().update(habit);

        } else {
            habit.setTypeSound(true);
            holder.tvTypeRingtone.setText(R.string.txt_spotify_ringtone);
            holder.tvTypeRingtone.setTextColor(Color.rgb(50, 205, 50));
            holder.btnAlarmState.setColorFilter(Color.rgb(50,205,50));
            holder.tvHourItem.setTextColor(Color.rgb(50,205,50));
            db.habitDao().update(habit);
        }

    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImageItem;
        TextView tvNameItem;
        TextView tvHourItem;
        TextView tvDescriptionItem;
        CardView itemContainer;
        ImageButton alarmState;
        TextView tvTypeRingtone;
        ImageButton btnAlarmState;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageItem = itemView.findViewById(R.id.ivImageItem);
            tvNameItem = itemView.findViewById(R.id.tvTitle);
            tvHourItem = itemView.findViewById(R.id.tvHour);
            tvDescriptionItem = itemView.findViewById(R.id.tvDescription);
            itemContainer = itemView.findViewById(R.id.containerImage);
            alarmState = itemView.findViewById(R.id.btnListItemAlarmState);
            tvTypeRingtone = itemView.findViewById(R.id.tvTypeRingtone);
            btnAlarmState = itemView.findViewById(R.id.btnListItemAlarmState);

        }

        void bind(Habit habit) {
            ivImageItem.setImageResource(habit.getImage());
            tvNameItem.setText(habit.getName());
            tvHourItem.setText(habit.getHour());
            tvDescriptionItem.setText(habit.getDescription());

            if (habit.getStateAlarm()) {
                tvTypeRingtone.setAlpha(1f);
                btnAlarmState.setAlpha(1f);
                tvHourItem.setAlpha(1f);

            } else {
                tvTypeRingtone.setAlpha(0.5f);
                btnAlarmState.setAlpha(0.5f);
                tvHourItem.setAlpha(0.5f);

            }

            if (habit.getTypeSound()) {
                tvTypeRingtone.setText(R.string.txt_spotify_ringtone);

            } else {
                tvTypeRingtone.setText(R.string.txt_default_ringtone);

            }

        }
    }

}
