package com.danielcirilo.frasescelebres.adaptadores;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;


import java.util.ArrayList;

public class AdaptadorCategoriaFrase extends RecyclerView.Adapter<AdaptadorCategoriaFrase.ViewHolderFraseCategoria> {
    private ArrayList<Frase> frases;

    private Context context;

    public AdaptadorCategoriaFrase(ArrayList<Frase> frases, Context context) {
        this.frases = frases;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderFraseCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frases, parent, false);
        ViewHolderFraseCategoria viewHolderAutor = new ViewHolderFraseCategoria(itemView, context);
        return viewHolderAutor;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFraseCategoria holder, int position) {
        Frase frase = frases.get(position);
        holder.binAutorFrases(frase);


    }

    @Override
    public int getItemCount() {
        return frases.size();
    }

    public class ViewHolderFraseCategoria extends RecyclerView.ViewHolder {
        private TextView tvFrase;
        private Context context;

        public ViewHolderFraseCategoria(@NonNull View itemView, Context context) {
            super(itemView);

            tvFrase = itemView.findViewById(R.id.tvFrasesAutor);
            this.context = context;


        }

        public void binAutorFrases(Frase frase) {
            tvFrase.setText(frase.getTexto());

        }


    }
}