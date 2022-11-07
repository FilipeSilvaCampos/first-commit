package com.example.conta.UI.Adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.conta.UI.CreatHabitActivity;
import com.example.conta.Domain.SpotifyItem;
import com.example.conta.databinding.ActivityCreatHabitBinding;
import com.example.conta.databinding.ItemSpotifyListBinding;

import java.util.List;

public class ItemSpotifyAdapter extends RecyclerView.Adapter<ItemSpotifyAdapter.ItemViewHolder> {

    private final List<SpotifyItem> list;
    private final TextView textView;
    private int actualItemSelected;

    public ItemSpotifyAdapter(List<SpotifyItem> list) {
        this.list = list;
        textView = CreatHabitActivity.textView;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSpotifyListBinding binding = ItemSpotifyListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SpotifyItem spotifyItem = list.get(position);
        holder.bind(spotifyItem);

        holder.binding.cvItem.setOnClickListener((view) -> {
            CreatHabitActivity.spotifyItem = spotifyItem;
            textView.setText(spotifyItem.getName());

            holder.binding.cvItem.setCardBackgroundColor(Color.rgb(50,205,50));
            list.get(actualItemSelected).setSelected(false);
            this.notifyItemChanged(actualItemSelected);
            actualItemSelected = holder.getAdapterPosition();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemSpotifyListBinding binding;

        public ItemViewHolder(ItemSpotifyListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        void bind(SpotifyItem spotifyItem) {
            String imageUrl;
            if (spotifyItem.getImages().size() == 0) {imageUrl = null;}
            else {imageUrl = String.valueOf(spotifyItem.getImages().get(0).getUrl());}

            Glide.with(binding.getRoot().getContext()).load(imageUrl).into(binding.ivCapa);
            binding.tvName.setText(spotifyItem.getName());
            binding.tvDetails.setText(spotifyItem.getType());

            if (!spotifyItem.isSelected()) {
                binding.cvItem.setCardBackgroundColor(Color.DKGRAY);
            }
        }
    }

}
