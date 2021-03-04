package com.danielcirilo.frasescelebres.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danielcirilo.frasescelebres.Models.Autor;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.interfaces.IAutorSeleccionado;

import java.util.ArrayList;

public class AdapterAutor extends RecyclerView.Adapter<AdapterAutor.ViewHolderAutor> {
    private ArrayList<Autor> autores;
    private Context context;
    private IAutorSeleccionado listener;

    public AdapterAutor(ArrayList<Autor> autores, Context context, IAutorSeleccionado listener) {
        this.autores = autores;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderAutor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_autor, parent, false);
        ViewHolderAutor viewHolderAutor = new ViewHolderAutor(itemView, context, listener);
        return viewHolderAutor;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAutor holder, int position) {
        Autor autor = autores.get(position);
        holder.binAutor(autor);
    }

    @Override
    public int getItemCount() {
        return autores.size();
    }

    public class ViewHolderAutor extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        private IAutorSeleccionado listener;
        private TextView tvMuerte;
        private TextView tvnombre;
        private ImageView ivAutor;

        public ViewHolderAutor(@NonNull View itemView, Context context, IAutorSeleccionado listener) {
            super(itemView);
            this.context = context;
            this.listener = listener;
            tvMuerte = itemView.findViewById(R.id.tvMuerte);
            tvnombre = itemView.findViewById(R.id.tvNombre);
            ivAutor = itemView.findViewById(R.id.ivAutor);
            itemView.setOnClickListener(this);
        }

        public void binAutor(Autor autor) {
            String imageName = "a" + autor.getId();
            int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
            if (resID != 0) {
                ivAutor.setImageResource(resID);
            } else {
                ivAutor.setImageResource(R.drawable.incognito);
            }
            tvnombre.setText(autor.getNombre());
            tvMuerte.setText(autor.getNacimiento() + "-" + autor.getMuerte());

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onAutorSeleccionado(getAdapterPosition());
            }
        }
    }
}
