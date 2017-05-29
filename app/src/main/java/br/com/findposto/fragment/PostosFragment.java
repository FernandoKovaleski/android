package br.com.findposto.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.findposto.R;
import br.com.findposto.activity.PostoActivity;
import br.com.findposto.activity.PostosActivity;
import br.com.findposto.adapter.PostoAdapter;
import br.com.findposto.model.Posto;
import br.com.findposto.service.PostoService;

/**
 * Este fragmento é responsável pelo conteúdo onde são listados os carros. A navegabilidade
 * é responsabilidade da Activity que "infla" este fragmento.
 *
 */
public class PostosFragment extends BaseFragment
        implements SearchView.OnQueryTextListener{

    //o container onde serão apresentados os dados
    protected RecyclerView recyclerView;

    //o SwipeRefresh. O objeto que identificará o movimento de swipe e reagirá
    private SwipeRefreshLayout swipeRefreshLayout;

    //uma animação para indicar processando
    private ProgressBar progressBar;

    //lista dos postos, utilizada no método de tratamento do onClick() do item da RecyclerView
    private List<Posto> postos;

    //a cidade do postos que é recebido como argumento na construção do fragmento
    private String cidade;

    /*
        Método do ciclo de vida do Fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //se há argumentos, o armazena para filtar na lista de postos
        //este tipo de posto vem do TabsAdapter
        if (getArguments() != null) {
            this.cidade = getArguments().getString("cidade");
        }

        //informa ao sistema que o fragment irá adicionar itens de menu na ActionBar
        setHasOptionsMenu(true);

    }

    /*
        Método do ciclo de vida do Fragment.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish(); //finaliza a app
    }

    @Override
    public void onResume() {
        super.onResume();
        //se houver conexão com a internet, wi-fi ou 3G ...
        if (isNetworkAvailable(getContext())) {
            new PostosTask().execute(); //executa a operação REST GET em uma thread AsyncTask
        } else{
            progressBar.setVisibility(View.INVISIBLE);
            if(PostosFragment.this.cidade.equals(getString(R.string.cidade_cachoeirinha))) {
                alertOk(R.string.title_conectividade, R.string.msg_conectividade);
            }
        }
    }

    /*
            Método do ciclo de vida do Fragment.
         */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //infla o xml da UI e associa ao Fragment
        View view = inflater.inflate(R.layout.fragment_postos, container, false);

        //um título para a janela
        ((PostosActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_listpostos);

        //configura a RecyclerView
        //mapeia o RecyclerView do layout.
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_fragmentpostos);

        //associa um gerenciador de layout Linear ao recyclerView.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //associa um tipo de animação ao recyclerView.
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //configura o SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swinperefrechlayout);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);

        //Cria um ProgressBar para mostrar uma animação de processando
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        new PostosTask().execute();

        return view;

    }

    /*
        Infla o menu da ActionBar.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //infla o menu na ActionBar
        inflater.inflate(R.menu.menu_fragment_postos, menu);
        //obtém a SearchView
        SearchView mySearchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        //coloca um hint na SearchView
        mySearchView.setQueryHint(getResources().getString(R.string.hint_searchview));
        //cadastra o tratador de eventos na lista de tratadores da SearchView
        mySearchView.setOnQueryTextListener(this);
    }

    /*
        Trata eventos da SearchView
     */
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        //uma lista para nova camada de modelo da RecyclerView
        List<Posto> postList = new ArrayList<>();

        //um for-eatch na lista de postos
        for(Posto posto : postos){
            //se o nome do posto começa com o texto digitado
            if(posto.nome.contains(newText)) {
                //adiciona o posto na nova lista
                postList.add(posto);
            }
        }

        //coloca a nova lista como fonte de dados do novo adaptador da RecyclerView
        //(Context, fonte de dados, tratador do evento onClick)
        recyclerView.setAdapter(new PostoAdapter(getContext(), postList, onClickPosto()));

        return true;
    }

    /*
        Classe interna que extende uma AsyncTask.
        A AsyncTask gerencia a thread que acessa os dados no web service.
    */
    private class PostosTask extends AsyncTask<Void, Void, List<Posto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //faz com que a ProgressBar apareça para o usuário
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Posto> doInBackground(Void... params) {
            //busca os postos em background, em uma thread exclusiva para esta tarefa.
            try {
                if(PostosFragment.this.cidade.equals(getString(R.string.cidade_todas))){
                    return PostoService.getPosto("/posto");
                }else if(PostosFragment.this.cidade.equals(getString(R.string.cidade_cachoeirinha))){
                    return PostoService.getPosto("/posto/cidade" + PostosFragment.this.cidade);
                }
                    return PostoService.getPosto("/posto/cidade" + PostosFragment.this.cidade);

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Exceção ao obter a lista de postos, método .doInBackground()");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Posto> postos) {
            super.onPostExecute(postos);
            if (postos != null) {
                Log.d(TAG, "Quantidade de postos no onPostExecute(): " + postos.size());
                //atualiza a view na UIThread
                //(Context, fonte de dados, tratador do evento onClick)
                //coloca a lista retornada pelo web service como fonte de dados do adaptador da RecyclerView
                recyclerView.setAdapter(new PostoAdapter(getContext(), postos, onClickPosto()));
                //copia a lista de postos para uso no tratador do onClick
                PostosFragment.this.postos = postos;
                //para a animação da swipeRefrech
                swipeRefreshLayout.setRefreshing(false);
                //faz com que a ProgressBar desapareça para o usuário
                progressBar.setVisibility(View.INVISIBLE);
            }else{
                //faz com que a ProgressBar desapareça para o usuário
                progressBar.setVisibility(View.INVISIBLE);
                //avisa o usuário da falha no download
                alertOk(R.string.title_erro, R.string.msg_erro_falhanodownload); //faz aparecer o AlertDialog para o usuário
            }
        }
    }

    /*
        Este método utiliza a interface declarada na classe PostoAdapter para tratar
        o evento onClick do item da lista.
     */
    protected PostoAdapter.PostoOnClickListener onClickPosto() {
        //chama o contrutor da interface (implícito) para cria uma instância da interface declarada no adaptador.
        return new PostoAdapter.PostoOnClickListener() {
            // Aqui trata o evento onItemClick.
            @Override
            public void onClickPosto(View view, int idx) {
                //armazena o posto que foi clicado na RecyclerView
                Posto posto = postos.get(idx);
                //chama outra Activity para detalhar ou editar o posto clicado pelo usuário
                //configura uma Intent explícita
                Intent intent = new Intent(getContext(), PostoActivity.class);
                //insere um extra com a referência para o objeto Posto
                intent.putExtra("posto", posto);
                //indica para a outra Activity qual o fragmento deve abrir
                intent.putExtra("qualFragmentAbrir", "PostoDetalheFragment");
                //chama outra Activity
                startActivity(intent);
            }
        };
    }
    /*
        Este método trata o evento onRefresh() do SwipeRefreshLayout.
        Ele acontece quando o usuário faz um swipe com o dedo para baixo na View.
     */
    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable(getContext())) { //se houver conexão com a internet, wi-fi ou 3G ...
                    new PostosTask().execute(); //cria uma instância de AsyncTask
                } else {
                    alertOk(R.string.title_conectividade, R.string.msg_conectividade);
                    recyclerView.setAdapter(new PostoAdapter(getContext(), new ArrayList<Posto>(), onClickPosto()));
                }

            }
        };
    }
}
