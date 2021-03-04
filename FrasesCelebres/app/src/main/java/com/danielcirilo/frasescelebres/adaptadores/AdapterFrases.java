package com.danielcirilo.frasescelebres.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danielcirilo.frasescelebres.Models.Categoria;
import com.danielcirilo.frasescelebres.Models.Frase;
import com.danielcirilo.frasescelebres.R;
import com.danielcirilo.frasescelebres.interfaces.ICategoriaSeleccionada;

import java.util.ArrayList;

public class AdapterFrases extends RecyclerView.Adapter<AdapterFrases.ViewHolderFrase> {
    private ArrayList<Categoria> categorias;
    private Context context;
    private ICategoriaSeleccionada listener;

    public AdapterFrases(ArrayList<Categoria> categorias, Context context, ICategoriaSeleccionada listener) {
        this.categorias = categorias;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderFrase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorias, parent, false);
        ViewHolderFrase viewHolderAutor = new ViewHolderFrase(itemView, context, listener);
        return viewHolderAutor;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFrase holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.bindFrases(categoria);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class ViewHolderFrase extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;
        private ICategoriaSeleccionada listener;
        private TextView tvCategoria;

        public ViewHolderFrase(@NonNull View itemView, Context context, ICategoriaSeleccionada listener) {
            super(itemView);
            this.context = context;
            this.listener = listener;
            tvCategoria = itemView.findViewById(R.id.tvCategoria);

            itemView.setOnClickListener(this);
        }

        public void bindFrases(Categoria categoria) {

            tvCategoria.setText(categoria.getNombre());

        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onCategoriaSeleccionada(getAdapterPosition());
            }
        }
    }
}
