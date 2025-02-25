package com.alexgl00.proyectodb.character;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexgl00.proyectodb.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class TransformationAdapter extends RecyclerView.Adapter<TransformationAdapter.TransformationViewHolder> {

    private Context context;
    private List<Transformation> transformationList;

    public TransformationAdapter(Context context, List<Transformation> transformationList) {
        this.context = context;
        this.transformationList = transformationList;
    }

    @NonNull
    @Override
    public TransformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transformation, parent, false);
        return new TransformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransformationViewHolder holder, int position) {
        Transformation transformation = transformationList.get(position);
        Glide.with(context).load(transformation.getImage()).into(holder.transformationImage);
    }

    @Override
    public int getItemCount() {
        return transformationList.size();
    }

    static class TransformationViewHolder extends RecyclerView.ViewHolder {
        ImageView transformationImage;

        public TransformationViewHolder(@NonNull View itemView) {
            super(itemView);
            transformationImage = itemView.findViewById(R.id.transformationImage);
        }
    }
}
