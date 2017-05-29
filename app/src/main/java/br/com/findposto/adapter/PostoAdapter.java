package br.com.findposto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.findposto.R;
import br.com.findposto.model.Posto;

/**
 * Esta classe realiza a adaptação dos dados entre a RecyclerView <-> List.
 * Neste projeto a List está sendo alimentada com dados oriundos de um webservice, via JSON.
 *
 */
public class PostoAdapter extends RecyclerView.Adapter<PostoAdapter.PostosViewHolder> {
    protected static final String TAG = "projeto";
    private final List<Posto> postos;

    private final Context context;

    private PostoOnClickListener postoOnClickListener;

    public PostoAdapter(Context context, List<Posto> postos, PostoOnClickListener postoOnClickListener) {
        this.context = context;
        this.postos = postos;
        this.postoOnClickListener = postoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.postos != null ? this.postos.size() : 0;
    }

    @Override
    public PostosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_postos, viewGroup, false);

        // Cria o ViewHolder
        PostosViewHolder holder = new PostosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final PostosViewHolder holder, final int position) {
        // Atualiza a view
        Posto p = postos.get(position);

        holder.tvNome.setText(p.nome);
        holder.tvRua.setText(p.rua);
/**
        Preco pr = precos.get(position);
        holder.tvCombustivel.setText(pr.combustivel);
        holder.tvPreco.setText((int) pr.valor);

        holder.progress.setVisibility(View.VISIBLE);

        Bandeira bnd = bandeiras.get(position);
        Picasso.with(context).load(bnd.id).fit().into(holder.icone, new Callback() {
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }

        });
**/
        // Click
        if (postoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postoOnClickListener.onClickPosto(holder.itemView, position); // A variável position é final
                }
            });
        }
    }

    public interface PostoOnClickListener {
        public void onClickPosto(View view, int idx);
    }

    // ViewHolder com as views
    public static class PostosViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNome;
        public TextView tvRua;
        public TextView tvCombustivel;
        public TextView tvPreco;
        ImageView icone;
        ProgressBar progress;

        public PostosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tvNome = (TextView) view.findViewById(R.id.tvNome);
            tvRua = (TextView) view.findViewById(R.id.tvRua);
            tvCombustivel = (TextView) view.findViewById(R.id.tvCombustivel);
            tvPreco = (TextView)view.findViewById(R.id.tvPreco);
            icone = (ImageView) view.findViewById(R.id.icIdBadeira);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
}
