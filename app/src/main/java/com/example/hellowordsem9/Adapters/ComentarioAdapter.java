package com.example.hellowordsem9.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hellowordsem9.R;

import java.util.List;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder> {
    List<String> comentarios;

    public ComentarioAdapter(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    @NonNull
    @Override
    public ComentarioAdapter.ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
        ComentarioAdapter.ComentarioViewHolder vh = new ComentarioAdapter.ComentarioViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        View vw = holder.itemView;

        String item_comentario = comentarios.get(position);
        TextView comentario = holder.itemView.findViewById(R.id.tvcomentario);


        comentario.setText(item_comentario);
    }


    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public class ComentarioViewHolder extends RecyclerView.ViewHolder {
        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}