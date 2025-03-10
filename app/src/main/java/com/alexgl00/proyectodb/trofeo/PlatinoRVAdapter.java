package com.alexgl00.proyectodb.trofeo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl00.proyectodb.R;

import java.util.ArrayList;

public class PlatinoRVAdapter extends RecyclerView.Adapter<PlatinoRVAdapter.MyViewHolder> {

    Context context;
    ArrayList<Platino_item> platinoItems;

    public PlatinoRVAdapter(Context context, ArrayList<Platino_item> platinoItems) {
        this.context = context;
        this.platinoItems = platinoItems;
    }

    @NonNull
    @Override
    public PlatinoRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.platino_item, parent, false);
        return new PlatinoRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatinoRVAdapter.MyViewHolder holder, int position) {

        String platinoName = platinoItems.get(position).getPlatinoName();
        String platinoDescription = platinoItems.get(position).getPlatinoDescription();

        holder.platinoName.setText(platinoItems.get(position).getPlatinoName());
        holder.platinoDescription.setText(platinoItems.get(position).getPlatinoDescription());
    }

    @Override
    public int getItemCount() {
        return platinoItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView platinoName, platinoDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            platinoName = itemView.findViewById(R.id.platinoName);
            platinoDescription = itemView.findViewById(R.id.platinoDescription);
        }
    }
}