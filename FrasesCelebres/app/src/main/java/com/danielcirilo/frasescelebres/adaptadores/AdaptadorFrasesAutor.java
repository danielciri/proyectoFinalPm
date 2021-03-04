package com.danielcirilo.frasescelebres.adaptadores;


import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.interfaces.IOnFraseSeleccionada;


import java.util.ArrayList;

public class AdaptadorFrasesAutor extends RecyclerView.Adapter<AdaptadorFrasesAutor.ViewHolderFraseAutor> {
    private ArrayList<Frase> frases;
    private IOnFraseSeleccionada listener;
    private Context context;


    public AdaptadorFrasesAutor(ArrayList<Frase> frases, IOnFraseSeleccionada listener, Context context) {
        this.frases = frases;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderFraseAutor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("no paso de aqui", "bye");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frases, parent, false);
        ViewHolderFraseAutor viewHolderAutor = new ViewHolderFraseAutor(itemView, context, listener);
        return viewHolderAutor;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFraseAutor holder, int position) {
        Frase frase = frases.get(position);
        holder.binAutorFrases(frase);


    }

    @Override
    public int getItemCount() {
        return frases.size();
    }

    public class ViewHolderFraseAutor extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvFrase;
        private IOnFraseSeleccionada listener;
        private Context context;

        public ViewHolderFraseAutor(@NonNull View itemView, Context context, IOnFraseSeleccionada listener) {
            super(itemView);

            tvFrase = itemView.findViewById(R.id.tvFrasesAutor);
            this.context = context;
            this.listener = listener;
            itemView.setOnClickListener(this);

        }

        public void binAutorFrases(Frase frase) {
            tvFrase.setText(frase.getTexto());

        }


        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onFraseSeleccionada(getAdapterPosition());
            }
        }
    }
}