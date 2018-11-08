package sv.dk.com.youbetterwrite.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.dk.com.youbetterwrite.Modelos.Story;
import sv.dk.com.youbetterwrite.R;

/**
 * Created by DK-Ragnar on 8/9/2018.
 */

public class HistoriasAdapter extends RecyclerView.Adapter<HistoriasAdapter.ViewHolder> {

    private List<Story> listaHistorias;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private int lastPosition = -1;

    public HistoriasAdapter(Context context, List<Story> listaHistorias) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listaHistorias = listaHistorias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_reciclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.myTextView.setText(mData[position]);
        holder.titulo.setText(listaHistorias.get(position).getName());
        holder.autor.setText("Papa jhons");
        holder.portada.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.categoria.setText(listaHistorias.get(position).getCategory().getName());
        //Glide.with(holder.portada.getContext()).load(listaHistorias.get(position).getPortada()).into(holder.portada);
        Glide.with(holder.portada.getContext()).load("https://www.guiainfantil.com/uploads/ocio/carrerazapatillas-p.jpg").into(holder.portada);
    }

    @Override
    public int getItemCount() {
        return listaHistorias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView portada;
        TextView titulo, autor;
        Button categoria;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.txthistoriatitulo);
            portada = (ImageView) itemView.findViewById(R.id.txthistoriaportada);
            autor = (TextView) itemView.findViewById(R.id.txthistoriaautor);
            categoria = (Button) itemView.findViewById(R.id.txthistoriacategoria);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
