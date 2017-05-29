package br.com.findposto.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import br.com.findposto.R;
import br.com.findposto.activity.PostoActivity;
import br.com.findposto.model.Posto;
import br.com.findposto.model.Preco;
import br.com.findposto.model.TipoServicoPosto;
import br.com.findposto.service.PostoService;


public class PostoEdicaoFragment extends BaseFragment {

    private Posto posto; //uma instância da classe Posto com escopo global para utilização em membros da classe
    private Preco preco;
    private TipoServicoPosto tsp;

    private RadioButton rbCachoeirinha, rbGravatai;
    private TextView tvNome, tvRua, tvCidade;
    private EditText edComum, edAditivada, edPremium, edEtanol, edDiesel;
    private CheckBox cbLavagem, cb24h, cbOleo, cbConveniencia;
    private ProgressBar progressBarCard0; //progressBar do Card0, do container icone do posto


    //utilizado pelo Fragment para repassar o objeto posto clicado na lista pelo user
    public void setPosto(Posto posto) {
        this.posto = posto;
    }
    public void setPreco(Preco preco) {
        this.preco = preco;
    }
    public void setTipoServicoPosto(TipoServicoPosto tsp) {
        this.tsp = tsp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //informa ao sistema que o fragment irá adicionar botões na ActionBar

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //infla o layout
        View view = inflater.inflate(R.layout.fragment_edicaoposto, container, false);

        ((PostoActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_edicaoposto);  //um título para a janela

        //um log para depurar
        Log.d(TAG, "Dados do registro = " + posto);
/**
        //carrega o icone e controla o progressbar
        Log.d(TAG, "icone = " + posto.idBandeira); //um log para depurar
        icBandeira = (ImageView) view.findViewById(R.id.icIdBadeira_card0_fredicaoposto);
        if(posto.idBandeira != null){
            progressBarCard0 = (ProgressBar) view.findViewById(R.id.pb_card0_fredicaoposto);
            Picasso.with(getContext()).load(posto.urlFoto).fit().into(icBandeira, new Callback() {
                @Override
                public void onSuccess() {
                    progressBarCard0.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressBarCard0.setVisibility(View.GONE);
                }
            });
        }else{
            icBandeira.setImageResource(R.drawable.icBandeira_background);
        }
        icBandeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cria uma Intent
                //primeiro argumento: ação ACTION_PICK "escolha um item a partir dos dados e retorne o seu URI"
                //segundo argumento: refina a ação para arquivos de imagem, retornando um URI
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //inicializa uma Activity. Neste caso, uma que forneca acesso a galeria de imagens do dispositivo.
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), 0);
            }
        });
        **/
        //carrega as cidades nos RadioButtons
        Log.d(TAG, "Cidade = " + posto.cidade); //um log para depurar
        rbCachoeirinha = (RadioButton) view.findViewById(R.id.rb_cachoeirinha_card1_fredicaoposto);
        rbGravatai = (RadioButton) view.findViewById(R.id.rb_gravatai_card1_fredicaoposto);
        if (posto.cidade.equals("cachoeirinha")) {
            rbCachoeirinha.setChecked(true);
        } else {
            rbGravatai.setChecked(true);
        }

        //carrega o nome, cidade, e rua
        Log.d(TAG, "Nome = " + posto.nome + "\nCidade = " + posto.cidade + "\nRua = " + posto.rua); //um log para depurar
        tvNome= (TextView) view.findViewById(R.id.tvNome_card0_fredicaoposto);
        tvCidade = (TextView) view.findViewById(R.id.tvCidade_card0_fredicaoposto);
        tvRua = (TextView) view.findViewById(R.id.tvRua_card0_fredicaoposto);
        tvNome.setText(posto.nome);
        tvCidade.setText(posto.cidade);
        tvRua.setText(posto.rua);

        return view;

        //carega a edição dos precos de combustiveis




        //carrega os tipo de serviços do CheckBox



    }

    /*
        Infla o menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_edicaoposto, menu);
    }


       // Trata eventos dos itens de menu.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{
                //carrega os dados do formulário no objeto
                posto.cidade = tvCidade.getText().toString();
                posto.nome = tvNome.getText().toString();
                posto.rua = tvRua.getText().toString();

                if(rbCachoeirinha.isChecked()){
                    posto.cidade = getContext().getResources().getString(R.string.cidade_cachoeirinha);
                }else if(rbGravatai.isChecked()){
                    posto.cidade = getContext().getResources().getString(R.string.cidade_gravatai);
                }
                new PostosTask().execute("put"); //executa a operação REST PUT em uma thread AsyncTask
                break;
            }
            case R.id.menuitem_excluir:{
                new PostosTask().execute("delete"); //executa a operação REST DELETE em uma thread AsyncTask
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return true;
    }

    /**
     * Método que recebe o retorno da Activity de galeria de imagens.

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            Log.d(TAG, data.toString());
            Uri arquivoUri = data.getData(); //obtém o URI
            Log.d(TAG, "URI do arquivo: " + arquivoUri);
            if(arquivoUri.toString().contains("images")) {
                imageViewFoto.setImageURI(arquivoUri); //coloca a imagem no ImageView
                posto.urlFoto = arquivoUri.toString(); //armazena o Uri da imagem no objeto do modelo
            }else if(arquivoUri.toString().contains("video")) {
                //editTextUrlVideo.setText(arquivoUri.toString()); //coloca a URL do vídeo no EditText
               // posto.urlVideo = arquivoUri.toString(); //armazena o Uri do vídeo no objeto do modelo
            }
        }
    }
**/
    /*
        Classe interna que extende uma AsyncTask.
        Lembrando: A AsyncTask gerência a thread que acessa os dados no web service.
    */
    private class PostosTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //executa a tarefa em background, em uma thread exclusiva para esta tarefa.
            if(params[0].equals("put")){
                try {
                    return PostoService.put("/postos", posto); //URL_BASE
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if(params[0].equals("delete")){
                    try {
                        return PostoService.delete("/postos/" + posto.id); //URL_BASE + /carros/id
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                //faz aparecer uma caixa de diálogo confirmando a operação
                alertOk(R.string.title_confirmacao, R.string.msg_realizadocomsucesso);
            }
        }
    }

}
