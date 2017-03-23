package co.devhack.ejercicioimc.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.modelos.HistorialIMC;

/**
 * Created by jggomez on 14-Mar-17.
 */

public class HistorialIMCAdapdator extends
        RecyclerView.Adapter<HistorialIMCAdapdator.HistorialIMCHolder> {


    private List<HistorialIMC> lstHistorialIMC;
    private Context context;

    public HistorialIMCAdapdator(List<HistorialIMC> lstHistorialIMC, Context context) {
        this.lstHistorialIMC = lstHistorialIMC;
        this.context = context;
    }

    @Override
    public HistorialIMCHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.historial_imc_list_item, parent, false);

        return new HistorialIMCHolder(view);
    }

    @Override
    public void onBindViewHolder(HistorialIMCHolder holder, int position) {
        holder.listItemLblUsuario.setText(lstHistorialIMC.get(position).getNombreUsuario());
        holder.listItemLblResultadoIMC.setText(lstHistorialIMC.get(position).getResultadoIMC());

        if (lstHistorialIMC.get(position).getPathImage() != null &&
                !lstHistorialIMC.get(position).getPathImage().equals("")) {
            Picasso.with(context)
                    .load(lstHistorialIMC.get(position).getPathImage())
                    .resize(120, 100)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(holder.ListItemImgUsuario);
        }
    }

    @Override
    public int getItemCount() {
        return lstHistorialIMC.size();
    }

    public void add(HistorialIMC historialIMC) {
        lstHistorialIMC.add(historialIMC);
        notifyDataSetChanged();
    }

    public void add(List<HistorialIMC> lstHistorialIMC) {
        this.lstHistorialIMC = lstHistorialIMC;
        notifyDataSetChanged();
    }

    public static class HistorialIMCHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.ListItemImgUsuario)
        ImageView ListItemImgUsuario;

        @BindView(R.id.listItemLblUsuario)
        TextView listItemLblUsuario;

        @BindView(R.id.listItemLblResultadoIMC)
        TextView listItemLblResultadoIMC;

        public HistorialIMCHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
