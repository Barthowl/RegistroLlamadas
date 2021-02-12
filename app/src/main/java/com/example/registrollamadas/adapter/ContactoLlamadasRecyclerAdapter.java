package com.example.registrollamadas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registrollamadas.R;
import com.example.registrollamadas.room.pojo.Contacto;
import com.example.registrollamadas.room.pojo.ContactoLlamadas;

import java.util.ArrayList;
import java.util.List;

public class ContactoLlamadasRecyclerAdapter extends RecyclerView.Adapter<ContactoLlamadasRecyclerAdapter.RecyclerViewHolder>{

    private List<ContactoLlamadas> cl = new ArrayList<>();


    public ContactoLlamadasRecyclerAdapter(List<ContactoLlamadas> cl) {
        this.cl = cl;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.tvtexto.setText(cl.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return cl.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvtexto;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtexto = itemView.findViewById(R.id.tvtexto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}