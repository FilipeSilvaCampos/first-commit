package com.example.conta.Adapters;

import static com.example.conta.Activitys.MainActivity.db;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conta.Activitys.HabitsThisPartDay;
import com.example.conta.Activitys.MainActivity;
import com.example.conta.Objects.Habit;
import com.example.conta.R;
import com.example.conta.Recieves.recieve;

import java.util.Calendar;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    List<Habit> list = db.habitDao().getPartInOrder(HabitsThisPartDay.partToAdapter);

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habitsonlist,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
        Habit habitAct = list.get(position);
        holder.bind(habitAct);

        holder.itemContainer.setOnLongClickListener((view) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setMessage("Deseja excluir este habito")
                    .setTitle(habitAct.getName())
                    .setPositiveButton("Apagar",(dialog, id) -> {
                        db.habitDao().delete(habitAct);
                        list = db.habitDao().getPartInOrder(HabitsThisPartDay.partToAdapter);
                        HabitsThisPartDay.recyclerView.setAdapter(new ListAdapter());

                        Intent toCancel = new Intent(view.getContext(),recieve.class);
                        AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(PendingIntent.getBroadcast(view.getContext(),habitAct.getId(),toCancel,0));

                    }).setNegativeButton("Não",(dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog dialog = alert.create();
            dialog.show();
        return false;
        });

        holder.alarmState.setOnClickListener((view -> {
            if (holder.alarmState.isChecked()) {
                Toast toast = Toast.makeText(view.getContext(), "alarme ativado",Toast.LENGTH_LONG);
                toast.show();

                habitAct.setStateAlarm(true);
                db.habitDao().update(habitAct);

                String[] hourBreakApart = habitAct.getHour().split(":");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,Calendar.MONTH,Calendar.DATE,
                        Integer.parseInt(hourBreakApart[0]),
                        Integer.parseInt(hourBreakApart[1]),
                        0);

                Intent habitIntent = new Intent(view.getContext(), recieve.class);
                habitIntent.putExtra("habitID",habitAct.getId());

                AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);

                PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(view.getContext(), habitAct.getId(), habitIntent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            } else {
                habitAct.setStateAlarm(false);
                db.habitDao().update(habitAct);

                Intent toCancel = new Intent(view.getContext(),recieve.class);
                AlarmManager alarmManager = (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(PendingIntent.getBroadcast(view.getContext(),habitAct.getId(),toCancel,0));

                Toast toast = Toast.makeText(view.getContext(), "alarme desativado",Toast.LENGTH_LONG);
                toast.show();
            }
        }));
    }

    @Override
    public int getItemCount() {return list.size();}

    class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        TextView hourView;
        TextView descriptionView;
        CardView itemContainer;
        Switch alarmState;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.name);
            hourView = itemView.findViewById(R.id.hour);
            descriptionView = itemView.findViewById(R.id.description);
            itemContainer = itemView.findViewById(R.id.itemContainer);
            alarmState = itemView.findViewById(R.id.alarmState);
        }

        void bind(Habit habit) {
            imageView.setImageResource(habit.getImage());
            nameView.setText(habit.getName());
            hourView.setText(habit.getHour());
            descriptionView.setText(habit.getDescription());
            alarmState.setChecked(habit.getStateAlarm());
        }
    }
}
