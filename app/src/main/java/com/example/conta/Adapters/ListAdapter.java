package com.example.conta.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conta.Activitys.HabitsThisPartDay;
import com.example.conta.Activitys.MainActivity;
import com.example.conta.Objects.Habit;
import com.example.conta.R;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    List<Habit> list = MainActivity.db.habitDao().getPartInOrder(HabitsThisPartDay.partToAdapter);

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
                    .setPositiveButton("Excluir",(dialog, id) -> {
                        MainActivity.db.habitDao().delete(habitAct);
                        list = MainActivity.db.habitDao().getPartInOrder(HabitsThisPartDay.partToAdapter);
                        HabitsThisPartDay.recyclerView.setAdapter(new ListAdapter());
                    }).setNegativeButton("Não",(dialog, id) -> {
                        dialog.dismiss();
                    });
            AlertDialog dialog = alert.create();
            dialog.show();
        return false;
        });
    }

    @Override
    public int getItemCount() {return list.size();}

    class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        TextView hourView;
        TextView descriptionView;
        CardView itemContainer;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameView = itemView.findViewById(R.id.name);
            hourView = itemView.findViewById(R.id.hour);
            descriptionView = itemView.findViewById(R.id.description);
            itemContainer = itemView.findViewById(R.id.itemContainer);
        }

        void bind(Habit habit) {
            imageView.setImageResource(R.drawable.ic_launcher_background);
            nameView.setText(habit.getName());
            hourView.setText(habit.getHour());
            descriptionView.setText(habit.getDescription());
        }
    }
}
